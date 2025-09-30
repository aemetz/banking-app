package com.tony.banking_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.banking_app.dto.AccountResponse;
import com.tony.banking_app.dto.CreateAccountRequest;
import com.tony.banking_app.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Retrieve the username of the current user from the SecurityContext and use the accountService to return their accounts
     * @return A response entity containing a list of accounts associated with the current user
     */
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountResponse> accounts = accountService.getAllAccountsByUsername(username);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accounts);
    }

    /**
     * Create a new account for the current user and return it in the response
     * @param request CreateAccountRequest request body containing AccountType
     * @return A response entity containing the persisted account
     */
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) { // @RequestBody AccountStatus status (?)
        // initial balance 0, status active, type (get from request body), user_id look up based on username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountResponse newAccount = accountService.createAccount(username, request.getType());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(newAccount);
    }

}
