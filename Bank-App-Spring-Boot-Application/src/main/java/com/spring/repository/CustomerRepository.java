package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Optional<Customer> findByCustomerNumber(Long customerNumber);

}
