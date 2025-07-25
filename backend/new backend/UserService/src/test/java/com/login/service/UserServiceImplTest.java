package com.login.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.security.Jwtutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private Jwtutil jwtutil;

    @InjectMocks private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setFullName("TestUser");
        testUser.setEmailId("test@example.com");
        testUser.setPassword("password123");
        testUser.setRole("ROLE_USER");
        testUser.setProfileId("123456");
    }

    @Test
    public void testRegisterUser_Success_NewUser() throws UserAlreadyPresentException {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        boolean result = userService.registerUser(testUser);

        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        User newUser = new User();
        newUser.setEmailId("test@example.com");

        assertThrows(UserAlreadyPresentException.class, () -> userService.registerUser(newUser));
    }

    @Test
    public void testGenerateUniqueRandomId() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        String id = userService.genrateUniqueRandomId();

        assertNotNull(id);
        assertTrue(id.matches("\\d{1,6}"));
    }

    @Test
    public void testLogin_Success() throws InvalidCredentialException {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("test@example.com");
        authDto.setPassword("password");

        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(mockAuth);
        when(mockAuth.isAuthenticated()).thenReturn(true);

        when(userRepository.findByfullNameOrEmailId(anyString(), anyString()))
            .thenReturn(testUser);
        when(jwtutil.generateToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn("mocked_token");

        userDto response = userService.login(authDto);

        assertNotNull(response);
        assertEquals("mocked_token", response.getAccessToken());
        assertEquals("TestUser", response.getUsername());
        assertEquals("123456", response.getUserId());
        assertEquals("ROLE_USER", response.getRole());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        AuthDto authDto = new AuthDto();
        authDto.setEmail("invalid@example.com");
        authDto.setPassword("wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(InvalidCredentialException.class, () -> userService.login(authDto));
    }

    @Test
    public void testUpdateProfile() {
        when(userRepository.save(testUser)).thenReturn(testUser);
        userService.updateProfile(testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    public void testGetAllProfiles() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> result = userService.getAllProfiles();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetByProfileId() {
        when(userRepository.findByProfileId("123456")).thenReturn(testUser);
        User result = userService.getByProfileId("123456");
        assertEquals("TestUser", result.getFullName());
    }

    @Test
    public void testFindByMobileNumber() {
        testUser.setMobileNumber(9876543210L);
        when(userRepository.findByMobileNumber(9876543210L)).thenReturn(testUser);
        User result = userService.findByMobileNumber(9876543210L);
        assertEquals(9876543210L, result.getMobileNumber());
    }

    @Test
    public void testDeleteProfile() {
        doNothing().when(userRepository).deleteByProfileId("123456");
        userService.deleteProfile("123456");
        verify(userRepository).deleteByProfileId("123456");
    }

    @Test
    public void testGetByFullName() {
        when(userRepository.findByFullName("TestUser")).thenReturn(testUser);
        User result = userService.getByFullName("TestUser");
        assertEquals("test@example.com", result.getEmailId());
    }
}
