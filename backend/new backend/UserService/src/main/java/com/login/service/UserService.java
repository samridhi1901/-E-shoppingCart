package com.login.service;

import java.util.List;


import com.login.dto.AuthDto;
import com.login.dto.userDto;
import com.login.exception.InvalidCredentialException;
import com.login.exception.UserAlreadyPresentException;
import com.login.model.User;

public interface UserService {

	public Boolean registerUser(User user) throws UserAlreadyPresentException;

	public String genrateUniqueRandomId();
	
	public userDto login(AuthDto loginUser) throws InvalidCredentialException;
	List<User> getAllProfiles();
	User getByProfileId(String profileId);
	User getByFullName(String fullName);
    void updateProfile(User user);
	User findByMobileNumber(Long mobileNumber);
    void deleteProfile(String profileId);
	
}
