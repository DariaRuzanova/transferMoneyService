package ru.daria.transfermoneyservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.daria.transfermoneyservice.model.ConfirmOperation;
import ru.daria.transfermoneyservice.model.TransferMoney;
import ru.daria.transfermoneyservice.service.CardService;

@RestController
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = {"http://localhost:5500"})
public class TransferMoneyController {
    private final CardService service;

    public TransferMoneyController(CardService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferMoney transferMoney) {
        return service.transfer(transferMoney);

    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }


}
