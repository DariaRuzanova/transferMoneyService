package ru.daria.transfermoneyservice.repository;

import org.springframework.stereotype.Repository;
import ru.daria.transfermoneyservice.dbCards.DBCards;
import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.Card;
import ru.daria.transfermoneyservice.model.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import ru.daria.transfermoneyservice.model.Amount;
import ru.daria.transfermoneyservice.model.Card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {

    private DBCards dataBaseCards = new DBCards();
    private Amount amount;
    private Logger logger;


    public boolean findCardNumber(String cardNumber){
        if(!dataBaseCards.getListCards().containsKey(cardNumber)){
            System.out.println("Карты с номером " +cardNumber+" нет в базе.");
            logger.getLog("Номер карты "+cardNumber+" введен не верно");
            return false;
        }
        return true;
    }
    public boolean verificationCVV(String cardCVV,String cardNumber){

    }







}
