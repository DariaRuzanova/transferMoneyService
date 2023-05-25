package ru.daria.transfermoneyservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.daria.transfermoneyservice.model.ConfirmOperation;
import ru.daria.transfermoneyservice.model.TransferMoney;
import ru.daria.transfermoneyservice.model.TransferResult;
import ru.daria.transfermoneyservice.service.CardService;

@RestController
@CrossOrigin
public class TransferMoneyController {
    private final CardService service;

    public TransferMoneyController(CardService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResult> transfer(@RequestBody TransferMoney transferMoney) {
        return service.transfer(transferMoney);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<TransferResult> confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }
}
