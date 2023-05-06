package ru.daria.transfermoneyservice.model;

import java.util.Objects;

public class Card {
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private Amount amount;

    public Card(String cardNumber, String cardValidTill, String cardCVV, Amount amount ){
        this.cardNumber = cardNumber;
        this.cardValidTill = cardValidTill;
        this.cardCVV = cardCVV;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardValidTill() {
        return cardValidTill;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(cardValidTill, card.cardValidTill) && Objects.equals(cardCVV, card.cardCVV) && Objects.equals(amount, card.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardValidTill, cardCVV, amount);
    }
}
