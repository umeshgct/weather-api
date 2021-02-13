package com.lp.weather.service;

import com.lp.weather.entity.Weather;
import com.lp.weather.exception.CityNotFoundException;
import com.lp.weather.exception.UnAuthorizedException;
import com.lp.weather.model.WeatherResponse;
import com.lp.weather.repository.WeatherRepository;
import com.lp.weather.util.WeatherConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Weatherservice Implementation class for fetching and persist latest weather information from open weather API
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * To fetch the latest weather information from open API
     *
     * @param cityName
     * @param apiKey
     * @return
     */

    public Weather getWeather(String cityName, String apiKey) {
        Weather weather = null;
        try {
            String url = WeatherConstants.WEATHER_API_URL.replace("{city}", cityName).replace("{appid}", apiKey);

            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);

            weather = weatherDataMapper(response.getBody());

            Optional<Weather> optionalPersistedEntity = Optional.ofNullable(weatherRepository.findFirstByCity(cityName));

            if (optionalPersistedEntity.isPresent()) {

                return updateWeather(weather, optionalPersistedEntity);
            }


        } catch (HttpClientErrorException ex) {
            int statusCode = ex.getRawStatusCode();
            if (statusCode == 401) {
                throw new UnAuthorizedException("Invalid API Key");
            } else if (statusCode == 404) {
                throw new CityNotFoundException("City Not Found");
            }
        }
        return weatherRepository.save(weather);
    }


    private Weather updateWeather(Weather weather, Optional<Weather> optionalPersistedEntity) {

        Weather persistWeather = optionalPersistedEntity.get();

        persistWeather.setTemperature(weather.getTemperature());

        return weatherRepository.save(persistWeather);
    }


    private Weather weatherDataMapper(WeatherResponse response) {

        Weather entity = new Weather();
        entity.setCity(response.getName());
        entity.setCountry(response.getSys().getCountry());
        entity.setTemperature(response.getMain().getTemp());

        return entity;
    }
}
