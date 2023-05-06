package ru.daria.transfermoneyservice.exception;

public class IncorrectDataEntry extends RuntimeException {
    public IncorrectDataEntry(String s) {
        super(s);
    }
}
