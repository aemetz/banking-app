package com.tony.banking_app.dto;

import com.tony.banking_app.entity.enums.AccountStatus;
import com.tony.banking_app.entity.enums.AccountType;

public record AccountResponse(Long id, Double balance, AccountType type, AccountStatus status) {}
