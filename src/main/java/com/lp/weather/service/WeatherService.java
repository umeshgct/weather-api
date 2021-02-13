package com.lp.weather.service;

import com.lp.weather.entity.Weather;

/**
 * Weather service interface
 */
public interface WeatherService {

    Weather getWeather(String cityName, String apiKey);


}
