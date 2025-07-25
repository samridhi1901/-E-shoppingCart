package com.cg.estore.orderservice.servicetests;

import com.cg.estore.orderservice.entity.*;
import com.cg.estore.orderservice.repository.OrderRepository;
import com.cg.estore.orderservice.serviceImpl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartFeignClient cartFeignClient;

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        List<OrderDetail> mockOrders = Arrays.asList(new OrderDetail(), new OrderDetail());
        when(orderRepository.findAll()).thenReturn(mockOrders);

        List<OrderDetail> result = orderService.getAllOrders();

        assertEquals(mockOrders.size(), result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testPlaceOrder() {
        OrderDetail input = new OrderDetail(
            0, "", "Product1", 2, "1234567890", "123 Street", "profile123", "cart001", "txn001", "John Doe"
        );

        User mockUser = new User();
        mockUser.setProfileId("profile123");

        Cart mockCart = new Cart(); 
        mockCart.setCartId("cart001");

        Product mockProduct = new Product();
        mockProduct.setProductId("P001");
        mockProduct.setProductName("Product1");
        mockProduct.setTotalPrice(100.0);

        when(userFeignClient.getProfileByFullName("John Doe")).thenReturn(mockUser);
        when(productFeignClient.getProductsByName("Product1")).thenReturn(Arrays.asList(mockProduct));

        OrderDetail expectedOrder = new OrderDetail(
            200.0,
            "ORDER PLACED",
            "Product1",
            2,
            "1234567890",
            "123 Street",
            "profile123",
            "cart001",
            "txn001",
            "John Doe"
        );

        when(orderRepository.save(any(OrderDetail.class))).thenReturn(expectedOrder);

        OrderDetail result = orderService.placeOrder(input);

        assertNotNull(result);
        assertEquals("Product1", result.getProductName());
        assertEquals(2, result.getQuantity());
        assertEquals(200.0, result.getTotalPrice());
        assertEquals("profile123", result.getProfileId());

        verify(orderRepository, times(1)).save(any(OrderDetail.class));
        verify(userFeignClient).getProfileByFullName("John Doe");
        verify(productFeignClient).getProductsByName("Product1");
    }

    @Test
    void testGetOrderById() {
        Long orderId = 123L;
        OrderDetail mockOrder = new OrderDetail();
        mockOrder.setOrderId(orderId);

        when(orderRepository.findById(orderId.toString())).thenReturn(Optional.of(mockOrder));

        OrderDetail result = orderService.getOrderById(orderId.toString());

        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(orderRepository).findById(orderId.toString());
    }

    @Test
    void testGetOrderByIdWithInvalidId() {
        String orderId = "invalid";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(orderId);
        });

        assertTrue(exception.getMessage().contains("Order not found"));
    }

    @Test
    void testDeleteOrderByIdWhenExists() {
        String orderId = "123";
        when(orderRepository.existsById(orderId)).thenReturn(true);

        orderService.deleteOrderById(orderId);

        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void testDeleteOrderByIdWhenNotExists() {
        String orderId = "invalid";
        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            orderService.deleteOrderById(orderId);
        });

        verify(orderRepository, never()).deleteById(orderId);
    }
}
