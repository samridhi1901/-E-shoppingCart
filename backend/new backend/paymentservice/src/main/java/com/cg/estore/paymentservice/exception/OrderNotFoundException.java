package com.cg.estore.paymentservice.exception;



public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(String msg) {
		super(msg);
	}
}
