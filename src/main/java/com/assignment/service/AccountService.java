package com.assignment.service;

import com.assignment.model.CreateAccountResponse;
import com.assignment.model.CustomerResponse;

import java.math.BigDecimal;

public interface AccountService {

    CreateAccountResponse createAccount(Integer accountId, BigDecimal initialAmount) throws Exception;

    CustomerResponse fetchCustomerAccountByAccountId(Integer accountId);

}