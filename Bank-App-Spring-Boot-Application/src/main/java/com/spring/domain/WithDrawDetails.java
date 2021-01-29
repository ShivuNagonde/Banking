package com.spring.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WithDrawDetails {

	private Long accountNumber;
	private Double drawAmount;
	private Date drawDateTime;
}
