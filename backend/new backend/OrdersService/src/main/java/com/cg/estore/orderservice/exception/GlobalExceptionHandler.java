package com.cg.estore.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Global exception handler for order service.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleOrderNotFoundException(OrderNotFoundException ex) {
        return ex.getMessage();  // You can customize this message format if needed
    }

    // Optional: Catch any other unhandled exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleGenericRuntimeException(RuntimeException ex) {
        return "Unexpected error occurred: " + ex.getMessage();
    }
}
