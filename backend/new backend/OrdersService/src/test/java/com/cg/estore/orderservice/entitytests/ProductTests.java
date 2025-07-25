package com.cg.estore.orderservice.entitytests;
 
import com.cg.estore.orderservice.entity.Product;
import org.junit.jupiter.api.Test;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class ProductTests {
 
    @Test
    public void testParameterizedConstructor() {
        String productId = "P001";
        String productName = "TestProduct";
 
        Product product = new Product(productId, productName, 0);
 
        assertEquals(productId, product.getProductId());
        assertEquals(productName, product.getProductName());
    }
 
    @Test
    public void testDefaultConstructor() {
        Product product = new Product();
        assertNull(product.getProductId());
        assertNull(product.getProductName());
        assertEquals(0.0, product.getTotalPrice());
    }
 
    @Test
    public void testSettersAndGetters() {
        Product product = new Product();
        product.setProductId("P002");
        product.setProductName("AnotherProduct");
        product.setTotalPrice(199.99);
 
        assertEquals("P002", product.getProductId());
        assertEquals("AnotherProduct", product.getProductName());
        assertEquals(199.99, product.getTotalPrice());
    }
}
 
 