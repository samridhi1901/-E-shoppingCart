package com.cg.estore.paymentservice.entitytests;

import org.junit.jupiter.api.Test;

import com.cg.estore.paymentservice.entity.User;

import static org.junit.jupiter.api.Assertions.*;



public class UserTests {

    @Test
    public void testUserConstructorAndGetters() {
        String profileId = "123";
        String fullName = "John Doe";
        String emailId = "john.doe@example.com";
        Long mobileNumber = 1234567890L;
        String role = "USER";
        String password = "password123";

        User user = new User(profileId, fullName, emailId, mobileNumber, role, password);

        assertEquals(profileId, user.getProfileId());
        assertEquals(fullName, user.getFullName());
        assertEquals(emailId, user.getEmailId());
        assertEquals(mobileNumber, user.getMobileNumber());
        assertEquals(role, user.getRole());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testUserSetters() {
        User user = new User();
        user.setProfileId("123");
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setMobileNumber(1234567890L);
        user.setRole("USER");
        user.setPassword("password123");

        assertEquals("123", user.getProfileId());
        assertEquals("John Doe", user.getFullName());
        assertEquals("john.doe@example.com", user.getEmailId());
        assertEquals(1234567890L, user.getMobileNumber());
        assertEquals("USER", user.getRole());
        assertEquals("password123", user.getPassword());
    }
}