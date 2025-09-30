package com.tony.banking_app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tony.banking_app.dto.DepositWithdrawRequest;
import com.tony.banking_app.dto.TransactionResponse;
import com.tony.banking_app.entity.Account;
import com.tony.banking_app.entity.Transaction;
import com.tony.banking_app.entity.User;
import com.tony.banking_app.entity.enums.TransactionType;
import com.tony.banking_app.exception.AccountNotFoundException;
import com.tony.banking_app.exception.InvalidAccountException;
import com.tony.banking_app.exception.InvalidTransactionException;
import com.tony.banking_app.exception.UsernameNotFoundException;
import com.tony.banking_app.repository.AccountRepository;
import com.tony.banking_app.repository.TransactionRepository;
import com.tony.banking_app.repository.UserRepository;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponse makeDeposit(DepositWithdrawRequest request) throws AccountNotFoundException, UsernameNotFoundException, InvalidAccountException {
        // Check if the requested account belongs to the current user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Account destinationAccount = accountRepository.findById(request.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + request.getAccountId()));
        if (!user.getAccounts().contains(destinationAccount)) {
            throw new InvalidAccountException("Account " + request.getAccountId() + " does not belong to current user");
        }

        // Initialize transaction fields and persist to database
        Double amount = request.getAmount();
        if (amount <= 0) {
            throw new InvalidTransactionException("Deposit amount must be greater than 0");
        }
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction deposit = new Transaction(destinationAccount, TransactionType.DEPOSIT, amount, timestamp);
        Transaction persistedTransaction = transactionRepository.save(deposit);

        // Retreive account, update account balance, and save to repository
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        accountRepository.save(destinationAccount);

        // Create and return response object
        TransactionResponse response = new TransactionResponse();
        response.setAccountId(request.getAccountId());
        response.setAmount(persistedTransaction.getAmount());
        response.setTimestamp(persistedTransaction.getTimestamp());
        response.setType(persistedTransaction.getType());
        return response;
    }

}
