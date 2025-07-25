package com.cg.estore.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @Column(name = "address_id")
    private String addressId;

    @NotBlank(message = "Customer ID cannot be blank")
    private String customerID;

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNumber;

    @Positive(message = "Flat number must be positive")
    private int flatNumber;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @Positive(message = "Pincode must be positive")
    private int pincode;

    @NotBlank(message = "State cannot be blank")
    private String state;
}
