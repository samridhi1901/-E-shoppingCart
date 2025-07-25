package com.cg.estore.paymentservice.controllertests;

import com.cg.estore.paymentservice.controller.PaymentController;
import com.cg.estore.paymentservice.entity.Payment;
import com.cg.estore.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPayment() {
        // Given
        Payment payment = new Payment();
        when(paymentService.addPayment(payment, "John Doe", "cart123")).thenReturn(payment);

        // When
        Payment result = paymentController.addPayment(payment, "John Doe", "cart123");

        // Then
        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentById() {
        // Given
        Payment payment = new Payment();
        when(paymentService.getPaymentById(anyString())).thenReturn(payment);

        // When
        Payment result = paymentController.getPaymentById("payment123");

        // Then
        assertEquals(payment, result);
    }
}
