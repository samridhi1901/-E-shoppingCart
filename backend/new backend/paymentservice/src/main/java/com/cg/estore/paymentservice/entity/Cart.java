package com.cg.estore.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    private String cartId;

    @NotEmpty(message = "Product ID cannot be empty")
    private String productId;

    @Positive(message = "Total price must be positive")
    private double totalPrice;

    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    @Positive(message = "Quantity must be positive")
    private int quantity;
}
