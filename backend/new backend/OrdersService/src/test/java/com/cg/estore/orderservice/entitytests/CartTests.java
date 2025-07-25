package com.cg.estore.orderservice.entitytests;
 
import org.junit.jupiter.api.Test;
 
import com.cg.estore.orderservice.entity.Cart;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class CartTests {
 
    @Test
    public void testCartConstructorAndGetters() {
        String cartId = "123";
        String productId = "456";
        double totalPrice = 99.99;
        String productName = "Test Product";
        int quantity = 2;
 
        Cart cart = new Cart(cartId, productId, totalPrice, productName, quantity);
 
        assertEquals(cartId, cart.getCartId());
        assertEquals(productId, cart.getProductId());
        assertEquals(totalPrice, cart.getTotalPrice());
        assertEquals(productName, cart.getProductName());
        assertEquals(quantity, cart.getQuantity());
    }
 
    @Test
    public void testCartSetters() {
        Cart cart = new Cart();
        cart.setCartId("123");
        cart.setProductId("456");
        cart.setTotalPrice(99.99);
        cart.setProductName("Test Product");
        cart.setQuantity(2);
 
        assertEquals("123", cart.getCartId());
        assertEquals("456", cart.getProductId());
        assertEquals(99.99, cart.getTotalPrice());
        assertEquals("Test Product", cart.getProductName());
        assertEquals(2, cart.getQuantity());
    }
}
 