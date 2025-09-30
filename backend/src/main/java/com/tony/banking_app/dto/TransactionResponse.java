package com.tony.banking_app.dto;

import java.time.LocalDateTime;

import com.tony.banking_app.entity.enums.TransactionType;

public class TransactionResponse {
    private Double amount;
    private LocalDateTime timestamp;
    private TransactionType type;
    private Long accountId;
    private Long relatedAccountId;

    public TransactionResponse() {}

    public TransactionResponse(Double amount, LocalDateTime timestamp, TransactionType type, Long accountId, Long relatedAccountId) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.accountId = accountId;
        this.relatedAccountId = relatedAccountId;
    }

    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionType getType() {
        return this.type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getAccountId() {
        return this.accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRelatedAccountId() {
        return this.relatedAccountId;
    }
    public void setRelatedAccountId(Long relatedAccountId) {
        this.relatedAccountId = relatedAccountId;
    }

}
