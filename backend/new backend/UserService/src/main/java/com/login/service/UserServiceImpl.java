package com.login.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.security.Jwtutil;

@Service
public class UserServiceImpl implements UserService{

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	Jwtutil jwtutil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean  registerUser(User user) throws UserAlreadyPresentException {
		List<User> userList = userRepository.findAll();
		if(userList.isEmpty()) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setRole("USER");
				User u = userRepository.save(user);
				return true;
		}
		else {
			String email = user.getEmailId();
			boolean flag = false;
			for(User u: userList) {
				if(u.getEmailId().equalsIgnoreCase(email)) {
					flag = true;
				}
			}
			if(flag) {
				logger.info("User already exists...");
				throw new UserAlreadyPresentException("Can't add user. Already exits.");
			}
			else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				User u =userRepository.save(user);
				return true;
			}
		}
	}

	@Override
	public String genrateUniqueRandomId() {
		Random random = new Random();
		String id = String.valueOf(random.nextInt(999999)+1);
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			id = genrateUniqueRandomId();
		}
		
		return id; 
	}

	@Override
	public userDto login(AuthDto loginUser) throws InvalidCredentialException {
	 
 	   String str = null;
 	   try {
	 	   Authentication authentication =  authenticationManager.authenticate(
	 			   new UsernamePasswordAuthenticationToken(
	                        loginUser.getEmail(),
	                        loginUser.getPassword()
	                ));
	 	   if(authentication.isAuthenticated()) {
	 		   User u = userRepository.findByfullNameOrEmailId(loginUser.getEmail(), loginUser.getEmail());
	 		   str = jwtutil.generateToken(u.getFullName(),u.getRole(),u.getProfileId(),u.getEmailId());
	 		   userDto loginResponse=new userDto();
	 		   loginResponse.setUserId(u.getProfileId());
	 		   loginResponse.setAccessToken(str);
	 		   loginResponse.setRole(u.getRole());
	 		   loginResponse.setUsername(u.getFullName());
	 	   	   return loginResponse;
	 	   }else {
	 		  throw new InvalidCredentialException("Invalid Email or password");
	 	   }
 	   }
 	   catch(BadCredentialsException e) {
 		   throw new InvalidCredentialException("Invalid Email or password");
 	   }
	}
	
	 @Override
	  public void updateProfile(User user) {
	        // Use the repository's save method to update the profile
	        userRepository.save(user);
	    }
	
	@Override
	public List<User> getAllProfiles() {
		return userRepository.findAll();
	}
	
	 @Override
	    public User getByProfileId(String profileId) {
	        return userRepository.findByProfileId(profileId);
	    }

	    @Override
	    public User findByMobileNumber(Long mobileNumber) {
	        return userRepository.findByMobileNumber(mobileNumber);
	    }

	    @Override
	    public void deleteProfile(String profileId) {
	        userRepository.deleteByProfileId(profileId);
	    }

	    @Override
	    public User getByFullName(String fullName) {
	        return userRepository.findByFullName(fullName);
	    }
	    
//	    @Override
//	    public User getByEmailId(String emailId) {
//	        return userRepository.findByEmailId(emailId);
//	    }

}
