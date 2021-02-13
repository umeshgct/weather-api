package com.lp.weather.repository;

import com.lp.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Weather API Repository
 */
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    Weather findFirstByCity(String cityName);

}
