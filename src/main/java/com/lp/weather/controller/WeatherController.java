package com.lp.weather.controller;

import com.lp.weather.entity.Weather;
import com.lp.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for fetching and saving latest weather information from open weather API
 */
@RestController
@RequestMapping("/weather-service/v1")
@Tag(name = "weather", description = "the Weather API")
public class WeatherController {

    private final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    /**
     * To fetch the weather information based on the supplied city
     *
     * @param cityName
     * @param apiKey
     * @return
     */
    @Operation(summary = "Fetch weather information for a particular city", description = "", tags = {"weather"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Weather.class))),
            @ApiResponse(responseCode = "401", description = "Invalid API key supplied"),
            @ApiResponse(responseCode = "404", description = "City not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server error occurred")})

    @GetMapping(value = "/weather", produces = {"application/json"})
    public ResponseEntity<Weather> getWeather(
            @Parameter(description = "City name", required = true) @RequestParam(value = "cityName") String cityName,

            @Parameter(description = "Api Key for retrieving  value", required = true) @RequestParam("apiKey") String apiKey) {

        Weather weather = weatherService.getWeather(cityName, apiKey);

        LOGGER.info("Weather API Response :" + weather);

        return new ResponseEntity<>(weather, HttpStatus.OK);

    }


}

