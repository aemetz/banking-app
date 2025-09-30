package com.tony.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tony.banking_app.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
