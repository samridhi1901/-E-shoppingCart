package com.cg.estore.cartservice.exception;
//Custom exception class to represent the scenario where a product is not found
public class ProductNotFoundException extends RuntimeException {

 // Constructor that takes a message describing the exception
 public ProductNotFoundException(String msg) {
     super(msg);
 }
}
