package com.spring.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spring.domain.AccountInformation;
import com.spring.domain.CustomerDetails;
import com.spring.domain.DepositDetails;
import com.spring.domain.TransactionDetails;
import com.spring.domain.TransferDetails;
import com.spring.domain.WithDrawDetails;

public interface BankingService {

	public ResponseEntity<Object> cashDeposite(DepositDetails depositDetails,Long accountNumber); 
	
	public ResponseEntity<Object> cashWithDraw(WithDrawDetails withDrawDetails,Long accountNumber);
   
	public List<CustomerDetails> findAll();
    
    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);
    
    public CustomerDetails findByCustomerNumber(Long customerNumber);
    
    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);
    
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) ;
    
    public ResponseEntity<Object> findByAccountNumber(Long accountNumber);
    
    public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);
    
    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);
    
    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber);
    
}
