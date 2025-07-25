package com.cg.estore.orderservice.entitytests;

import com.cg.estore.orderservice.entity.OrderDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrdersTests {

    @Test
    public void testParameterizedConstructor() {
        double totalPrice = 100.0;
        String orderStatus = "Pending";
        String productName = "Product1";
        int quantity = 2;
        String contactNumber = "1234567890";
        String fulladdress = "123 Street, City";
        String profileId = "profile123";
        String cartId = "cart123";
        String transactionId = "txn123";
        String fullName = "John Doe";

        OrderDetail order = new OrderDetail(
                totalPrice,
                orderStatus,
                productName,
                quantity,
                contactNumber,
                fulladdress,
                profileId,
                cartId,
                transactionId,
                fullName
        );

        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(orderStatus, order.getOrderStatus());
        assertEquals(productName, order.getProductName());
        assertEquals(quantity, order.getQuantity());
        assertEquals(contactNumber, order.getContactNumber());
        assertEquals(fulladdress, order.getFulladdress());
        assertEquals(profileId, order.getProfileId());
        assertEquals(cartId, order.getCartId());
        assertEquals(transactionId, order.getTransactionId());
        assertEquals(fullName, order.getFullName());
    }

    @Test
    public void testDefaultConstructor() {
        OrderDetail order = new OrderDetail();
        assertNull(order.getOrderId());
        assertEquals(0.0, order.getTotalPrice());
        assertNull(order.getOrderStatus());
        assertNull(order.getProductName());
        assertEquals(0, order.getQuantity());
        assertNull(order.getContactNumber());
        assertNull(order.getFulladdress());
        assertNull(order.getProfileId());
        assertNull(order.getCartId());
        assertNull(order.getTransactionId());
        assertNull(order.getFullName());
    }

    @Test
    public void testSettersAndGetters() {
        OrderDetail order = new OrderDetail();

        order.setOrderId(1L);  // Use Long value
        order.setTotalPrice(200.0);
        order.setOrderStatus("Confirmed");
        order.setProductName("New Product");
        order.setQuantity(3);
        order.setContactNumber("9876543210");
        order.setFulladdress("456 Avenue");
        order.setProfileId("profile456");
        order.setCartId("cart456");
        order.setTransactionId("txn456");
        order.setFullName("Jane Smith");

        assertEquals(1L, order.getOrderId());
        assertEquals(200.0, order.getTotalPrice());
        assertEquals("Confirmed", order.getOrderStatus());
        assertEquals("New Product", order.getProductName());
        assertEquals(3, order.getQuantity());
        assertEquals("9876543210", order.getContactNumber());
        assertEquals("456 Avenue", order.getFulladdress());
        assertEquals("profile456", order.getProfileId());
        assertEquals("cart456", order.getCartId());
        assertEquals("txn456", order.getTransactionId());
        assertEquals("Jane Smith", order.getFullName());
    }
}
