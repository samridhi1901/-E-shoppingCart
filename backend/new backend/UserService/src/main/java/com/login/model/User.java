package com.login.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	private String profileId;
	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
	}
	@NotBlank(message = "Please enter user name")
	private String fullName;

	@Email(message = "Please enter valid email")
	private String emailId;

	private String password;
	
	private Long mobileNumber;

	private String gender;
	private String role;
	


//	@Embedded
//	private Address address;

	private String image;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	
	@PreUpdate
	protected void onUpdate() {
		lastModifiedDate = new Date();
	}
}
