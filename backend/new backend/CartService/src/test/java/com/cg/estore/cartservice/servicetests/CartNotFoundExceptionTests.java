package com.cg.estore.cartservice.servicetests;

import org.junit.jupiter.api.Test;

import com.cg.estore.cartservice.exception.CartNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartNotFoundExceptionTests {

    @Test
    public void testCartNotFoundExceptionWithMessage() {
        CartNotFoundException exception = new CartNotFoundException("Cart not found");
        assertEquals("Cart not found", exception.getMessage());
    }

    @Test
    public void testCartNotFoundExceptionWithoutMessage() {
        CartNotFoundException exception = new CartNotFoundException(null);
        assertEquals(null, exception.getMessage());
    }
}

