package com.cg.estore.cartservice.exception;

//Custom exception class to represent errors during cart total calculation
public class CartTotalCalculationException extends RuntimeException {

 // Constructor that takes a message describing the exception
 public CartTotalCalculationException(String message) {
     super(message);
 }

 // Constructor that takes a message and a cause (e.g., another exception) for more detailed information
 public CartTotalCalculationException(String message, Throwable cause) {
     super(message, cause);
 }
}
