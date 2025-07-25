package com.cg.estore.cartservice.exception;

// Custom exception class to represent the scenario where a cart is not found
public class CartNotFoundException extends RuntimeException {

    // Constructor that takes a message describing the exception
    public CartNotFoundException(String message) {
        super(message);
    }
}
