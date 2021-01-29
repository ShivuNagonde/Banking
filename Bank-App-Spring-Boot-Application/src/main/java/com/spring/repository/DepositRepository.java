package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

}
