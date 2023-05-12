package ru.daria.transfermoneyservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.NotEnoughMoneyException;
import ru.daria.transfermoneyservice.exception.exceptionUnknownCard;
import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.Card;
import ru.daria.transfermoneyservice.model.ConfirmOperation;
import ru.daria.transfermoneyservice.model.TransferMoney;
import ru.daria.transfermoneyservice.repository.CardRepository;

@SpringBootTest
public class ServiceTest {
    private TransferMoney transferMoney;
    private CardRepository cardRepository;
    private CardService cardService;

    @BeforeEach
    void init() {
        cardRepository = new CardRepository();
        cardService = new CardService(cardRepository);
    }


    @Test
    void transferTest_exceptionUnknownCard() {
        TransferMoney transferMoney = new TransferMoney(
                "null",
                "05.30",
                "115",
                "2200564512341234",
                new Amount(100000, "RU"));
        Assertions.assertThrowsExactly(exceptionUnknownCard.class, () -> cardService.transfer(transferMoney));


    }

    @Test
    void transferTest_IncorrectDataEntry() {
        TransferMoney transferMoney = new TransferMoney(
                "2200220022002200",
                "10.30",
                "115",
                "2200564512341234",
                new Amount(100000, "RU"));
        Assertions.assertThrowsExactly(IncorrectDataEntry.class, () -> cardService.transfer(transferMoney));


    }

    @Test
    void transferTest_NotEnoughMoneyException() {
        TransferMoney transferMoney = new TransferMoney(
                "2200220022002200",
                "05/30",
                "115",
                "2200564512341234",
                new Amount(120000, "RU"));
        Assertions.assertThrowsExactly(NotEnoughMoneyException.class, () -> cardService.transfer(transferMoney));
    }

    @Test
    void transferTest() {
        TransferMoney transferMoney = new TransferMoney(
                "2200220022002200",
                "05/30",
                "115",
                "2200564512341234",
                new Amount(10000, "RU"));
        Assertions.assertEquals(0, cardService.transfer(transferMoney));
    }

    @Test
    void confirmOperation() {
        TransferMoney transferMoney = new TransferMoney(
                "2200220022002200",
                "05/30",
                "115",
                "2200564512341234",
                new Amount(10000, "RU"));
        long id = cardService.transfer(transferMoney);
        String code = "0000";

        ConfirmOperation confirmOperation = new ConfirmOperation(id, code);
        var response = cardService.confirmOperation(confirmOperation);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
