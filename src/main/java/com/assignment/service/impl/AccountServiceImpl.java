package com.assignment.service.impl;

import com.assignment.client.TransactionServiceClient;
import com.assignment.entity.Account;
import com.assignment.entity.AccountType;
import com.assignment.entity.Customer;
import com.assignment.enums.AccountTypes;
import com.assignment.exception.BadRequestException;
import com.assignment.exception.CustomerNotFoundException;
import com.assignment.mapper.CustomerMapper;
import com.assignment.model.CreateAccountResponse;
import com.assignment.model.CustomerResponse;
import com.assignment.model.TransactionResponse;
import com.assignment.repository.AccountRepository;
import com.assignment.repository.AccountTypeRepository;
import com.assignment.repository.CustomerRepository;
import com.assignment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountTypeRepository accountTypeRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private final TransactionServiceClient transactionServiceClient;

    @Override
    public CreateAccountResponse createAccount(Integer customerId, BigDecimal initialAmount) throws Exception {
        try{
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
            AccountType accountType = accountTypeRepository.findByType(AccountTypes.CURRENT_ACCOUNT.getKey()).orElseThrow(() -> new Exception("Account Type Not Found"));

            final Account currentAccount;
            if(customer.getAccount()!=null){
                throw new BadRequestException("Account already Exists");
            }else {
                Account account = new Account();
                account.setAccountType(accountType);
                account.setCustomer(customer);
                currentAccount = accountRepository.save(account);
            }

            if(initialAmount!=null && initialAmount.intValue() > 0){
                log.trace("Transaction initiated for Account {}", currentAccount.getId());
                transactionServiceClient.createTransaction(currentAccount.getId(), initialAmount);
                log.trace("Transaction Completed Successfully for Account {}", currentAccount.getId());
            }
            log.trace("Current Account Created Successfully");
            return new CreateAccountResponse().message("Current Account Created Successfully.");
        }catch (Exception e){
            log.error("Error while creating account {} {}",customerId, e.getMessage());
            throw e;

        }
    }

    @Override
    public CustomerResponse fetchCustomerAccountByAccountId(Integer accountId) {
        try {
            Customer customer = customerRepository.findById(accountId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found for Account..."));
            log.trace("Fetch Transactions by Account {} initiated", accountId);
            ResponseEntity<TransactionResponse> transactionResponse = transactionServiceClient.fetchTransactionByAccountId(accountId);
            log.trace("Fetch Transactions by Account {} completed", accountId);
            return CustomerMapper.CUSTOMER_MAPPER.map(customer, transactionResponse.getBody());
        }catch (Exception e){
            log.error("Error while fetching account {} {}", accountId, e.getMessage());
            throw e;
        }
    }
}