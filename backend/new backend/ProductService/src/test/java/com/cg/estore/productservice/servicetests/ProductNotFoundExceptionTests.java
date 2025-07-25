package com.cg.estore.productservice.servicetests;
import org.junit.jupiter.api.Test;

import com.cg.estore.productservice.exception.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductNotFoundExceptionTests {

	@Test
    void constructorWithMessage() {
        // Given
        String message = "Product not found";

        // When
        ProductNotFoundException exception = new ProductNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    
}


