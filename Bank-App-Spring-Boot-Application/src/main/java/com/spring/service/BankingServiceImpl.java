package com.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.domain.AccountInformation;
import com.spring.domain.CustomerDetails;
import com.spring.domain.DepositDetails;
import com.spring.domain.TransactionDetails;
import com.spring.domain.TransferDetails;
import com.spring.domain.WithDrawDetails;
import com.spring.model.Account;
import com.spring.model.Address;
import com.spring.model.Contact;
import com.spring.model.Customer;
import com.spring.model.CustomerAccountXRef;
import com.spring.model.Deposit;
import com.spring.model.Transaction;
import com.spring.model.WithDraw;
import com.spring.repository.AccountRepository;
import com.spring.repository.CustomerAccountXRefRepository;
import com.spring.repository.CustomerRepository;
import com.spring.repository.DepositRepository;
import com.spring.repository.TransactionRepository;
import com.spring.repository.WithDrawRepository;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private DepositRepository depositRepository;
	@Autowired
	private WithDrawRepository withDrawRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
    private CustomerAccountXRefRepository custAccXRefRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;
	
    public BankingServiceImpl(CustomerRepository repository) {
        this.customerRepository=repository;
    }
    
public List<CustomerDetails> findAll() {
    	
    	List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
        	allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
        });
        
        return allCustomerDetails;
    }
   
	public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {
		Optional<Customer> customer1 = customerRepository.findByCustomerNumber(customerDetails.getCustomerNumber());
		if(!customer1.isPresent()) {
		Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		customer.setCreateDateTime(new Date());
		customerRepository.save(customer);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already used this CustomerNumber " +customerDetails.getCustomerNumber());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
	}
    
	public CustomerDetails findByCustomerNumber(Long customerNumber) {
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(customerEntityOpt.isPresent())
			return bankingServiceHelper.convertToCustomerDomain(customerEntityOpt.get());
		
		return null;
	}

	public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
		Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();
			
			if(Optional.ofNullable(unmanagedCustomerEntity.getContactDetails()).isPresent()) {
				
				Contact managedContact = managedCustomerEntity.getContactDetails();
				if(managedContact != null) {
					managedContact.setEmailId(unmanagedCustomerEntity.getContactDetails().getEmailId());
					managedContact.setHomePhone(unmanagedCustomerEntity.getContactDetails().getHomePhone());
					managedContact.setWorkPhone(unmanagedCustomerEntity.getContactDetails().getWorkPhone());
				} else
					managedCustomerEntity.setContactDetails(unmanagedCustomerEntity.getContactDetails());
			}
			
			if(Optional.ofNullable(unmanagedCustomerEntity.getCustomerAddress()).isPresent()) {
				
				Address managedAddress = managedCustomerEntity.getCustomerAddress();
				if(managedAddress != null) {
					managedAddress.setAddress1(unmanagedCustomerEntity.getCustomerAddress().getAddress1());
					managedAddress.setAddress2(unmanagedCustomerEntity.getCustomerAddress().getAddress2());
					managedAddress.setCity(unmanagedCustomerEntity.getCustomerAddress().getCity());
					managedAddress.setState(unmanagedCustomerEntity.getCustomerAddress().getState());
					managedAddress.setZipcode(unmanagedCustomerEntity.getCustomerAddress().getZipcode());
					managedAddress.setCountry(unmanagedCustomerEntity.getCustomerAddress().getCountry());
				} else
					managedCustomerEntity.setCustomerAddress(unmanagedCustomerEntity.getCustomerAddress());
			}
			
			managedCustomerEntity.setUpdateDateTime(new Date());
			managedCustomerEntity.setStatus(unmanagedCustomerEntity.getStatus());
			managedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
			managedCustomerEntity.setMiddleName(unmanagedCustomerEntity.getMiddleName());
			managedCustomerEntity.setLastName(unmanagedCustomerEntity.getLastName());
			managedCustomerEntity.setUpdateDateTime(new Date());
			
			customerRepository.save(managedCustomerEntity);
			
			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
	}

	public ResponseEntity<Object> deleteCustomer(Long customerNumber) {
		
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();
			customerRepository.delete(managedCustomerEntity);
			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist.");
		}
	}

	public ResponseEntity<Object> findByAccountNumber(Long accountNumber) {
		
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);

		if(accountEntityOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToAccountDomain(accountEntityOpt.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number " + accountNumber + " not found.");
		}
		
	}

	public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber) {
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
		
		if(customerEntityOpt.isPresent()) {
			Optional<Account> account = accountRepository.findByAccountNumber(accountInformation.getAccountNumber());
			if(!account.isPresent()) {
				accountRepository.save(bankingServiceHelper.convertToAccountEntity(accountInformation));
                
				custAccXRefRepository.save(CustomerAccountXRef.builder()
						.accountNumber(accountInformation.getAccountNumber())
						.customerNumber(customerNumber)
						.build());
			}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This AccountNumber is already in use.");
			}	
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("New Account created successfully.");
	}
	
