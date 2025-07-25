package com.cg.estore.cartservice.servicetests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.cg.estore.cartservice.CartServiceApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull; // Import this

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CartServiceApplicationTests {

    @Autowired
    private CartServiceApplication cartServiceApplication;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
        // If there are any configuration errors, this test will fail.
    }

    @Test
    public void restTemplateBeanCreation() {
        // This test checks if the RestTemplate bean is created and injected successfully.
        // You can use this bean to make RESTful API calls in your application.
        assertNotNull(restTemplate);
    }

    // Add more test cases to validate specific functionality in your application as needed.
}
