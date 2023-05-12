package ru.daria.transfermoneyservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.daria.transfermoneyservice.exception.IncorrectСodeException;
import ru.daria.transfermoneyservice.exception.NotEnoughMoneyException;
import ru.daria.transfermoneyservice.model.*;
import ru.daria.transfermoneyservice.repository.CardRepository;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.exceptionUnknownCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CardService {
    private final CardRepository dataBaseCards;
    Logger logger=new Logger();
    AtomicLong operationId = new AtomicLong(0);

    List<PendingOperation> lisPendingOperation = new ArrayList<>();


    public CardService(CardRepository dataBaseCards) {
        this.dataBaseCards = dataBaseCards;
    }

    public long transfer(TransferMoney transferMoney) {
        String fromCardNumber = transferMoney.getCardFromNumber();
        String toCardNumber = transferMoney.getCardToNumber();
        Card fromCard = dataBaseCards.getCard(fromCardNumber);
        Card toCard = dataBaseCards.getCard(toCardNumber);

        if (fromCard == null || toCard == null) {
            throw new exceptionUnknownCard("Неизвестный номер карты " + fromCardNumber);
        }
        if (!fromCard.getCardValidTill().equals(transferMoney.cardFromValidTill) ||
                !fromCard.getCardCVV().equals(transferMoney.cardFromCVV)) {
            logger.getLog("ERROR! Ошибка ввода данных карты: Error input data card");
            throw new IncorrectDataEntry("Error input data card");
        }
        if (fromCard.getValueCard() < transferMoney.getAmount().getValue()) {
            logger.getLog("На карте недостаточно средств");
            throw new NotEnoughMoneyException("На карте недостаточно средств");
        }

        long id = operationId.getAndIncrement();
        String code = "0000";
        PendingOperation operation = new PendingOperation(id, code, transferMoney);
        lisPendingOperation.add(operation);
        return id;
    }

    public ResponseEntity<String> confirmOperation(ConfirmOperation confirmOperation) {
        PendingOperation pendingOperation = lisPendingOperation.stream().filter(x -> x.id == confirmOperation.getId()).findFirst().orElse(null);
        if (!pendingOperation.getCode().equals(confirmOperation.getCode())) {

            logger.getLog("Введен не верный код");
            throw new IncorrectСodeException("Введен не верный код");
        } else {
            TransferMoney transferMoney=pendingOperation.getTransferMoney();
            logger.getLog("Операция: \"" + confirmOperation.getId() + "\" выполнена \n" +
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
            logger.getLog("Баланс карты списания " + fromCardNumber + " : " + fromCard.getValueCard());
            logger.getLog("Баланс карты зачисления" + toCardNumber + " : " + toCard.getValueCard());
            return new ResponseEntity("Операция с id " + confirmOperation.getId() + " подтверждена", HttpStatus.OK);

        }

    }

    public void updateBalance(String fromCardNumber, String toCardNumber, Amount amount) {
        Card fromCard = dataBaseCards.getCard(fromCardNumber);
        Card toCard = dataBaseCards.getCard(toCardNumber);
        fromCard.getAmount().setValue(fromCard.getAmount().getValue() - amount.getValue());
        toCard.getAmount().setValue(toCard.getAmount().getValue() + amount.getValue());
    }

//    public Card cardNumberCheck(String cardNumber) {
//        return dataBaseCards.getCard(cardNumber);
//
//    }
}
