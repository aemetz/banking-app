package com.tony.banking_app.entity;

import java.util.List;

import com.tony.banking_app.entity.enums.AccountStatus;
import com.tony.banking_app.entity.enums.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="acct_seq")
    @SequenceGenerator(name="acct_seq", sequenceName="account_num_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private AccountType type; // CHECKING, SAVINGS

    @Enumerated(EnumType.STRING)
    private AccountStatus status; // ACTIVE, PENDING, REJECTED

    @OneToMany(mappedBy="account")
    private List<Transaction> transactions;


    /*
     * GETTERS / SETTERS
     */

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    public Double getBalance() {
        return this.balance;
    } 
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return this.type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountStatus getStatus() {
        return this.status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}