package com.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);

}
