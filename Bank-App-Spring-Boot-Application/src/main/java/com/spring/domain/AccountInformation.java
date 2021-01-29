package com.spring.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountInformation {

    private Long accountNumber;
    private BankInformation bankInformation;	
	private String accountStatus;	
	private String accountType;	
	private Double accountBalance;	
	private Date accountCreatedDate;
}
