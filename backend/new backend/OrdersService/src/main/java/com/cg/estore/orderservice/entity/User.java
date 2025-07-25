package com.cg.estore.orderservice.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String profileId;

    private String fullName;
    private String emailId;
    private Long mobileNumber;
    
//    private String about;
//    private Date dateOfBirth;
//    private String gender;
    private String role;
    private String password;

   
}

