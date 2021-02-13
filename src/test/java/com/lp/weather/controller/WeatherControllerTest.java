package com.lp.weather.controller;

import com.lp.weather.entity.Weather;
import com.lp.weather.service.WeatherService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WeatherController.class)
public class WeatherControllerTest {

    @MockBean
    WeatherService weatherService;
    HttpHeaders httpHeaders;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        httpHeaders = new HttpHeaders() {{
            String auth = "Test" + ":" + "Test123";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    @Test
    public void getWeatherTest() throws Exception {

        when(weatherService.getWeather(anyString(), anyString())).thenReturn(getWeather());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/weather-service/v1/weather").param("cityName", "London").param("apiKey", "cc22fdb65b00778a6de49a488ebe7438").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE).headers(httpHeaders);
        MvcResult mockResponse = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(mockResponse);
        Assert.assertEquals(200, mockResponse.getResponse().getStatus());
    }


    private Weather getWeather() {
        Weather weather = new Weather();
        weather.setCity("London");
        weather.setCountry("UK");
        weather.setTemperature(272.95);
        weather.setId(1);
        return weather;
    }

}
