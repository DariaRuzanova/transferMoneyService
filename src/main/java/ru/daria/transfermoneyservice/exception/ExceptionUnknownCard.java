package ru.daria.transfermoneyservice.exception;

public class ExceptionUnknownCard extends RuntimeException {
    public ExceptionUnknownCard(String s) {
        super(s);
    }
}
