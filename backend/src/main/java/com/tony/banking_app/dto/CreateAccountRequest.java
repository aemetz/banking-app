package com.tony.banking_app.dto;

import com.tony.banking_app.entity.enums.AccountType;

public class CreateAccountRequest {
    private AccountType type;

    public AccountType getType() {
        return this.type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
}
