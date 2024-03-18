package com.example.solarwatch.model.sunrisesunset;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long city_id;
    private String name;
    private double longitude;
    private double latitude;
    private String state;
    private String country;


    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
