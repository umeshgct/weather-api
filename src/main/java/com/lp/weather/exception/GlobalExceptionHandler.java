package com.lp.weather.exception;

import com.lp.weather.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Global exception handling for weather api application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(UnAuthorizedException unAuthorizedException) {
        ErrorResponse errorMessage = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                unAuthorizedException.getMessage(),
                "Please supply the valid api key");
        return errorMessage;
    }

    @ExceptionHandler({CityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(CityNotFoundException weatherAppCustomException) {
        ErrorResponse errorMessage = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                weatherAppCustomException.getMessage(),
                "Supplied city input not found in Wather api");
        return errorMessage;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse genericException(Exception exception) {
        ErrorResponse errorMessage = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                "Exception occurred in Weather api ");
        return errorMessage;
    }
}