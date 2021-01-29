package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	Optional<Account> findByAccountNumber(Long accountNumber);

	

	
}
