package com.tony.banking_app.dto;

public class TransferRequest {
    private Long fromId;
    private Double amount;
    private Long toId;

    public Long getFromId() {
        return this.fromId;
    }
    public void setfromId(Long fromId) {
        this.fromId = fromId;
    }
    
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getToId() {
        return this.toId;
    }
    public void setToId(Long toId) {
        this.toId = toId;
    }
}
