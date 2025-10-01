package com.tony.banking_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tony.banking_app.dto.AccountResponse;
import com.tony.banking_app.dto.CreateAccountRequest;
import com.tony.banking_app.entity.Account;
import com.tony.banking_app.entity.User;
import com.tony.banking_app.entity.enums.AccountType;
import com.tony.banking_app.exception.UsernameNotFoundException;
import com.tony.banking_app.repository.AccountRepository;
import com.tony.banking_app.repository.UserRepository;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    /**
     * Accesses user object from DB given username, and returns their accounts
     * @param username provided by controller
     * @return List of accounts associated with the user
     * @throws UsernameNotFoundException
     */
    public List<AccountResponse> getAllAccountsByUsername() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<Account> accounts = user.getAccounts();
        List<AccountResponse> newAccounts = accounts.stream()
            .map(account -> new AccountResponse(account.getId(), 
                                                account.getBalance(), 
                                                account.getType(), 
                                                account.getStatus()))
            .collect(Collectors.toList());
        return newAccounts;
    }


    /**
     * Accesses user object from DB given username, creates new account object, and saves to account repository
     * @param request
     * @return the persisted account
     * @throws UsernameNotFoundException
     */
    public AccountResponse createAccount(CreateAccountRequest request) throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        AccountType type = request.getType();
        Account newAccount = new Account(user, Double.valueOf(0), type);
        Account persistedAccount = accountRepository.save(newAccount);
        return new AccountResponse(persistedAccount.getId(), persistedAccount.getBalance(), persistedAccount.getType(), persistedAccount.getStatus());
    }

}
