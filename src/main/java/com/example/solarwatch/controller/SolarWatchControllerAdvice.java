package com.example.solarwatch.controller;

import com.example.solarwatch.exception.CityNotFoundException;
import com.example.solarwatch.exception.InvalidParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SolarWatchControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCityNotFoundException(CityNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidParameterException(InvalidParameterException ex) {
        return ex.getMessage();
    }
}
