package com.spring.domain;

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
public class TransferDetails {

	private Long fromAccountNumber;
	private Long toAccountNumber;
	private Double transferAmount;
}
