package ru.daria.transfermoneyservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.daria.transfermoneyservice.model.ConfirmOperation;
import ru.daria.transfermoneyservice.model.TransferMoney;
import ru.daria.transfermoneyservice.service.CardService;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
    private final CardService service;

    public Controller(CardService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public long transfer(@RequestBody TransferMoney transferMoney) {
        return service.transfer(transferMoney);

    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return service.confirmOperation(confirmOperation);
    }


}
