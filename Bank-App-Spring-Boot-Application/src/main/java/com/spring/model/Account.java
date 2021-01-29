package com.spring.model;

import java.util.Date;

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
public class Account {

    private static final long AccountStatus_VALID_DURATION = 120;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Long accountNumber;
	private String accountStatus;
	private String accountType;
	private Double accountBalance;
	
	//@Temporal(TemporalType.TIME)
	private Date createDateTime;
	
	//@Temporal(TemporalType.TIME)
	private Date updateDateTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	private BankInfo bankInformation;

	

	public boolean isAccountStatusRequired() {
        if (this.getAccountStatus() == "InActive") {
            return false;
        }
         
        long currentTimeInMillis = System.currentTimeMillis();
       // long statusRequestedTimeInMillis = this.statusRequestedTime.getTime();
         
        if (AccountStatus_VALID_DURATION < currentTimeInMillis) {
           return false;
            }
         return true;
         }
}
