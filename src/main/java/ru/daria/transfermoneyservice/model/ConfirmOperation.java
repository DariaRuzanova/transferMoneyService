package ru.daria.transfermoneyservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConfirmOperation {
    private long id;


    @NotNull
    @Min(3)
    private String code;

    public String getCode() {
        return code;
    }

    public long getId() {
        return id;
    }
}
