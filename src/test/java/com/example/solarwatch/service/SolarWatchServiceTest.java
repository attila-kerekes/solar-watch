package com.example.solarwatch.service;

import com.example.solarwatch.dto.sunrisesunset.GeocodingDTO;
import com.example.solarwatch.dto.sunrisesunset.SunriseSunsetDTO;
import com.example.solarwatch.dto.sunrisesunset.SunriseSunsetResult;
import com.example.solarwatch.exception.CityNotFoundException;
import com.example.solarwatch.repository.CityRepository;
import com.example.solarwatch.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@Disabled
@ExtendWith(MockitoExtension.class)
public class SolarWatchServiceTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private CityRepository cityRepositoryMock;

    @Mock
    private SunriseSunsetRepository sunriseSunsetRepositoryMock;

    @InjectMocks
    private SolarWatchService solarWatchService;

    @Test
    public void testGetSunriseSunset() {
        // Sample data
        String city = "New York";
        String date = "2024-03-03";
        double latitude = 40.7128;
        double longitude = -74.0060;
        String country = "USA";
        String state = "New York";
        String sunriseTime = "06:30:00 AM";
        String sunsetTime = "06:00:00 PM";

        // Mocking the response for geocoding API
        GeocodingDTO geocodingDTO = new GeocodingDTO(latitude, longitude, country, state);
        geocodingDTO.setLat(latitude);
        geocodingDTO.setLon(longitude);
        ResponseEntity<GeocodingDTO[]> geocodingResponseEntity =
                new ResponseEntity<>(new GeocodingDTO[]{geocodingDTO}, HttpStatus.OK);

        // Mocking the response for sunrise-sunset API
        SunriseSunsetResult sunriseSunsetResult = new SunriseSunsetResult();
        sunriseSunsetResult.setSunrise(sunriseTime);
        sunriseSunsetResult.setSunset(sunsetTime);
        SunriseSunsetDTO expectedSunriseSunsetDTO = new SunriseSunsetDTO();
        expectedSunriseSunsetDTO.setResults(sunriseSunsetResult);
        ResponseEntity<SunriseSunsetDTO> sunriseSunsetResponseEntity =
                new ResponseEntity<>(expectedSunriseSunsetDTO, HttpStatus.OK);

        // Mocking RestTemplate behavior
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(GeocodingDTO[].class)))
                .thenReturn(geocodingResponseEntity);
        Mockito.when(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.eq(SunriseSunsetDTO.class)))
                .thenReturn(sunriseSunsetResponseEntity);

        // Creating the service instance
        SolarWatchService solarWatchService = new SolarWatchService(restTemplateMock, cityRepositoryMock, sunriseSunsetRepositoryMock, userRepository);

        // Calling the method under test
        SunriseSunsetDTO actualSunriseSunsetDTO = solarWatchService.getSunriseSunset(city, date);

        // Assertions
        assertNotNull(actualSunriseSunsetDTO);
        assertEquals(sunriseTime, actualSunriseSunsetDTO.getResults().getSunrise());
        assertEquals(sunsetTime, actualSunriseSunsetDTO.getResults().getSunset());
    }


    @Test
    public void testGetSunriseSunsetCityNotFound() {
        // Sample data
        String city = "Unknown City";
        String date = "2024-03-03";

        // Mocking City repository behavior to return empty optional when city is not found
        Mockito.when(cityRepositoryMock.findByName(city)).thenReturn(Optional.empty());

        // Mocking RestTemplate behavior
        ResponseEntity<GeocodingDTO[]> geocodingResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplateMock.getForEntity(anyString(), Mockito.eq(GeocodingDTO[].class)))
                .thenReturn(geocodingResponseEntity);

        // Calling the method under test and asserting the exception
        assertThrows(CityNotFoundException.class, () -> solarWatchService.getSunriseSunset(city, date));
    }
}
