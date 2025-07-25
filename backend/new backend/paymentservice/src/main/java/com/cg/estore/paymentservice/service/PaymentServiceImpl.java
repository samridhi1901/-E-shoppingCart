package com.cg.estore.paymentservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.estore.paymentservice.entity.Cart;
import com.cg.estore.paymentservice.entity.Payment;
import com.cg.estore.paymentservice.entity.User;
import com.cg.estore.paymentservice.exception.CustomerNotFoundException;
import com.cg.estore.paymentservice.exception.OrderNotFoundException;
import com.cg.estore.paymentservice.exception.PaymentNotFoundException;
import com.cg.estore.paymentservice.repository.PaymentRepository;


@Service
public class PaymentServiceImpl implements PaymentService {
 
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserFeignClient userFeignClient;
    
    @Autowired
    private CartFeignClient cartFeignClient;

    
//    @Autowired
//    public PaymentServiceImpl(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
//        this.paymentRepository = paymentRepository;
//        Stripe.apiKey = secretKey;
//    }
//
//    public Map<String, String> createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
//
//        long amount = paymentInfoRequest.getAmount();
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amount * 100)
//                        .setCurrency("inr")
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .build()
//                        )
//                        .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//        int idx = paymentIntent.getClientSecret().indexOf("_secret");
//        Payment payment = paymentRepository.save(new Payment(paymentIntent.getClientSecret().substring(0, idx), paymentInfoRequest.getUserId(), new Date(), amount, paymentInfoRequest.getDescription(), "Initiated"));
//
//        Map<String, String> response = new HashMap<>();
//        response.put("clientSecret", paymentIntent.getClientSecret());
//        return response;
//    }
//
//    public void updatePaymentStatus(Payment payment) {
//        Optional<Payment> payment_ = paymentRepository.findById(payment.getPaymentId());
//
//        if (payment_.isPresent()) {
//            paymentRepository.save(payment);
//            return;
//        }
//        throw new PaymentException("Invalid payment id:" + payment.getPaymentId());
//    }
//
//    public Payment getPaymentData(String paymentId) {
//        Optional<Payment> payment = paymentRepository.findById(paymentId);
//
//        if (payment.isPresent()) {
//            return payment.get();
//        }
//        throw new PaymentException("Invalid payment id:" + paymentId);
//    }
//
//    @Override
//    public List<Payment> getAllPayment() {
//        return paymentRepository.findAll();
//    }
//}
    @Override
    public Payment addPayment(Payment payment, String fullName, String cartId) {
        try {
            // Fetch user by full name using Feign
            User user = userFeignClient.getProfileByFullName(fullName);
            if (user == null) {
                throw new CustomerNotFoundException("Customer not found with UserName: " + fullName);
            }

            // Fetch cart by ID using Feign
            Cart cart = cartFeignClient.getCartById(cartId);
            if (cart == null) {
                throw new OrderNotFoundException("Cart not found with ID: " + cartId);
            }

            // Validate card
            if (payment.getCard() == null) {
                throw new PaymentNotFoundException("Card details are missing in payment.");
            }

            // Set payment details
            payment.setUserName(user.getFullName());
            payment.setCartId(cart.getCartId());
            payment.setTotalPrice(cart.getTotalPrice());
            payment.setPaymentDate(new Date());
            payment.setUserprofile(user); // this line replaces the old problematic one
            payment.setPaymentStatus("Paid");

            return paymentRepository.save(payment);

        } catch (CustomerNotFoundException | OrderNotFoundException | PaymentNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while processing the request.", ex);
        }
    }

 
    @Override
    public Payment getPaymentById(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + paymentId));
    }
}