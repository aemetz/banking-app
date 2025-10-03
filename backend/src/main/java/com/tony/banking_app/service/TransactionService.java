package com.tony.banking_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tony.banking_app.dto.DepositWithdrawRequest;
import com.tony.banking_app.dto.TransactionResponse;
import com.tony.banking_app.dto.TransferRequest;
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

    /**
     * Make a deposit of a positive amount of money into the requested account, if it belongs to the user
     * @param request
     * @return TransactionResponse object
     * @throws AccountNotFoundException if requested account cannot be found in database
     * @throws UsernameNotFoundException if current user cannot be found in database
     * @throws InvalidAccountException if requested account does not belong to the user
     * @throws InvalidTransactionException if requested amount is non-positive
     */
    public TransactionResponse makeDeposit(DepositWithdrawRequest request) throws AccountNotFoundException, UsernameNotFoundException, InvalidAccountException, InvalidTransactionException {
        System.out.println("id: " + request.getAccountId() + " | amount: " + request.getAmount());
        
        // Ensure the requested account belongs to the current user
        Account destinationAccount = validateAccountOwnership(request.getAccountId());

        // Validate amount, create transaction object and persist to database
        Double amount = request.getAmount();
        if (amount <= 0) {
            throw new InvalidTransactionException("Deposit amount must be greater than 0");
        }
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction deposit = new Transaction(destinationAccount, TransactionType.DEPOSIT, amount, timestamp);
        Transaction persistedTransaction = transactionRepository.save(deposit);

        // Update retreived account's balance and save updated account to repository
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

    /**
     * Make a withdrawal of a positive amount of money from the requested account, if it belongs to the user
     * @param request
     * @return TransactionResponse object
     * @throws AccountNotFoundException if requested account cannot be found in database
     * @throws UsernameNotFoundException if current user cannot be found in database
     * @throws InvalidAccountException if requested account does not belong to the user
     * @throws InvalidTransactionException if requested amount is non-positive or higher than balance
     */
    public TransactionResponse makeWithdrawal(DepositWithdrawRequest request) throws AccountNotFoundException, UsernameNotFoundException, InvalidAccountException, InvalidTransactionException {
        // Ensure the requested account belongs to the current user
        Account sourceAccount = validateAccountOwnership(request.getAccountId());

        // Validate amount and existing balance, create transaction object and persist to database
        Double amount = request.getAmount();
        if (amount <= 0) {
            throw new InvalidTransactionException("Withdrawal amount must be greater than 0");
        }
        Double balance = sourceAccount.getBalance();
        if (balance < amount) {
            throw new InvalidTransactionException("Withdrawal amount cannot be greater than balance");
        }
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction withdrawal = new Transaction(sourceAccount, TransactionType.WITHDRAWAL, amount, timestamp);
        Transaction persistedTransaction = transactionRepository.save(withdrawal);

        // Update retreived account's balance and save updated account to repository
        sourceAccount.setBalance(balance - amount);
        accountRepository.save(sourceAccount);

        // Create and return response object
        TransactionResponse response = new TransactionResponse();
        response.setAccountId(request.getAccountId());
        response.setAmount(persistedTransaction.getAmount());
        response.setTimestamp(persistedTransaction.getTimestamp());
        response.setType(persistedTransaction.getType());
        return response;
    }

    /**
     * Transfer a positive amount of money from an account owned by the user to any other account (excluding itself)
     * @param request
     * @return TransactionResponse object
     * @throws AccountNotFoundException if requested account cannot be found in database
     * @throws UsernameNotFoundException if current user cannot be found in database
     * @throws InvalidAccountException if requested account does not belong to the user
     * @throws InvalidTransactionException if requested amount is non-positive, higher than balance, or goes from an account to itself
     */
    public TransactionResponse makeTransfer(TransferRequest request) throws AccountNotFoundException, UsernameNotFoundException, InvalidAccountException, InvalidTransactionException {
        // Validate that the current user owns the "from" account, and it is not equal to the "to" account
        Account from = validateAccountOwnership(request.getFromId());
        if (from.getId().equals(request.getToId())) {
            throw new InvalidTransactionException("Cannot transfer from an account to itself");
        }

        // Retrieve "to" account (can be any account, belonging to the current user or another)
        Account to = accountRepository.findById(request.getToId())
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + request.getToId()));

        // Ensure transfer amount is positive
        Double amount = request.getAmount();
        if (amount <= 0) {
            throw new InvalidTransactionException("Transfer amount must be greater than 0");
        }

        // Check balance of "from" account
        if (from.getBalance() < amount) {
            throw new InvalidTransactionException("Transfer amount cannot be greater than balance");
        }

        // Save new account balances and persist
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountRepository.save(from);
        accountRepository.save(to);

        // Create transaction object and persist
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction(from, TransactionType.TRANSFER, amount, timestamp, to);
        Transaction persistedTransaction = transactionRepository.save(transaction);

        // Create and return response object
        TransactionResponse response = new TransactionResponse();
        response.setAccountId(persistedTransaction.getAccount().getId());
        response.setRelatedAccountId(persistedTransaction.getRelatedAccount().getId());
        response.setAmount(persistedTransaction.getAmount());
        response.setTimestamp(persistedTransaction.getTimestamp());
        response.setType(persistedTransaction.getType());
        return response;
    }

    /**
     * Utility method that completes validation logic for account ownership - "does the current user own this account?"
     * @param accountId
     * @return Account object
     */
    private Account validateAccountOwnership(Long accountId) throws UsernameNotFoundException, AccountNotFoundException, InvalidAccountException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
        if (!user.getAccounts().contains(account)) {
            throw new InvalidAccountException("Account " + accountId + " does not belong to current user");
        }
        return account;
    }


    /**
     * Retrieve a list of all transactions associated with the current user (includes transactions where the user's account was primary or secondary)
     * @return a List of TransactionResponse objects
     */
    public List<TransactionResponse> getAllTransactionsByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Get transactions from repo and map to TransactionResponses
        List<TransactionResponse> transactions = transactionRepository.findByAccountUserUsernameOrRelatedAccountUserUsername(username, username)
            .stream()
            .map(TransactionService::transactionToResponseDTO)
            .collect(Collectors.toList());
        return transactions;
    }

    /**
     * Retrieve a list of all transactions associated with the account id provided
     * @param id associated with the account we want the transactions for
     * @return a List of TransactionResponse objects
     * @throws UsernameNotFoundException if current user cannot be found in database
     * @throws AccountNotFoundException if requested account cannot be found in database
     * @throws InvalidAccountException if requested account does not belong to the user
     */
    public List<TransactionResponse> getTransactionsByAccount(Long id) throws UsernameNotFoundException, AccountNotFoundException, InvalidAccountException {
        // Ensure that the requested account belongs to the current user
        validateAccountOwnership(id);

        // Get transactions from repo and map to TransactionResponses
        List<TransactionResponse> transactions = transactionRepository.findByAccountIdOrRelatedAccountId(id, id)
            .stream()
            .map(TransactionService::transactionToResponseDTO)
            .collect(Collectors.toList());
        return transactions;
    }


    /**
     * Utility method that transforms Transaction objects into TransactionResponse objects, handling potential relatedAccount nullity
     * @param transaction
     * @return TransactionResponse object
     */
    private static TransactionResponse transactionToResponseDTO(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setAmount(transaction.getAmount());
        response.setTimestamp(transaction.getTimestamp());
        response.setType(transaction.getType());
        response.setAccountId(transaction.getAccount().getId());
        if (transaction.getRelatedAccount() != null) {
            response.setRelatedAccountId(transaction.getRelatedAccount().getId());
        }
        return response;
    }


}
