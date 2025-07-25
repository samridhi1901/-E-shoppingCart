package com.cg.estore.producservice.controllertests;

import com.cg.estore.productservice.controller.ProductController;
import com.cg.estore.productservice.entity.Product;
import com.cg.estore.productservice.exception.ProductNotFoundException;
import com.cg.estore.productservice.service.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void addProduct() {
        // Given
        Product product = new Product();
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        // When
        ResponseEntity<Product> response = productController.addProduct(product);

        // Then
        assertEquals(product, response.getBody());
    }

    @Test
    void getAllProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(products);

        // When
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Then
        assertEquals(products, response.getBody());
    }

    @Test
    void getProductById() {
        // Given
        String productId = "1";
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // When
        ResponseEntity<Product> response = productController.getProductById(productId);

        // Then
        assertEquals(product, response.getBody());
    }

    @Test
    void getProductById_ProductNotFound() {
        // Given
        String productId = "1";
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(productId));
    }

    // Similar tests for other methods...

    @Test
    void deleteProductById() {
        // Given
        String productId = "1";

        // When
        ResponseEntity<Void> response = productController.deleteProductById(productId);

        // Then
        verify(productService, times(1)).deleteProductById(productId);
    }
}
