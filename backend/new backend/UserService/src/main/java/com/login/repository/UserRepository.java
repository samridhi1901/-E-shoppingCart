package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByProfileId(String profileId);
	User findByFullName(String fullName);
	User findByFullNameOrEmailId(String fullName, String emailId);
	User findByEmailId(String emailId);
	User findByMobileNumber(Long mobileNumber);
	void deleteByProfileId(String profileId);

	User findByfullNameOrEmailId(String username, String username1);
}
