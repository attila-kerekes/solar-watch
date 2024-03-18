package com.example.solarwatch.dto.sunrisesunset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingDTO {
    private double lat;
    private double lon;
    private String country;
    private String state;

    public GeocodingDTO(double lat, double lon, String country, String state) {
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.state = state;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
