package ru.daria.transfermoneyservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.daria.transfermoneyservice.exception.IncorrectCodeException;
import ru.daria.transfermoneyservice.exception.NotEnoughMoneyException;
import ru.daria.transfermoneyservice.logger.Logger;
import ru.daria.transfermoneyservice.model.*;
import ru.daria.transfermoneyservice.repository.CardRepository;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.ExceptionUnknownCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CardService {
    private final CardRepository dataBaseCards;
    private final Logger logger;
    AtomicLong operationId = new AtomicLong(0);

    List<PendingOperation> listPendingOperation = new ArrayList<>();

    public CardService(CardRepository dataBaseCards, Logger logger) {
        this.dataBaseCards = dataBaseCards;
        this.logger = logger;
    }

    public ResponseEntity<TransferResult> transfer(TransferMoney transferMoney) {
        String fromCardNumber = transferMoney.getCardFromNumber();
        String toCardNumber = transferMoney.getCardToNumber();
        Card fromCard = dataBaseCards.getCard(fromCardNumber);
        Card toCard = dataBaseCards.getCard(toCardNumber);

        if (fromCard == null || toCard == null) {
            throw new ExceptionUnknownCard("Неизвестный номер карты " + fromCardNumber);
        }
        if (!fromCard.cardValidTill().equals(transferMoney.cardFromValidTill) ||
                !fromCard.cardCVV().equals(transferMoney.cardFromCVV)) {
            logger.getLog("ERROR! Ошибка ввода данных карты: Error input data card");
            throw new IncorrectDataEntry("Error input data card");
        }
        if (fromCard.amount().getValue() < transferMoney.getAmount().getValue()) {
            logger.getLog("На карте недостаточно средств");
            throw new NotEnoughMoneyException("На карте недостаточно средств");
        }

        String id = String.valueOf(operationId.getAndIncrement());
        String code = "0000";
        PendingOperation operation = new PendingOperation(id, code, transferMoney);
        listPendingOperation.add(operation);

        TransferResult result = new TransferResult();
        result.operationId = id;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<TransferResult> confirmOperation(ConfirmOperation confirmOperation) {
        PendingOperation pendingOperation = listPendingOperation.stream()
                .filter(x -> Objects.equals(x.id, confirmOperation.getOperationId()))
                .findFirst()
                .orElse(null);
        if (pendingOperation != null && pendingOperation.getCode().equals(confirmOperation.getCode())) {
            TransferMoney transferMoney = pendingOperation.getTransferMoney();
            logger.getLog("Операция: \"" + confirmOperation.getOperationId() + "\" выполнена \n" +
                    "Карта списания: " + transferMoney.getCardFromNumber() + "\n" +
                    "Карта зачисления: " + transferMoney.getCardToNumber() + "\n" +
                    "Сумма перевода: " + transferMoney.getAmount().getValue() + "\n" +
                    "Валюта перевода: " + transferMoney.getAmount().getCurrency());

            String fromCardNumber = transferMoney.cardFromNumber;
            String toCardNumber = transferMoney.cardToNumber;
            Amount amount = transferMoney.getAmount();
            Card fromCard = dataBaseCards.getCard(fromCardNumber);
            Card toCard = dataBaseCards.getCard(toCardNumber);
            updateBalance(fromCardNumber, toCardNumber, amount);
            logger.getLog("Баланс карты списания " + fromCardNumber + " : " + fromCard.amount().getValue());
            logger.getLog("Баланс карты зачисления" + toCardNumber + " : " + toCard.amount().getValue());
            TransferResult result = new TransferResult();
            result.operationId = confirmOperation.getOperationId();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            logger.getLog("Введен не верный код");
            throw new IncorrectCodeException("Введен не верный код");
        }
    }

    public void updateBalance(String fromCardNumber, String toCardNumber, Amount amount) {
        Card fromCard = dataBaseCards.getCard(fromCardNumber);
        Card toCard = dataBaseCards.getCard(toCardNumber);
        fromCard.amount().setValue(fromCard.amount().getValue() - amount.getValue());
        toCard.amount().setValue(toCard.amount().getValue() + amount.getValue());
    }

}
