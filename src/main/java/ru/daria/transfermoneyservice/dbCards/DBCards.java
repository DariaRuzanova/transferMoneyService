package ru.daria.transfermoneyservice.dbCards;

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
public class DBCards {
    private final String FOLDERPATH = "src/main/resources/application.properties";
    private Map<String, Card> listCards;
    private Amount amount;
    Logger logger;

    public DBCards() {
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

        for (int i = 1; i <= Integer.parseInt(properties.getProperty("NUMBER_USERS")); i++) {
            listCards.put(String.valueOf(i), new Card(
                    properties.getProperty("CARD_NUMBER_" + i),
                    properties.getProperty("CARD_VALID_TILL_" + i),
                    properties.getProperty("CARD_CVV_" + i),
                    new Amount(Integer.parseInt(properties.getProperty("CARD_AMOUNT_" + i)), properties.getProperty("CURRENCY_" + i))));

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
