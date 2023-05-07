package ru.daria.transfermoneyservice.model;

public class PendingOperation {
    public long id;
    private String code;
    private TransferMoney transferMoney;

    public PendingOperation(long id, String code, TransferMoney transferMoney) {
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
