package com.lp.weather.exception;

/**
 * Custom exception class for handling invalid API keys
 */
public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException(String exceptionMessage) {

        super(exceptionMessage);
    }
}