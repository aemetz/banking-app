package com.tony.banking_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tony.banking_app.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByAccountUserUsernameOrRelatedAccountUserUsername(String username, String username1);
    List<Transaction> findByAccountIdOrRelatedAccountId(Long id, Long id1);
}
