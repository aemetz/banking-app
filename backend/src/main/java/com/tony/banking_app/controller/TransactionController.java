package com.tony.banking_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.dto.DepositWithdrawRequest;
import com.tony.banking_app.dto.TransactionResponse;
import com.tony.banking_app.dto.TransferRequest;
import com.tony.banking_app.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Make a deposit of a positive amount of money into the requested account, if it belongs to the user
     * @param request containing accountId and amount to deposit
     * @return ResponseEntity containing the transaction dto
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> makeDeposit(@RequestBody DepositWithdrawRequest request) {
        TransactionResponse transaction = transactionService.makeDeposit(request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transaction);
    }

    /**
     * Make a withdrawal of a positive amount of money from the requested account, if it belongs to the user
     * @param request containing accountId and amount to withdraw
     * @return ResponseEntity containing the transaction dto
     */
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> makeWithdrawal(@RequestBody DepositWithdrawRequest request) {
        TransactionResponse transaction = transactionService.makeWithdrawal(request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transaction);
    }

    /**
     * Transfer a positive amount of money from an account owned by the user to any other account (excluding itself)
     * @param request containing Id of the source and destination accounts, and amount to transfer
     * @return ResponseEntity containing the transaction dto
     */
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> makeTransfer(@RequestBody TransferRequest request) {
        TransactionResponse transaction = transactionService.makeTransfer(request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transaction);
    }

    /**
     * Retrieve a list of all transactions associated with the current user
     * @return ResponseEntity containing a list of TransactionResponse dtos
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactionsByUser() {
        List<TransactionResponse> transactions = transactionService.getAllTransactionsByUser();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions);
    }

    /**
     * Retrieve a list of all transactions associated with the account id provided
     * @param accountId associated with the account we want the transactions for
     * @return ResponseEntity containing a list of TransactionResponse dtos
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccount(@PathVariable Long accountId) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByAccount(accountId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(transactions);
    }

    

}
