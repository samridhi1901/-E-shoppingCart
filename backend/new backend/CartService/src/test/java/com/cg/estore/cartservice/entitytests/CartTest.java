package com.cg.estore.cartservice.entitytests;

import com.cg.estore.cartservice.entity.Cart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    void testCartConstructor() {
        // Given
        String cartId = "1";
        String productId = "prod123";
        double totalPrice = 200.0;
        String productName = "Product Name";
        int quantity = 2;

        // When
        Cart cart = new Cart();

        // Then
        assertNotNull(cart);
        assertEquals(cartId, cart.getCartId());
        assertEquals(productId, cart.getProductId());
        assertEquals(totalPrice, cart.getTotalPrice());
        assertEquals(productName, cart.getProductName());
        assertEquals(quantity, cart.getQuantity());
    }

    @Test
    void testCartSettersAndGetters() {
        // Given
        Cart cart = new Cart();

        // When
        cart.setCartId("1");
        cart.setProductId("prod123");
        cart.setTotalPrice(200.0);
        cart.setProductName("Product Name");
        cart.setQuantity(2);

        // Then
        assertEquals("1", cart.getCartId());
        assertEquals("prod123", cart.getProductId());
        assertEquals(200.0, cart.getTotalPrice());
        assertEquals("Product Name", cart.getProductName());
        assertEquals(2, cart.getQuantity());
    }
}
