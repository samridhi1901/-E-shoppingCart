package com.cg.estore.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.estore.paymentservice.entity.Payment;
import com.cg.estore.paymentservice.repository.PaymentRepository;
import com.cg.estore.paymentservice.service.PaymentService;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
 
    @PostMapping("/addPayment/{fullName}/{cartId}")
    public Payment addPayment(@RequestBody  Payment payment, @PathVariable String fullName, @PathVariable String cartId) {
//        payment.setUserName(user.);
//        payment.setOrderId(orderId);
        return paymentService.addPayment(payment, fullName, cartId);
    }
    @GetMapping("/getPayment/{paymentId}")
    public Payment getPaymentById(@PathVariable String paymentId) {
    	
        return paymentService.getPaymentById(paymentId);
    }

}
