package com.cg.estore.orderservice.servicetests;

import com.cg.estore.orderservice.entity.OrderDetail;
import com.cg.estore.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindFirstByOrderByOrderIdDesc() {
        OrderDetail order1 = new OrderDetail(
            100.0, "Placed", "Product1", 2, "1234567890",
            "Address1", "P001", "CART001", "TXN001", "John Doe"
        );

        OrderDetail order2 = new OrderDetail(
            150.0, "Placed", "Product2", 3, "1234567890",
            "Address2", "P002", "CART002", "TXN002", "Jane Doe"
        );

        orderRepository.save(order1);
        orderRepository.save(order2);

        OrderDetail latestOrder = orderRepository.findFirstByOrderByOrderIdDesc();

        assertNotNull(latestOrder);
        assertEquals(order2.getProductName(), latestOrder.getProductName());
    }

    @Test
    public void testFindByOrderId() {
        OrderDetail order = new OrderDetail(
            120.0, "Delivered", "Product3", 4, "1234567890",
            "Address3", "P003", "CART003", "TXN003", "Alice"
        );

        order = orderRepository.save(order); // save & get generated ID

        Optional<OrderDetail> retrievedOrder = orderRepository.findByOrderId(order.getOrderId());

        assertTrue(retrievedOrder.isPresent());
        assertEquals(order.getOrderId(), retrievedOrder.get().getOrderId());
    }

    @Test
    public void testDeleteByOrderId() {
        OrderDetail order = new OrderDetail(
            90.0, "Pending", "Product4", 1, "1234567890",
            "Address4", "P004", "CART004", "TXN004", "Bob"
        );

        order = orderRepository.save(order);
        Long orderId = order.getOrderId();

        orderRepository.deleteByOrderId(orderId);

        Optional<OrderDetail> deletedOrder = orderRepository.findByOrderId(orderId);
        assertFalse(deletedOrder.isPresent());
    }
}
