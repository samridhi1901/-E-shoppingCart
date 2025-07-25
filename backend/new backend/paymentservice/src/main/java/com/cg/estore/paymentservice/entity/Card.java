package com.cg.estore.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {

    @Id
    private String cardNumber; // Assuming cardNumber is unique and can serve as a primary key

    private String cardHolderName;
    private String expirationDate;
    private String cvvNumber;
}
