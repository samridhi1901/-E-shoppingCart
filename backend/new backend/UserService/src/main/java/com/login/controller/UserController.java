package com.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.security.Jwtutil;
import com.login.service.CustomUserDetailsService;
import com.login.service.UserServiceImpl;

import jakarta.validation.Valid;


//@CrossOrigin("*")
@RestController
@Validated
@RequestMapping("/profiles")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private Jwtutil jwtutil;
	
	@Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid User user) throws UserAlreadyPresentException {
        user.setProfileId(userService.genrateUniqueRandomId());
        if (userService.registerUser(user)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("emailId", user.getEmailId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Map.of("error", "Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody @Valid AuthDto loginUser) throws InvalidCredentialException {
//    	   return ResponseEntity.ok(userService.login(loginUser));
//    }

	@PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid AuthDto loginUser) throws InvalidCredentialException {
        logger.info("Attempting to log in user: {}", loginUser.getEmail());
        try {
            String encodedPassword = encoder.encode(loginUser.getPassword());
            userDto responseObj = userService.login(loginUser);
            loginUser.setPassword(encodedPassword);
            logger.info("User logged in successfully: {}", loginUser.getEmail());
//            User user = userService.getByEmailId(loginUser.getEmail());
//            String id = user.getProfileId();
            return generateResponse("Login successful", HttpStatus.OK, responseObj);
        } catch (InvalidCredentialException e) {
            logger.error("Invalid credentials for user: {}", loginUser.getEmail(), e);
            throw e;
        }
    }

   

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody userDto user) {
        jwtutil.validateToken(user.getAccessToken());
        return ResponseEntity.ok("Token is valid");
    }
    
    
    @PutMapping("/{profileId}")
    public ResponseEntity<Void> updateProfile(@PathVariable String profileId, @RequestBody User user) {
        User existingProfile = userService.getByProfileId(profileId);
        if (existingProfile != null) {
            user.setProfileId(existingProfile.getProfileId());
            userService.updateProfile(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllProfiles() {
        List<User> profiles = userService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
    
    @GetMapping("/getUser/{profileId}")
    public ResponseEntity<User> getByProfileId(@PathVariable String profileId) throws UserNotFoundException {
        User profile = userService.getByProfileId(profileId);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
        	throw new UserNotFoundException("Profile with ID " + profileId + " not found");
        }
    }
    
    @GetMapping("/by-mobile-number/{mobileNumber}")
    public ResponseEntity<User> getProfileByMobileNumber(@PathVariable long mobileNumber) {
        User profile = userService.findByMobileNumber(mobileNumber);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

}
    
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable String profileId) {
        User existingProfile = userService.getByProfileId(profileId);
        if (existingProfile != null) {
            userService.deleteProfile(profileId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getfullName/{fullName}")
    public ResponseEntity<?> getProfileByFullName(@PathVariable String fullName) {
        User profile = userService.getByFullName(fullName);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found with full name: " + fullName);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }
    }

    
    private ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus, userDto responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("Message", message);
        map.put("Status", httpStatus.value());
        map.put("userId", responseObj.getUserId());
        map.put("username", responseObj.getUsername());
        map.put("Token", responseObj.getAccessToken());
        map.put("role",responseObj.getRole());
 
        return new ResponseEntity<>(map, httpStatus);
    }
}
