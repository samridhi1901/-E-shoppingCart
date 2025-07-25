package com.cg.estore.paymentservice.exception;

public class PaymentNotFoundException extends RuntimeException{
	public PaymentNotFoundException(String msg) {
		super(msg);
	}

}
