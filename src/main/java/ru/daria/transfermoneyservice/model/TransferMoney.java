package ru.daria.transfermoneyservice.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
public class TransferMoney {
    @NotEmpty
    @Length(min = 16)
    public String cardFromNumber;

    @NotEmpty
    @Length(min = 4)
    public String cardFromValidTill;

    @NotEmpty
    @Length(min = 3)
    public String cardFromCVV;

    @NotEmpty
    @Length(min = 16)
    public String cardToNumber;

    Amount amount;


    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }
}

