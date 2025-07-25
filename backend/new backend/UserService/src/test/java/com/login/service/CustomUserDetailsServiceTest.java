package com.login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.login.model.User;
import com.login.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setFullName("TestUser");
        testUser.setEmailId("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("ROLE_USER");
        testUser.setProfileId("123");
    }

    @Test
    public void testLoadUserByUsername_Success() {
        when(userRepository.findByfullNameOrEmailId("TestUser", "TestUser")).thenReturn(testUser);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("TestUser");

        assertNotNull(userDetails);
        assertEquals("TestUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByfullNameOrEmailId("unknown", "unknown")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown");
        });
    }
}
