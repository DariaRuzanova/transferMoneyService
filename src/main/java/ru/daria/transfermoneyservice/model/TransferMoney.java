package ru.daria.transfermoneyservice.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.lang.ref.SoftReference;

public class TransferMoney {
    @NotEmpty
    @Length(min = 16)
    public String fromCardNumber;

    @NotEmpty
    @Length(min = 4)
    public String fromCardValidTill;

    @NotEmpty
    @Length(min = 3)
    public String fromCardCVC;

    @NotEmpty
    @Length(min = 16)
    public String toCardNumber;

    @Positive
    @NotNull
    Amount amount;
}