public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {
		
		List<Account> accountEntities = new ArrayList<>();
		Account fromAccountEntity = null;
		Account toAccountEntity = null;
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		
		if(customerEntityOpt.isPresent()) {
			
			
			Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
			if(fromAccountEntityOpt.isPresent()) {
				fromAccountEntity = fromAccountEntityOpt.get();
			}
			else {
		
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + transferDetails.getFromAccountNumber() + " not found.");
			}
			
			
			
			Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
			if(toAccountEntityOpt.isPresent()) {
				toAccountEntity = toAccountEntityOpt.get();
			}
			else {
			
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To Account Number " + transferDetails.getToAccountNumber() + " not found.");
			}

			if(fromAccountEntity.getAccountBalance() < transferDetails.getTransferAmount()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
			}
			else {
				synchronized (this) {
					fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance() - transferDetails.getTransferAmount());
					fromAccountEntity.setUpdateDateTime(new Date());
					accountEntities.add(fromAccountEntity);
					
					toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance() + transferDetails.getTransferAmount());
					toAccountEntity.setUpdateDateTime(new Date());
					accountEntities.add(toAccountEntity);
					
					accountRepository.saveAll(accountEntities);
					
					Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails, fromAccountEntity.getAccountNumber(), "DEBIT");
					transactionRepository.save(fromTransaction);
					
					Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails, toAccountEntity.getAccountNumber(), "CREDIT");
					transactionRepository.save(toTransaction);
				}

				return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for Customer Number " + customerNumber);
			}
				
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
		
	}

	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber) {
		List<TransactionDetails> transactionDetails = new ArrayList<>();
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);
		if(accountEntityOpt.isPresent()) {
			Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository.findByAccountNumber(accountNumber);
			if(transactionEntitiesOpt.isPresent()) {
				transactionEntitiesOpt.get().forEach(transaction -> {
					transactionDetails.add(bankingServiceHelper.convertToTransactionDomain(transaction));
				});
			}
		}
		
		return transactionDetails;
	}

	
	public ResponseEntity<Object> cashDeposite( DepositDetails depositDetails,Long accountNumber) {
		List<Account> accountEntities = new ArrayList<>();
		Account toAccountEntity = null;
		
		Optional<Account> toAccountNumberInfo = accountRepository.findByAccountNumber(depositDetails.getToAccountNumber());
		if(toAccountNumberInfo.isPresent()) {
			toAccountEntity = toAccountNumberInfo.get();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + depositDetails.getToAccountNumber() + " not found.");
		}
		
		toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance() + depositDetails.getDepositAmount());
		toAccountEntity.setUpdateDateTime(new Date());
		accountEntities.add(toAccountEntity);
		accountRepository.saveAll(accountEntities);
		
		Deposit toDeposit = bankingServiceHelper.createDeposit(depositDetails, toAccountEntity.getAccountNumber());
		depositRepository.save(toDeposit);
		
		return ResponseEntity.status(HttpStatus.OK).body("Success: Amount deposited successfully to AccountNumber " + accountNumber);
	}

	public ResponseEntity<Object> cashWithDraw(WithDrawDetails withDrawDetails, Long accountNumber) {
		List<Account> accountEntities = new ArrayList<>();
		Account fromAccountEntity = null;
		Optional<Account> fromAccountNumberInfo = accountRepository.findByAccountNumber(withDrawDetails.getAccountNumber());
		if(fromAccountNumberInfo.isPresent()) {
			fromAccountEntity = fromAccountNumberInfo.get();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + withDrawDetails.getAccountNumber() +" Not Found." );
		}
		if(fromAccountEntity.getAccountBalance() < withDrawDetails.getDrawAmount()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Balance.");
		}
		if(fromAccountEntity.getAccountBalance() <= 500) {
			fromAccountEntity.setAccountStatus("InActive");
			accountEntities.add(fromAccountEntity);
			accountRepository.saveAll(accountEntities);
		}
		fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance() - withDrawDetails.getDrawAmount());
		fromAccountEntity.setUpdateDateTime(new Date());
		accountEntities.add(fromAccountEntity);
		accountRepository.saveAll(accountEntities);
		
		WithDraw fromWithDraw = bankingServiceHelper.createWithDraw(withDrawDetails, fromAccountEntity.getAccountNumber());
		withDrawRepository.save(fromWithDraw);
		return ResponseEntity.status(HttpStatus.OK).body("Success: Amount WithDraw Successfully From AccountNumber "+ accountNumber);
	}
}
