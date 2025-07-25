package com.cg.estore.cartservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;

/**
 * Global exception handler to catch and handle all custom exceptions
 * across the application in a centralized manner.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleCartNotFoundException(CartNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleProductNotFoundException(ProductNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CartTotalCalculationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleCartTotalCalculationException(CartTotalCalculationException e) {
        return "Error while calculating cart total: " + e.getMessage();
    }

    // Optional: Catch any unhandled RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleRuntimeException(RuntimeException e) {
        return "Unexpected error occurred: " + e.getMessage();
    }
}
