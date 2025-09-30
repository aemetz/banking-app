package com.tony.banking_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.dto.DepositWithdrawRequest;
import com.tony.banking_app.dto.TransactionResponse;
import com.tony.banking_app.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> makeDeposit(@RequestBody DepositWithdrawRequest request) {
        TransactionResponse transaction = transactionService.makeDeposit(request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transaction);
    }

}
