package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.domain.AccountInformation;
import com.spring.domain.DepositDetails;
import com.spring.domain.TransactionDetails;
import com.spring.domain.TransferDetails;
import com.spring.domain.WithDrawDetails;
import com.spring.service.BankingServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private BankingServiceImpl bankingService;

	@GetMapping(path = "/{accountNumber}")
	public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findByAccountNumber(accountNumber);
	}

	@PostMapping(path = "/add/{customerNumber}")
	public ResponseEntity<Object> addNewAccount(@RequestBody AccountInformation accountInformation,
			@PathVariable Long customerNumber) {

		return bankingService.addNewAccount(accountInformation, customerNumber);
	}
	
	@PostMapping(path = "/deposit/{accountNumber}")
	public ResponseEntity<Object> cashDeposit(@RequestBody DepositDetails depositDetails,@PathVariable Long accountNumber){
		return bankingService.cashDeposite(depositDetails,accountNumber);
	}
	
	@GetMapping(path = "/withDraw/{accountNumber}")
	public ResponseEntity<Object> cashWithDraw(WithDrawDetails withDrawDetails, Long accountNumber) {
		return bankingService.cashWithDraw(withDrawDetails, accountNumber);
	}

	@PutMapping(path = "/transfer/{customerNumber}")
	public ResponseEntity<Object> transferDetails(@RequestBody TransferDetails transferDetails,
			@PathVariable Long customerNumber) {

		return bankingService.transferDetails(transferDetails, customerNumber);
	}

	@GetMapping(path = "/transactions/{accountNumber}")
	public List<TransactionDetails> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findTransactionsByAccountNumber(accountNumber);
	}
}
