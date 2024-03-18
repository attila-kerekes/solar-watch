package com.example.solarwatch.controller;

import com.example.solarwatch.dto.sunrisesunset.GeocodingDTO;
import com.example.solarwatch.dto.sunrisesunset.SunriseSunsetDTO;
import com.example.solarwatch.model.sunrisesunset.City;
import com.example.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final SolarWatchService solarWatchService;

    @Autowired
    public AdminController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @PostMapping("/cities/{cityName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addCity(@PathVariable String cityName,
                                        @RequestBody GeocodingDTO geocodingDTO) {
        City city = solarWatchService.saveCityData(cityName, geocodingDTO);
        if (city != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/sunrisesunset/{cityName}/{localDate}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addSunriseSunset(@PathVariable String cityName,
                                                 @PathVariable LocalDate localDate,
                                                 @RequestBody SunriseSunsetDTO sunriseSunsetDTO) {
        if (solarWatchService.saveSunriseSunsetData(cityName, localDate, sunriseSunsetDTO).isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/cities/{cityName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCity(@PathVariable String cityName,
                                           @RequestBody GeocodingDTO geocodingDTO) {
        if (solarWatchService.updateCity(cityName, geocodingDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sunrisesunset/{cityName}/{localDate}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateSunriseSunset(@PathVariable String cityName,
                                                    @PathVariable LocalDate localDate,
                                                    @RequestBody SunriseSunsetDTO sunriseSunsetDTO) {
        if (solarWatchService.updateSunriseSunset(cityName, localDate, sunriseSunsetDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cities/{cityName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCity(@PathVariable String cityName) {
        if (solarWatchService.deleteCity(cityName)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sunrisesunset/{cityName}/{localDate}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSunriseSunset(@PathVariable String cityName,
                                                    @PathVariable LocalDate localDate) {
        if (solarWatchService.deleteSunriseSunset(cityName, localDate)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
