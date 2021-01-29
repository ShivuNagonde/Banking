package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.CustomerAccountXRef;

public interface CustomerAccountXRefRepository extends JpaRepository<CustomerAccountXRef, Integer> {

}
