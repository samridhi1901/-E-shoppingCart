package com.cg.estore.productservice.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.estore.productservice.exception.GlobalExceptionHandler;
import com.cg.estore.productservice.exception.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTests {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleProductNotFoundException() {
        ProductNotFoundException exception = new ProductNotFoundException("Product not found");
        ResponseEntity<String> response = globalExceptionHandler.handleProductNotFoundException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }

    @Test
    public void testHandleGlobalException() {
        Exception exception = new Exception("Internal server error");
        ResponseEntity<String> response = globalExceptionHandler.handleGlobalException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred.", response.getBody());
    }

    // You can add more test cases for other exception handlers as needed
}
