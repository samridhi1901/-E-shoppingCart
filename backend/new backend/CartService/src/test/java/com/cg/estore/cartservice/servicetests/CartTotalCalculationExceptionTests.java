package com.cg.estore.cartservice.servicetests;

import org.junit.jupiter.api.Test;

import com.cg.estore.cartservice.exception.CartTotalCalculationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CartTotalCalculationExceptionTests {

    @Test
    public void testCartTotalCalculationExceptionWithMessage() {
        CartTotalCalculationException exception = new CartTotalCalculationException("Calculation error");
        assertEquals("Calculation error", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCartTotalCalculationExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        CartTotalCalculationException exception = new CartTotalCalculationException("Calculation error", cause);
        assertEquals("Calculation error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testCartTotalCalculationExceptionWithoutMessage() {
        CartTotalCalculationException exception = new CartTotalCalculationException(null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
