package com.cg.estore.productservice.entitytests;
import org.junit.jupiter.api.Test;

import com.cg.estore.productservice.entity.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductTests {

    @Test
    void testProductConstructor() {
        // Given
        String productId = "1";
        String productType = "Electronics";
        String productName = "Laptop";
        String category = "Computers";
        String image = "laptop_image.jpg";
        String description = "Powerful laptop with high-end specifications";
        int quantity = 10;
        double totalPrice = 1200.0;

        // When
        Product product = new Product(productId, productType, productName, category, image, description, quantity, totalPrice);

        // Then
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
        assertEquals(productType, product.getProductType());
        assertEquals(productName, product.getProductName());
        assertEquals(category, product.getCategory());
        assertEquals(image, product.getImage());
        assertEquals(description, product.getDescription());
        assertEquals(quantity, product.getQuantity());
        assertEquals(totalPrice, product.getTotalPrice());
    }

    @Test
    void testProductGettersAndSetters() {
        // Given
        Product product = new Product();

        // When
        product.setProductId("2");
        product.setProductType("Clothing");
        product.setProductName("T-Shirt");
        product.setCategory("Apparel");
        product.setImage("tshirt_image.jpg");
        product.setDescription("Comfortable cotton T-shirt");
        product.setQuantity(50);
        product.setTotalPrice(25.0);

        // Then
        assertEquals("2", product.getProductId());
        assertEquals("Clothing", product.getProductType());
        assertEquals("T-Shirt", product.getProductName());
        assertEquals("Apparel", product.getCategory());
        assertEquals("tshirt_image.jpg", product.getImage());
        assertEquals("Comfortable cotton T-shirt", product.getDescription());
        assertEquals(50, product.getQuantity());
        assertEquals(25.0, product.getTotalPrice());
    }

    @Test
    void testProductToString() {
        // Given
        Product product = new Product("3", "Electronics", "Smartphone", "Mobile", "phone_image.jpg", "High-end smartphone", 20, 800.0);

        // When
        String toStringResult = product.toString();

        // Then
        assertTrue(toStringResult.contains("productId=3"));
        assertTrue(toStringResult.contains("productType=Electronics"));
        assertTrue(toStringResult.contains("productName=Smartphone"));
        assertTrue(toStringResult.contains("category=Mobile"));
        assertTrue(toStringResult.contains("image=phone_image.jpg"));
        assertTrue(toStringResult.contains("description=High-end smartphone"));
        assertTrue(toStringResult.contains("quantity=20"));
        assertTrue(toStringResult.contains("totalPrice=800.0"));
    }
}
