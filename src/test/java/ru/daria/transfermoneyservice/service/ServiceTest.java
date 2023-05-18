package ru.daria.transfermoneyservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.NotEnoughMoneyException;
import ru.daria.transfermoneyservice.exception.ExceptionUnknownCard;
import ru.daria.transfermoneyservice.logger.Logger;
import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.ConfirmOperation;
import ru.daria.transfermoneyservice.model.TransferMoney;
import ru.daria.transfermoneyservice.model.TransferResult;
import ru.daria.transfermoneyservice.repository.CardRepository;

import java.util.Objects;

@SpringBootTest
public class ServiceTest {
    private CardService cardService;

    @BeforeEach
    void init() {
        Logger logger = new Logger();
        CardRepository cardRepository = new CardRepository(logger);
        cardService = new CardService(cardRepository,logger);
    }


    @Test
    void transferTest_exceptionUnknownCard() {
        TransferMoney transferMoney = new TransferMoney(
                "null",
                "05.30",
                "115",
                "2200564512341234",
                new Amount(100000, "RU"));
        Assertions.assertThrowsExactly(ExceptionUnknownCard.class, () -> cardService.transfer(transferMoney));


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
        ResponseEntity<TransferResult> result = cardService.transfer(transferMoney);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals("0", result.getBody().operationId);
    }

    @Test
    void confirmOperation() {
        TransferMoney transferMoney = new TransferMoney(
                "2200220022002200",
                "05/30",
                "115",
                "2200564512341234",
                new Amount(10000, "RU"));
        ResponseEntity<TransferResult> result = cardService.transfer(transferMoney);
        String id = Objects.requireNonNull(result.getBody()).operationId;
        String code = "0000";

        ConfirmOperation confirmOperation = new ConfirmOperation(id, code);
        var response = cardService.confirmOperation(confirmOperation);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
