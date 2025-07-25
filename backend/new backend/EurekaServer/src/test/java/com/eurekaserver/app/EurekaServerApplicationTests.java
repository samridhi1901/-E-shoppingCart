package com.eurekaserver.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaServerApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures the application context loads without issues.
    }

    @Test
    void mainMethodTest() {
        // Act
        EurekaServerApplication.main(new String[]{});

        // Assert
        // No assertion needed; if the application fails to start, this test will fail.
    }
}
