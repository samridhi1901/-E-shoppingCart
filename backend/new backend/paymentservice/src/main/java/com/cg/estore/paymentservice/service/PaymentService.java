package com.cg.estore.paymentservice.service;

import com.cg.estore.paymentservice.entity.Payment;

public interface PaymentService {

	Payment addPayment(Payment payment, String profileId, String cartId);

	Payment getPaymentById(String paymentId);

}
