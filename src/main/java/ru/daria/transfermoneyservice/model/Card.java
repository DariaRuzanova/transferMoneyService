package ru.daria.transfermoneyservice.model;

public record Card(String cardNumber, String cardValidTill, String cardCVV, Amount amount) {
}