package com.tony.banking_app.entity;

import java.time.LocalDateTime;

import com.tony.banking_app.entity.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    // @JsonIgnore // avoid infinite nesting if not using DTO
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // TRANSFER, DEPOSIT, WITHDRAWAL

    private Double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="related_account_id", nullable=true)
    private Account relatedAccount; // only used for TRANSFER transaction type


    public Transaction() {}

    public Transaction(Account account, TransactionType type, Double amount, LocalDateTime timestamp) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(Account account, TransactionType type, Double amount, LocalDateTime timestamp, Account relatedAccount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.relatedAccount = relatedAccount;
    }


    /*
     * GETTERS / SETTERS
     */

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return this.account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return this.type;
    }
    public void setType(TransactionType type) {
        this.type = type;
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

    public Account getRelatedAccount() {
        return this.relatedAccount;
    }
    public void setRelatedAccount(Account relatedAccount) {
        this.relatedAccount = relatedAccount;
    }
}