package com.example.solarwatch.repository;

import com.example.solarwatch.model.sunrisesunset.City;
import com.example.solarwatch.model.sunrisesunset.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunriseSunsetRepository extends JpaRepository<SunriseSunset, Long> {

    Optional<SunriseSunset> findByCityAndDate(City city, LocalDate date);

    void deleteByCity(City city);
}
