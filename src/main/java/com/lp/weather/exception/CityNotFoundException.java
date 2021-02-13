package com.lp.weather.exception;

/**
 * Custom exception class for handling supplied invalid city
 */
public class CityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String exceptionMessage) {

        super(exceptionMessage);
    }
}