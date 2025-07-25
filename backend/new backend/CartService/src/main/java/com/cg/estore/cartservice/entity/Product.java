package com.cg.estore.cartservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @NotBlank(message = "Product ID must not be blank")
    private String productId;

    @NotBlank(message = "Product type must not be blank")
    private String productType;

    @NotBlank(message = "Product name must not be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String productName;

    @NotBlank(message = "Category must not be blank")
    private String category;

    @NotBlank(message = "Image URL must not be blank")
    private String image;

    @PositiveOrZero(message = "Total price must be zero or positive")
    private double totalPrice;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Quantity must not be null")
    @PositiveOrZero(message = "Quantity must be zero or positive")
    private int quantity;
}
