package com.cg.estore.paymentservice.entitytests;

import com.cg.estore.paymentservice.entity.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void testCardConstructor() {
        // Given
        String cardHolderName = "John Doe";
        String cardNumber = "1234567890123456";
        String expirationDate = "12/25";
        String cvvNumber = "123";

        // When
        Card card = new Card(cardHolderName, cardNumber, expirationDate, cvvNumber);

        // Then
        assertNotNull(card);
        assertEquals(cardHolderName, card.getCardHolderName());
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(cvvNumber, card.getCvvNumber());
    }

    @Test
    void testCardSettersAndGetters() {
        // Given
        Card card = new Card();
        String cardHolderName = "John Doe";
        String cardNumber = "1234567890123456";
        String expirationDate = "12/25";
        String cvvNumber = "123";

        // When
        card.setCardHolderName(cardHolderName);
        card.setCardNumber(cardNumber);
        card.setExpirationDate(expirationDate);
        card.setCvvNumber(cvvNumber);

        // Then
        assertEquals(cardHolderName, card.getCardHolderName());
        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(cvvNumber, card.getCvvNumber());
    }


}
