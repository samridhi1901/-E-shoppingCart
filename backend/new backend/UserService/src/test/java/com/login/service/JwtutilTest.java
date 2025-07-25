package com.login.service;



import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.login.security.Jwtutil;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtutilTest {

    private Jwtutil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new Jwtutil();
    }

    @Test
    public void testGenerateAndValidateToken() {
        String token = jwtUtil.generateToken("testuser", "USER", "123", "test@example.com");

        // Validate that no exception is thrown
        assertDoesNotThrow(() -> jwtUtil.validateToken(token));

        // Extract fields and validate
        assertEquals("testuser", jwtUtil.getUsernameFromToken(token));
        assertNotNull(jwtUtil.getExpirationDateFromToken(token));
    }

    @Test
    public void testExtractClaims() {
        String token = jwtUtil.generateToken("john_doe", "ADMIN", "42", "john@example.com");

        assertEquals("john_doe", jwtUtil.getUsernameFromToken(token));
        Date expiration = jwtUtil.getExpirationDateFromToken(token);
        assertTrue(expiration.after(new Date()), "Expiration date should be in the future");
    }

    @Test
    public void testValidateTamperedToken() {
        String token = jwtUtil.generateToken("user", "USER", "1", "user@example.com");

        // Tamper the token by removing characters
        String tampered = token.substring(0, token.length() - 10);

        assertThrows(JwtException.class, () -> jwtUtil.validateToken(tampered));
    }

    @Test
    public void testTokenExpiration() throws InterruptedException {
        // Overriding the validity for this test (simulate expired token)
        Jwtutil shortLivedJwt = new Jwtutil() {
            @Override
            public String generateToken(String userName, String role, String id, String email) {
                return Jwts.builder()
                        .setSubject(userName)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000)) // 1 second
                        .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secret)), io.jsonwebtoken.SignatureAlgorithm.HS512)
                        .compact();
            }
        };

        String token = shortLivedJwt.generateToken("shortLivedUser", "USER", "99", "short@example.com");
        Thread.sleep(1500); // Wait for token to expire

        assertThrows(ExpiredJwtException.class, () -> shortLivedJwt.validateToken(token));
    }
}
