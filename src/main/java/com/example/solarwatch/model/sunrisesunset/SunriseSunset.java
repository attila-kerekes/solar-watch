package com.example.solarwatch.model.sunrisesunset;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class SunriseSunset {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long sunrisesunset_id;
    private String sunrise;
    private String sunset;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
