package com.example.solarwatch.service;

import com.example.solarwatch.exception.CityNotFoundException;
import com.example.solarwatch.model.sunrisesunset.City;
import com.example.solarwatch.dto.sunrisesunset.GeocodingDTO;
import com.example.solarwatch.model.sunrisesunset.SunriseSunset;
import com.example.solarwatch.dto.sunrisesunset.SunriseSunsetDTO;
import com.example.solarwatch.repository.CityRepository;
import com.example.solarwatch.repository.SunriseSunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SolarWatchService {

    private static final String API_KEY = "c2cd24014ece24a410142e5efdd56314";
    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);

    public SolarWatchService(RestTemplate restTemplate,
                             CityRepository cityRepository,
                             SunriseSunsetRepository sunriseSunsetRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

    public SunriseSunsetDTO getSunriseSunset(String city, String date) {

        //SEARCH DB FOR CITY
        Optional<City> cityOptional = cityRepository.findByName(city);
        City existingCity;

        //IF I HAVE IT, RETRIEVE
        if (cityOptional.isPresent()) {
            existingCity = cityOptional.get();
            //IF NOT FETCH THE DTO
        } else {
            GeocodingDTO cityDTO = fetchGeocodingResponseFromAPI(city);
            //SAVE IT IN THE DB
            existingCity = saveCityData(city, cityDTO);
        }

        //SEARCH DB FOR SUNRISE, SUNSET
        LocalDate localDate = LocalDate.parse(date);

        Optional<SunriseSunset> sunriseSunsetOptional = sunriseSunsetRepository.findByCityAndDate(existingCity, localDate);
        Optional<SunriseSunset> sunriseSunset;

        SunriseSunsetDTO sunriseSunsetDTO = new SunriseSunsetDTO();
        //IF I HAVE IT, RETRIEVE
        if (sunriseSunsetOptional.isPresent()) {
            sunriseSunset = sunriseSunsetOptional;
            //IF NOT FETCH THE DTO
        } else {
            sunriseSunsetDTO = fetchSunriseSunsetResponseFromAPI(
                    existingCity.getLatitude(),
                    existingCity.getLongitude(),
                    date);
            //SAVE IT IN THE DB
            sunriseSunset = saveSunriseSunsetData(existingCity.getName(), localDate, sunriseSunsetDTO);
        }

        //RETURN SUNRISE/SUNSET DATA
        return sunriseSunsetDTO;
    }

    private GeocodingDTO fetchGeocodingResponseFromAPI(String city) {
        String geocodingUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + API_KEY;
        ResponseEntity<GeocodingDTO[]> responseEntity = restTemplate.getForEntity(geocodingUrl, GeocodingDTO[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null && responseEntity.getBody().length > 0) {
            return responseEntity.getBody()[0];
        } else {
            throw new CityNotFoundException("City not found: " + city);
        }
    }

    private SunriseSunsetDTO fetchSunriseSunsetResponseFromAPI(double latitude, double longitude, String date) {
        String sunriseSunsetUrl = "https://api.sunrise-sunset.org/json?lat=" +
                latitude +
                "&lng=" +
                longitude +
                "&date=" +
                date;
        ResponseEntity<SunriseSunsetDTO> sunriseSunsetResponse = restTemplate.getForEntity(
                sunriseSunsetUrl, SunriseSunsetDTO.class);

        if (sunriseSunsetResponse.getStatusCode().is2xxSuccessful()) {
            return sunriseSunsetResponse.getBody();
        } else {
            logger.error("Failed to fetch sunrise/sunset data from the API");
            throw new RuntimeException("Failed to fetch sunrise/sunset data from the API");
        }
    }

    public City saveCityData(String city, GeocodingDTO geocodingResponse) {
        City newCity = new City();
        newCity.setName(city);
        newCity.setLatitude(geocodingResponse.getLat());
        newCity.setLongitude(geocodingResponse.getLon());
        newCity.setState(geocodingResponse.getState());
        newCity.setCountry(geocodingResponse.getCountry());

        return cityRepository.save(newCity);
    }

    public Optional<SunriseSunset> saveSunriseSunsetData(String cityName, LocalDate date, SunriseSunsetDTO responseBody) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            SunriseSunset sunriseSunset = new SunriseSunset();
            sunriseSunset.setSunrise(responseBody.getResults().getSunrise());
            sunriseSunset.setSunset(responseBody.getResults().getSunset());
            sunriseSunset.setDate(date);
            sunriseSunset.setCity(city);
            return Optional.of(sunriseSunsetRepository.save(sunriseSunset));
        }
        return Optional.empty();
    }

    public boolean updateCity(String cityName, GeocodingDTO geocodingDTO) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setLatitude(geocodingDTO.getLat());
            city.setLongitude(geocodingDTO.getLon());
            city.setState(geocodingDTO.getState());
            city.setCountry(geocodingDTO.getCountry());
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    public boolean updateSunriseSunset(String cityName, LocalDate localDate, SunriseSunsetDTO sunriseSunsetDTO) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            Optional<SunriseSunset> optionalSunriseSunset = sunriseSunsetRepository.findByCityAndDate(city, localDate);
            if (optionalSunriseSunset.isPresent()) {
                SunriseSunset sunriseSunset = optionalSunriseSunset.get();
                sunriseSunset.setSunrise(sunriseSunsetDTO.getResults().getSunrise());
                sunriseSunset.setSunset(sunriseSunsetDTO.getResults().getSunset());
                sunriseSunsetRepository.save(sunriseSunset);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSunriseSunset(String cityName, LocalDate date) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            Optional<SunriseSunset> optionalSunriseSunset = sunriseSunsetRepository.findByCityAndDate(city, date);
            if (optionalSunriseSunset.isPresent()) {
                sunriseSunsetRepository.delete(optionalSunriseSunset.get());
                return true;
            }
        }
        return false;
    }

    public boolean deleteCity(String cityName) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();

            sunriseSunsetRepository.deleteByCity(city);

            cityRepository.delete(city);

            return true;
        }
        return false;
    }

}
