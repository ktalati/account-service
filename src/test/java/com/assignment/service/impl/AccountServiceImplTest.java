package com.assignment.service.impl;

import com.assignment.Constants;
import com.assignment.client.TransactionServiceClient;
import com.assignment.entity.Account;
import com.assignment.model.CreateAccountResponse;
import com.assignment.model.CreateTransactionResponse;
import com.assignment.model.CustomerResponse;
import com.assignment.model.TransactionResponse;
import com.assignment.repository.AccountRepository;
import com.assignment.repository.AccountTypeRepository;
import com.assignment.repository.CustomerRepository;
import com.assignment.util.AccountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountTypeRepository accountTypeRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionServiceClient transactionServiceClient;


    @Test
    void test_createAccount_when_valid_request_provided() throws Exception {
        Account mockedAccount = AccountUtil.prepareAccount();
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(AccountUtil.prepareCustomer()));
        Mockito.when(accountTypeRepository.findByType(ArgumentMatchers.anyString())).thenReturn(Optional.of(AccountUtil.prepareAccountType()));
        Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(mockedAccount);
        Mockito.when(transactionServiceClient.createTransaction(ArgumentMatchers.anyInt(), ArgumentMatchers.any())).thenReturn(ResponseEntity.of(Optional.of(new CreateTransactionResponse().message("Transaction created successfully."))));
        CreateAccountResponse accountResponse = accountService.createAccount(Constants.CUSTOMER_ID, Constants.AMOUNT);
        Assertions.assertEquals(Constants.CREATE_ACCOUNT_SUCCESS_MESSAGE, accountResponse.getMessage());
    }

    @Test
    void test_createAccount_when_only_customerId_provided() throws Exception {
        Account mockedAccount = AccountUtil.prepareAccount();
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(AccountUtil.prepareCustomer()));
        Mockito.when(accountTypeRepository.findByType(ArgumentMatchers.anyString())).thenReturn(Optional.of(AccountUtil.prepareAccountType()));
        Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(mockedAccount);
        CreateAccountResponse accountResponse = accountService.createAccount(Constants.CUSTOMER_ID, null);
        Assertions.assertEquals(Constants.CREATE_ACCOUNT_SUCCESS_MESSAGE, accountResponse.getMessage());
    }

    @Test
    void test_createAccount_when_exception_throws() throws Exception{
        Account mockedAccount = AccountUtil.prepareAccount();
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(AccountUtil.prepareCustomer()));
        Mockito.when(accountTypeRepository.findByType(ArgumentMatchers.anyString())).thenReturn(Optional.of(AccountUtil.prepareAccountType()));
        Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(mockedAccount);
        Mockito.doThrow(new InternalError("Error while processing")).when(transactionServiceClient).createTransaction(ArgumentMatchers.anyInt(), ArgumentMatchers.any());
        Assertions.assertThrows(InternalError.class, () -> accountService.createAccount(Constants.CUSTOMER_ID, Constants.AMOUNT));
        Mockito.verify(transactionServiceClient, Mockito.times(1))
                .createTransaction(ArgumentMatchers.anyInt(), ArgumentMatchers.any());
    }

    @Test
    void test_fetchAccountById_when_valid_request_provided() throws Exception{
        TransactionResponse mockedTransactionResponse = AccountUtil.prepareTransactionResponse();
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(AccountUtil.prepareCustomerWithAccount()));
        Mockito.when(transactionServiceClient.fetchTransactionByAccountId(ArgumentMatchers.anyInt())).thenReturn(ResponseEntity.of(Optional.of(mockedTransactionResponse)));
        CustomerResponse actualResponse = accountService.fetchCustomerAccountByAccountId(Constants.CURRENT_ACCOUNT_ID);
        Assertions.assertEquals(mockedTransactionResponse.getAccountId(), actualResponse.getAccountId());
    }

    @Test
    void test_fetchAccountById_when_exception_throws() throws Exception{
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(AccountUtil.prepareCustomer()));
        Mockito.doThrow(new InternalError("Error while processing")).when(transactionServiceClient).fetchTransactionByAccountId(ArgumentMatchers.anyInt());
        Assertions.assertThrows(InternalError.class, () -> accountService.fetchCustomerAccountByAccountId(Constants.CURRENT_ACCOUNT_ID));
        Mockito.verify(transactionServiceClient, Mockito.times(1))
                .fetchTransactionByAccountId(ArgumentMatchers.anyInt());
    }
}