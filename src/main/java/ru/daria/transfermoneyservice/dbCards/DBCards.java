package ru.daria.transfermoneyservice.dbCards;

import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DBCards {
    private final String FOLDERPATH = "src/main/resources/application.properties";
    private Map<String, Card> listCards;
    private Amount amount;

    public DBCards() {
        listCards = new ConcurrentHashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(FOLDERPATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void addCard(Properties properties){

        for (int i = 1; i <= Integer.parseInt(properties.getProperty("NUMBER_USERS")); i++) {
            listCards.put(String.valueOf(i),new Card(
                    properties.getProperty("CARD_NUMBER_"+i),
                    properties.getProperty("CARD_VALID_TILL_"+i),
                    properties.getProperty("CARD_CVV_"+i),
                    new Amount(Integer.parseInt(properties.getProperty("CARD_AMOUNT_"+i)), properties.getProperty("CURRENCY_"+i))));

        }
    }
}
