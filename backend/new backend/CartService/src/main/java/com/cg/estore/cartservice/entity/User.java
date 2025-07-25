package com.cg.estore.cartservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotBlank(message = "Profile ID must not be blank")
    private String profileId;

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email ID must not be blank")
    private String emailId;

    @NotNull(message = "Mobile number must not be null")
    private Long mobileNumber;

    @NotBlank(message = "Role must not be blank")
    private String role;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
