package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.WithDraw;

public interface WithDrawRepository extends JpaRepository<WithDraw, Integer> {

}
