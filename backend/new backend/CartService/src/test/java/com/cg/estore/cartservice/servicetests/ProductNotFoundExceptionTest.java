package com.cg.estore.cartservice.servicetests;

import org.junit.jupiter.api.Test;

import com.cg.estore.cartservice.exception.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductNotFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        // Arrange
        String errorMessage = "Product not found.";

        // Act
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testDefaultConstructor() {
    	String errorMessage = "Product not found.";
        // Act
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        // Assert
        assertNull(exception.getMessage());
    }

    // Additional test cases can be added as needed
}
