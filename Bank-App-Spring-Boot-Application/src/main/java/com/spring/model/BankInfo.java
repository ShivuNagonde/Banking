package com.spring.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String branchName;
	private Integer branchCode;
	private Integer routingNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
    private Address branchAddress;
}
