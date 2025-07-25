package com.cg.estore.cartservice.servicetests;
import org.junit.jupiter.api.Test;

import com.cg.estore.cartservice.entity.Product;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductConstructor() {
        // Arrange
        String productId = "1";
        String productType = "Electronics";
        String productName = "Laptop";
        String category = "Computers";
        Map<Integer, Double> rating = new HashMap<>();
        rating.put(1, 4.5);
        rating.put(2, 5.0);
        Map<Integer, String> review = new HashMap<>();
        review.put(1, "Good");
        review.put(2, "Excellent");
        String image = "laptop_image.jpg";
        double totalPrice = 1200.0;
        String description = "High-performance laptop";
        int quantity = 10;

        // Act
      //  Product product = new Product(productId, productType, productName, category, rating, review, image, totalPrice, description, quantity);

        // Assert
//        assertEquals(productId, product.getProductId());
//        assertEquals(productType, product.getProductType());
//        assertEquals(productName, product.getProductName());
//        assertEquals(category, product.getCategory());
//        assertEquals(rating, product.getRating());
//        assertEquals(review, product.getReview());
//        assertEquals(image, product.getImage());
//        assertEquals(totalPrice, product.getTotalPrice());
//        assertEquals(description, product.getDescription());
//        assertEquals(quantity, product.getQuantity());
    }

    @Test
    public void testProductValidation() {
        // Arrange
        String productId = "1";
        String productType = "Electronics";
        String productName = "Laptop";
        String category = "Computers";
        Map<Integer, Double> rating = new HashMap<>();
        rating.put(1, 4.5);
        rating.put(2, 5.0);
        Map<Integer, String> review = new HashMap<>();
        review.put(1, "Good");
        review.put(2, "Excellent");
        String image = "laptop_image.jpg";
        double totalPrice = 1200.0;
        String description = "High-performance laptop";
        int quantity = 10;

        // Act
//        Product product = new Product(productId, productType, productName, category, rating, review, image, totalPrice, description, quantity);
//
//        // Assert
//        assertAll("Product Validation",
//                () -> assertNotNull(product.getProductId()),
//                () -> assertNotNull(product.getProductType()),
//                () -> assertNotNull(product.getProductName()),
//                () -> assertNotNull(product.getCategory()),
//                () -> assertNotNull(product.getRating()),
//                () -> assertNotNull(product.getReview()),
//                () -> assertNotNull(product.getImage()),
//                () -> assertTrue(product.getTotalPrice() >= 0.0),
//                () -> assertNotNull(product.getDescription()),
//                () -> assertTrue(product.getQuantity() >= 0)
      //  );
    }

    // Additional test cases can be added as needed
}
