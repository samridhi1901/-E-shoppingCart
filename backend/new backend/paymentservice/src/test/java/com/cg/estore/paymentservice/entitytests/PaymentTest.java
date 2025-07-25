package com.cg.estore.paymentservice.entitytests;

import com.cg.estore.paymentservice.entity.Card;
import com.cg.estore.paymentservice.entity.Payment;
import com.cg.estore.paymentservice.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void testPaymentConstructor() {
        // Given
        String paymentId = "1";
        double totalPrice = 100.0;
        String paymentMode = "Credit Card";
        String paymentStatus = "Completed";
        Card card = new Card();
        Date paymentDate = new Date();
        String userName = "John Doe";
        String cartId = "cart123";
        User userprofile = new User();

        // When
        Payment payment = new Payment(paymentId, totalPrice, paymentMode, paymentStatus, card, paymentDate, userName, cartId, userprofile);

        // Then
        assertNotNull(payment);
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(totalPrice, payment.getTotalPrice());
        assertEquals(paymentMode, payment.getPaymentMode());
        assertEquals(paymentStatus, payment.getPaymentStatus());
        assertEquals(card, payment.getCard());
        assertEquals(paymentDate, payment.getPaymentDate());
        assertEquals(userName, payment.getUserName());
        assertEquals(cartId, payment.getCartId());
        assertEquals(userprofile, payment.getUserprofile());
    }

    @Test
    void testPaymentSettersAndGetters() {
        // Given
        Payment payment = new Payment();
        String paymentId = "1";
        double totalPrice = 100.0;
        String paymentMode = "Credit Card";
        String paymentStatus = "Completed";
        Card card = new Card();
        Date paymentDate = new Date();
        String userName = "John Doe";
        String cartId = "cart123";
        User userprofile = new User();

        // When
        payment.setPaymentId(paymentId);
        payment.setTotalPrice(totalPrice);
        payment.setPaymentMode(paymentMode);
        payment.setPaymentStatus(paymentStatus);
        payment.setCard(card);
        payment.setPaymentDate(paymentDate);
        payment.setUserName(userName);
        payment.setCartId(cartId);
        payment.setUserprofile(userprofile);

        // Then
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(totalPrice, payment.getTotalPrice());
        assertEquals(paymentMode, payment.getPaymentMode());
        assertEquals(paymentStatus, payment.getPaymentStatus());
        assertEquals(card, payment.getCard());
        assertEquals(paymentDate, payment.getPaymentDate());
        assertEquals(userName, payment.getUserName());
        assertEquals(cartId, payment.getCartId());
        assertEquals(userprofile, payment.getUserprofile());
    }

    @Test
    void testPaymentToString() {
        // Given
        Payment payment = new Payment();
        payment.setPaymentId("1");
        payment.setTotalPrice(100.0);
        payment.setPaymentMode("Credit Card");
        payment.setPaymentStatus("Completed");
        payment.setCard(new Card());
        payment.setPaymentDate(new Date());
        payment.setUserName("John Doe");
        payment.setCartId("cart123");
        payment.setUserprofile(new User());

        // When
        String result = payment.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("100.0"));
        assertTrue(result.contains("Credit Card"));
        assertTrue(result.contains("Completed"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("cart123"));
    }
}
