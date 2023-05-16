package ru.daria.transfermoneyservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.daria.transfermoneyservice.exception.ExceptionUnknownCard;
import ru.daria.transfermoneyservice.exception.IncorrectDataEntry;
import ru.daria.transfermoneyservice.exception.IncorrectCodeException;
import ru.daria.transfermoneyservice.exception.NotEnoughMoneyException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(IncorrectDataEntry.class)
    public ResponseEntity<String> ideHandler(IncorrectDataEntry e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionUnknownCard.class)
    public ResponseEntity<String> eucHandler(ExceptionUnknownCard e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectCodeException.class)
    public ResponseEntity<String> iceHandler(IncorrectCodeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<String> nemHandler(NotEnoughMoneyException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
