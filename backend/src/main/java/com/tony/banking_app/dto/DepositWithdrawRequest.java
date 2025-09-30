package com.tony.banking_app.dto;

public class DepositWithdrawRequest {
    private Long accountId;
    private Double amount;


    public Long getAccountId() {
        return this.accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
