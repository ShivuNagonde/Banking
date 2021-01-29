package com.spring.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.spring.domain.AccountInformation;
import com.spring.domain.AddressDetails;
import com.spring.domain.BankInformation;
import com.spring.domain.ContactDetails;
import com.spring.domain.CustomerDetails;
import com.spring.domain.DepositDetails;
import com.spring.domain.TransactionDetails;
import com.spring.domain.TransferDetails;
import com.spring.domain.WithDrawDetails;
import com.spring.model.Account;
import com.spring.model.Address;
import com.spring.model.BankInfo;
import com.spring.model.Contact;
import com.spring.model.Customer;
import com.spring.model.Deposit;
import com.spring.model.Transaction;
import com.spring.model.WithDraw;

@Component
public class BankingServiceHelper {
	
public CustomerDetails convertToCustomerDomain(Customer customer) {
		
		return CustomerDetails.builder()
				.firstName(customer.getFirstName())
				.middleName(customer.getMiddleName())
				.lastName(customer.getLastName())
				.customerNumber(customer.getCustomerNumber())
				.status(customer.getStatus())
				.contactDetails(convertToContactDomain(customer.getContactDetails()))
				.customerAddress(convertToAddressDomain(customer.getCustomerAddress()))
				.build();
	}
	
	public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
		
		
		return Customer.builder()
				.firstName(customerDetails.getFirstName())
				.middleName(customerDetails.getMiddleName())
				.lastName(customerDetails.getLastName())
				.customerNumber(customerDetails.getCustomerNumber())
				.status(customerDetails.getStatus())
				.contactDetails(convertToContactEntity(customerDetails.getContactDetails()))
				.customerAddress(convertToAddressEntity(customerDetails.getCustomerAddress()))
				.build();
	}

	public AccountInformation convertToAccountDomain(Account account) {

		return AccountInformation.builder()
				.accountType(account.getAccountType())
				.accountBalance(account.getAccountBalance())
				.accountNumber(account.getAccountNumber())
				.accountStatus(account.getAccountStatus())
				.accountCreatedDate(account.getCreateDateTime())
				.bankInformation(convertToBankInfoDomain(account.getBankInformation()))
				.build();
	}
	
	public Account convertToAccountEntity(AccountInformation accInfo) {
		
		return Account.builder()
				.accountType(accInfo.getAccountType())
				.accountBalance(accInfo.getAccountBalance())
				.accountNumber(accInfo.getAccountNumber())
				.accountStatus(accInfo.getAccountStatus())
				.createDateTime(accInfo.getAccountCreatedDate())
				.bankInformation(convertToBankInfoEntity(accInfo.getBankInformation()))
				.build();
	}
	
	public AddressDetails convertToAddressDomain(Address address) {
		
		return AddressDetails.builder().address1(address.getAddress1())
				.address2(address.getAddress2())
				.city(address.getCity())
				.state(address.getState())
				.zip(address.getZipcode())
				.country(address.getCountry())
				.build();
	}
	
	public Address convertToAddressEntity(AddressDetails addressDetails) {
		
		return Address.builder().address1(addressDetails.getAddress1())
				.address2(addressDetails.getAddress2())
				.city(addressDetails.getCity())
				.state(addressDetails.getState())
				.zipcode(addressDetails.getZip())
				.country(addressDetails.getCountry())
				.build();
	}
	
	public ContactDetails convertToContactDomain(Contact contact) {
		
		return ContactDetails.builder()
				.emailId(contact.getEmailId())
				.homePhone(contact.getHomePhone())
				.workPhone(contact.getWorkPhone())
				.build();
	}
	
	public Contact convertToContactEntity(ContactDetails contactDetails) {
		
		return Contact.builder()
				.emailId(contactDetails.getEmailId())
				.homePhone(contactDetails.getHomePhone())
				.workPhone(contactDetails.getWorkPhone())
				.build();
	}
	
	public BankInformation convertToBankInfoDomain(BankInfo bankInfo) {
		
		return BankInformation.builder()
				.branchCode(bankInfo.getBranchCode())
				.branchName(bankInfo.getBranchName())
				.routingNumber(bankInfo.getRoutingNumber())
				.branchAddress(convertToAddressDomain(bankInfo.getBranchAddress()))
				.build();
	}
	
	public BankInfo convertToBankInfoEntity(BankInformation bankInformation) {
		
		return BankInfo.builder()
				.branchCode(bankInformation.getBranchCode())
				.branchName(bankInformation.getBranchName())
				.routingNumber(bankInformation.getRoutingNumber())
				.branchAddress(convertToAddressEntity(bankInformation.getBranchAddress()))
				.build();
	}
	
     public TransactionDetails convertToTransactionDomain(Transaction transaction) {
		
		return TransactionDetails.builder()
									.txAmount(transaction.getTxAmount())
									.txDateTime(transaction.getTxDateTime())
									.txType(transaction.getTxType())
									.accountNumber(transaction.getAccountNumber())
									.build();
	}
	
	public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {
		
		return Transaction.builder()
							.txAmount(transactionDetails.getTxAmount())
							.txDateTime(transactionDetails.getTxDateTime())
							.txType(transactionDetails.getTxType())
							.accountNumber(transactionDetails.getAccountNumber())
							.build();
	}
	public Transaction createTransaction(TransferDetails transferDetails, Long accountNumber, String txType) {	
		return Transaction.builder()
							.accountNumber(accountNumber)
							.txAmount(transferDetails.getTransferAmount())
							.txType(txType)
							.txDateTime(new Date())
							.build();
	}
	public DepositDetails convertToDepositDomain(Deposit deposit) {
		return DepositDetails.builder()
				.depositAmount(deposit.getDepositAmount())
				.depositDateTime(deposit.getDepositDateTime())
				.toAccountNumber(deposit.getAccountNumber())
				.build();
	}
	public Deposit convertToDepositEntity(DepositDetails depositDetails) {
		return Deposit.builder()
				.accountNumber(depositDetails.getToAccountNumber())
				.depositAmount(depositDetails.getDepositAmount())
				.depositDateTime(depositDetails.getDepositDateTime())
				.build();
	}
	public Deposit createDeposit(DepositDetails depositDetails,Long accountNumber) {
		return Deposit.builder()
				.accountNumber(accountNumber)
				.depositAmount(depositDetails.getDepositAmount())
				.depositDateTime(new Date())
				.build();
		}
	public WithDrawDetails convertToWithDrawDomain(WithDraw withDraw) {
		return WithDrawDetails.builder()
				.accountNumber(withDraw.getAccountNumber())
				.drawAmount(withDraw.getDrawAmount())
				.drawDateTime(withDraw.getDrawDateTime())
				.build();
	}
	public WithDraw convertToWithDrawEntity(WithDrawDetails withDrawDetails) {
		return WithDraw.builder()
				.accountNumber(withDrawDetails.getAccountNumber())
				.drawAmount(withDrawDetails.getDrawAmount())
				.drawDateTime(withDrawDetails.getDrawDateTime())
				.build();
		
	}
	public WithDraw createWithDraw(WithDrawDetails withDrawDetails, Long accountNumber) {
		return WithDraw.builder()
				.accountNumber(accountNumber)
				.drawAmount(withDrawDetails.getDrawAmount())
				.drawDateTime(new Date())
				.build();
	}
	
}
