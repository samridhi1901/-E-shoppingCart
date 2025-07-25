package com.cg.estore.paymentservice.servicetests;

import com.cg.estore.paymentservice.entity.Cart;
import com.cg.estore.paymentservice.entity.Payment;
import com.cg.estore.paymentservice.entity.User;
import com.cg.estore.paymentservice.exception.CustomerNotFoundException;
import com.cg.estore.paymentservice.exception.OrderNotFoundException;
import com.cg.estore.paymentservice.exception.PaymentNotFoundException;
import com.cg.estore.paymentservice.repository.PaymentRepository;
import com.cg.estore.paymentservice.service.CartFeignClient;
import com.cg.estore.paymentservice.service.PaymentServiceImpl;
import com.cg.estore.paymentservice.service.UserFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private CartFeignClient cartFeignClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPayment_Success() {
        // Given
        Payment payment = new Payment();
        User user = new User("1", "John Doe", "john.doe@example.com", 1234567890L, "Customer", "password");
        Cart cart = new Cart("1", "product1", 100.0, "Product 1", 2);

        when(userFeignClient.getProfileByFullName(anyString())).thenReturn(user);
        when(cartFeignClient.getCartById(anyString())).thenReturn(cart);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // When
        Payment result = paymentService.addPayment(payment, "John Doe", "1");

        // Then
        assertNotNull(result);
        verify(userFeignClient, times(1)).getProfileByFullName(anyString());
        verify(cartFeignClient, times(1)).getCartById(anyString());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_CustomerNotFound() {
        // Given
        Payment payment = new Payment();
        when(userFeignClient.getProfileByFullName(anyString())).thenReturn(null);

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> paymentService.addPayment(payment, "John Doe", "1"));
        verify(userFeignClient, times(1)).getProfileByFullName(anyString());
        verify(cartFeignClient, times(0)).getCartById(anyString());
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testAddPayment_OrderNotFound() {
        // Given
        Payment payment = new Payment();
        User user = new User("1", "John Doe", "john.doe@example.com", 1234567890L, "Customer", "password");

        when(userFeignClient.getProfileByFullName(anyString())).thenReturn(user);
        when(cartFeignClient.getCartById(anyString())).thenReturn(null);

        // When & Then
        assertThrows(OrderNotFoundException.class, () -> paymentService.addPayment(payment, "John Doe", "1"));
        verify(userFeignClient, times(1)).getProfileByFullName(anyString());
        verify(cartFeignClient, times(1)).getCartById(anyString());
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById_Success() {
        // Given
        String paymentId = "1";
        Payment payment = new Payment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        // When
        Payment result = paymentService.getPaymentById(paymentId);

        // Then
        assertNotNull(result);
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetPaymentById_NotFound() {
        // Given
        String paymentId = "1";
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PaymentNotFoundException.class, () -> paymentService.getPaymentById(paymentId));
        verify(paymentRepository, times(1)).findById(paymentId);
    }
}
