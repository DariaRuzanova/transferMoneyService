package ru.daria.transfermoneyservice.repository;

import org.springframework.stereotype.Repository;
import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.Card;
import ru.daria.transfermoneyservice.model.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {
    private final String FOLDERPATH = "src/main/resources/application.properties";
    private Map<String, Card> listCards;
    private Amount amount;
    Logger logger = new Logger();

    public CardRepository() {
        listCards = new ConcurrentHashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(FOLDERPATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addCard(properties);
    }

    private void addCard(Properties properties) {
        var prop = properties.getProperty("NUMBER_USERS");
        var c = Integer.parseInt(prop);

        for (int i = 1; i <= c; i++) {
            String cardNumber = properties.getProperty("CARD_NUMBER_" + i);
            var amountStr = properties.getProperty("CARD_AMOUNT_" + i);
            var amountValue = Integer.parseInt(amountStr);
            var amountCurrency = properties.getProperty("CURRENCY_" + i);
            var amount = new Amount(amountValue, amountCurrency);
            var card = new Card(cardNumber,
                    properties.getProperty("CARD_VALID_TILL_" + i),
                    properties.getProperty("CARD_CVV_" + i),
                    amount);
            listCards.put(cardNumber, card);
        }
    }


    public Map<String, Card> getListCards() {
        return listCards;
    }


    public Card getCard(String cardNumber) {
        if (!listCards.containsKey(cardNumber)) {
            System.out.println("Карты с номером " + cardNumber + " нет в базе.");
            logger.getLog("Номер карты " + cardNumber + " введен не верно");
            return null;
        }
        Card card = listCards.get(cardNumber);
        return card;
    }
}
