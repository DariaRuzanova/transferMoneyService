package ru.daria.transfermoneyservice.service;

import org.springframework.stereotype.Service;
import ru.daria.transfermoneyservice.dbCards.DBCards;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.exceptionUnknownCard;
import ru.daria.transfermoneyservice.model.Card;
import ru.daria.transfermoneyservice.model.Logger;
import ru.daria.transfermoneyservice.model.TransferMoney;

@Service
public class AuthorizationService {
    private TransferMoney transferMoney;
    private final DBCards dataBaseCards;
    Logger logger;

    public AuthorizationService(DBCards dataBaseCards) {
        this.dataBaseCards = dataBaseCards;
    }

    public String transfer(TransferMoney transferMoney) {
        String fromCardNumber = transferMoney.getFromCardNumber();
        String toCardNumber = transferMoney.getToCardNumber();
        Card fromCard = cardNumberCheck(fromCardNumber);
        Card toCard = cardNumberCheck(toCardNumber);

        if (fromCard == null || toCardNumber == null) {
            throw new exceptionUnknownCard("Неизвестный номер карты " + fromCardNumber);
        }
        if (!fromCard.getCardValidTill().equals(transferMoney.fromCardValidTill) ||
                !fromCard.getCardCVV().equals(transferMoney.fromCardCVC)) {
            logger.getLog("ERROR! Ошибка ввода данных карты: Error input data card");
            throw new IncorrectDataEntry("Error input data card");

        }


    }


    private Card cardNumberCheck(String cardNumber) {
        return dataBaseCards.getCard(cardNumber);

    }


}
