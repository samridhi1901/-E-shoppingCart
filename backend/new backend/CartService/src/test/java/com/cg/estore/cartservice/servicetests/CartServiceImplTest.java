package com.cg.estore.cartservice.servicetests;

import com.cg.estore.cartservice.entity.Cart;
import com.cg.estore.cartservice.entity.Product;
import com.cg.estore.cartservice.exception.CartNotFoundException;
import com.cg.estore.cartservice.exception.ProductNotFoundException;
import com.cg.estore.cartservice.repository.CartRepository;
import com.cg.estore.cartservice.service.CartServiceImpl;
import com.cg.estore.cartservice.service.ProductFeignClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductFeignClient productFeignClient;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId("product1");
        product.setProductName("Test Product");
        product.setTotalPrice(100.0);
        product.setQuantity(10); // set available quantity in inventory

        cart = new Cart();
        cart.setCartId("profile1");
        List<Product> products = new ArrayList<>();
        Product cartProduct = new Product();
        cartProduct.setProductId("product1");
        cartProduct.setProductName("Test Product");
        cartProduct.setQuantity(1);
        cartProduct.setTotalPrice(100.0);
        products.add(cartProduct);
        cart.setProductsAdded(products);
        cart.setTotalPrice(100.0);
    }

    @Test
    public void testAddProductToCart_Success() {
        when(productFeignClient.getProductById("product1")).thenReturn(product);
        when(cartRepository.findById("profile1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(productFeignClient).updateProductQuantity(anyString(), anyInt());

        Cart addedCart = cartService.addProductToCart("product1", "profile1", 1);

        assertNotNull(addedCart);
        assertEquals("profile1", addedCart.getCartId());
        assertFalse(addedCart.getProductsAdded().isEmpty());
        assertEquals(1, addedCart.getProductsAdded().get(0).getQuantity());
        verify(productFeignClient, times(1)).updateProductQuantity("product1", -1);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testAddProductToCart_ProductNotFound() {
        when(productFeignClient.getProductById("product1")).thenReturn(null);

        assertThrows(ProductNotFoundException.class,
                () -> cartService.addProductToCart("product1", "profile1", 1));
    }

    @Test
    public void testGetAllCarts_Success() {
        when(cartRepository.findAll()).thenReturn(Collections.singletonList(cart));

        List<Cart> carts = cartService.getAllCarts();

        assertNotNull(carts);
        assertFalse(carts.isEmpty());
        assertEquals(1, carts.size());
    }

    @Test
    public void testGetAllCarts_NotFound() {
        when(cartRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(CartNotFoundException.class, () -> cartService.getAllCarts());
    }

    @Test
    public void testGetCartById_Found() {
        when(cartRepository.findById("profile1")).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.getCartById("profile1");

        assertNotNull(foundCart);
        assertEquals("profile1", foundCart.getCartId());
    }

    @Test
    public void testGetCartById_NotFound() {
        when(cartRepository.findById("profile1")).thenReturn(Optional.empty());

        Cart foundCart = cartService.getCartById("profile1");

        // Your implementation returns new Cart() if not found
        assertNotNull(foundCart);
        assertNull(foundCart.getCartId()); // new Cart with no ID
    }

    // Skipping updateCart tests as method is commented out in service

    // Skipping cartTotal tests as method is commented out in service

    @Test
    public void testDeleteProductFromCart_Success() {
        when(cartRepository.findById("profile1")).thenReturn(Optional.of(cart));
        doNothing().when(cartRepository).delete(cart);

        assertDoesNotThrow(() -> cartService.deleteProductFromCart("profile1"));
        verify(cartRepository, times(1)).delete(cart);
    }

    @Test
    public void testDeleteProductFromCart_NotFound() {
        when(cartRepository.findById("profile1")).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.deleteProductFromCart("profile1"));
    }
}
