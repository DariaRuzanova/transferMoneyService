package ru.daria.transfermoneyservice.model;

public class PendingOperation {
    public String id;
    private final String code;
    private final TransferMoney transferMoney;

    public PendingOperation(String id, String code, TransferMoney transferMoney) {
        this.id = id;
        this.code = code;
        this.transferMoney = transferMoney;
    }

    public String getCode() {
        return code;
    }

    public TransferMoney getTransferMoney() {
        return transferMoney;
    }
}
