package com.cg.estore.cartservice.controllertests;

import com.cg.estore.cartservice.controller.CartController;
import com.cg.estore.cartservice.entity.Cart;
import com.cg.estore.cartservice.exception.CartNotFoundException;
import com.cg.estore.cartservice.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    private Cart cart;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        cart = new Cart();
        cart.setCartId("cart1");
        cart.setProductId("product1");
        cart.setQuantity(1);
        cart.setProductName("Test Product");
        cart.setTotalPrice(100.0);
    }

    @Test
    public void testAddCartWithProduct() {
        // Note: your controller method signature is addCartWithProduct(String productId, String profileId, int quantity)
        when(cartService.addProductToCart(anyString(), anyString(), anyInt())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.addCartWithProduct("product1", "profile1", 1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

        @Test
        public void testGetAllCarts() {
        when(cartService.getAllCarts()).thenReturn(Collections.singletonList(cart));

        ResponseEntity<List<Cart>> response = cartController.getAllCarts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(cart, response.getBody().get(0));
    }

    @Test
    public void testGetCartById() {
        when(cartService.getCartById("cart1")).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.getCartById("cart1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartById_NotFound() {
        when(cartService.getCartById("cart1")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.getCartById("cart1");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    // Since updateCart is commented out in controller, no test for it here

    // Since cartTotal is commented out in controller, no test for it here

    @Test
    public void testDeleteProductFromCart_Success() {
        doNothing().when(cartService).deleteProductFromCart("cart1");

        ResponseEntity<Void> response = cartController.deleteProductFromCart("cart1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductFromCart_Failure() {
        doThrow(new RuntimeException("Something went wrong")).when(cartService).deleteProductFromCart("cart1");

        ResponseEntity<Void> response = cartController.deleteProductFromCart("cart1");

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
