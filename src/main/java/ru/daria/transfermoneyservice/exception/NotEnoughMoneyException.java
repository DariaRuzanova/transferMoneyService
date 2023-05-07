package ru.daria.transfermoneyservice.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String s) {
        super(s);
    }
}
