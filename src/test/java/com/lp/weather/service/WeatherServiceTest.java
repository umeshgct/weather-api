package com.lp.weather.service;

import com.lp.weather.entity.Weather;
import com.lp.weather.exception.CityNotFoundException;
import com.lp.weather.exception.UnAuthorizedException;
import com.lp.weather.model.Main;
import com.lp.weather.model.Sys;
import com.lp.weather.model.WeatherResponse;
import com.lp.weather.repository.WeatherRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WeatherServiceTest {

    @InjectMocks
    WeatherServiceImpl weatherService = new WeatherServiceImpl();

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void init() {

    }

    @Test
    public void getWeatherAndSaveDBTest() throws IOException {
        String url = "http://localhost:8080/weather-service/v1/weather?cityName=London&apiKey=cc22fdb65b00778a6de49a488ebe7438";
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(new ResponseEntity(getWeatherResponse(), HttpStatus.OK));
        when(weatherRepository.findFirstByCity("London")).thenReturn(null);
        when(weatherRepository.save(any(Weather.class))).thenAnswer(i -> i.getArguments()[0]);
        Weather weather = weatherService.getWeather("London", "cc22fdb65b00778a6de49a488ebe7438");
        Assert.assertNotNull(weather);
        Assert.assertEquals("London", weather.getCity());
        Assert.assertEquals("UK", weather.getCountry());
        Assert.assertEquals(Double.valueOf(272.95), weather.getTemperature());
    }


    @Test
    public void getWeatherAndUpdateDBTest() throws IOException {
        String url = "http://localhost:8080/weather-service/v1/weather?cityName=London&apiKey=cc22fdb65b00778a6de49a488ebe7438";
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenReturn(new ResponseEntity(getWeatherResponse(), HttpStatus.OK));
        when(weatherRepository.findFirstByCity("London")).thenReturn(getWeather());
        when(weatherRepository.save(any(Weather.class))).thenAnswer(i -> i.getArguments()[0]);
        Weather weather = weatherService.getWeather("London", "cc22fdb65b00778a6de49a488ebe7438");
        Assert.assertNotNull(weather);
        Assert.assertEquals(weather.getCity(), "London");
        Assert.assertEquals(weather.getCountry(), "UK");
        Assert.assertEquals(Double.valueOf(272.95), weather.getTemperature());
    }

    @Test(expected = CityNotFoundException.class)
    public void CityNotFoundExceptionTest() {

        String url = "http://localhost:8080/weather-service/v1/weather?cityName=London&apiKey=cc22fdb65b00778a6de49a488ebe7438";
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenThrow(CityNotFoundException.class);
        Weather weather = weatherService.getWeather("London", "cc22fdb65b00778a6de49a488ebe7438");
    }

    @Test(expected = UnAuthorizedException.class)
    public void unAuthorizedExceptionTest() {

        String url = "http://localhost:8080/weather-service/v1/weather?cityName=London&apiKey=cc22fdb65b00778a6de49a488ebe7438";
        when(restTemplate.getForEntity(anyString(), any(Class.class))).thenThrow(UnAuthorizedException.class);
        Weather weather = weatherService.getWeather("London", "cc22fdb65b00778a6de49a488ebe7438");
    }


    private Weather getWeather() {
        Weather weather = new Weather();
        weather.setCity("London");
        weather.setCountry("UK");
        weather.setTemperature(272.95);
        weather.setId(1);
        return weather;
    }

    private WeatherResponse getWeatherResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName("London");

        Sys sys = new Sys();
        sys.setCountry("UK");

        Main main = new Main();
        main.setTemp(272.95);

        weatherResponse.setSys(sys);
        weatherResponse.setMain(main);

        return weatherResponse;
    }

}
