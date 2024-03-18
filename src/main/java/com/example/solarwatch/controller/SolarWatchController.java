package com.example.solarwatch.controller;

import com.example.solarwatch.dto.sunrisesunset.SunriseSunsetDTO;
import com.example.solarwatch.service.SolarWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolarWatchController {

    private final SolarWatchService solarWatchService;

    @Autowired
    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/api/sunrise-sunset")
    public ResponseEntity<SunriseSunsetDTO> getSunriseSunset(
            @RequestParam String city,
            @RequestParam String date) {
        SunriseSunsetDTO sunriseSunset = solarWatchService.getSunriseSunset(city, date);
        return new ResponseEntity<>(sunriseSunset, HttpStatus.OK);
    }
}

