package com.userdetails.service;

import com.login.model.User;
import com.login.service.CustomUserDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CustomUserDetailsTest {

    private User user;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setFullName("John Doe");
        user.setPassword("securePassword123");
        user.setRole("USER");

        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    public void testGetUsername() {
        assertEquals("John Doe", customUserDetails.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("securePassword123", customUserDetails.getPassword());
    }

    @Test
    public void testGetRole() {
        assertEquals("USER", customUserDetails.getRole());
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(customUserDetails.isEnabled());
    }
}
