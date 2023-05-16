package ru.daria.transfermoneyservice.exception;

public class IncorrectCodeException extends RuntimeException {
    public IncorrectCodeException(String s) {
        super(s);
    }
}
