package com.assignment.controller;

import com.assignment.Constants;
import com.assignment.exception.APIException;
import com.assignment.exception.CustomerNotFoundException;
import com.assignment.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService accountService;

    private static final String CREATE_ACCOUNT_ENDPOINT = "/account/create";
    private static final String FETCH_ACCOUNT_DETAILS_ENDPOINT = "/account/fetch";

    @Test
    void test_createAccount_when_valid_request_provided() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(CREATE_ACCOUNT_ENDPOINT)
                .param("customerId", Constants.CUSTOMER_ID.toString())
                .param("initialCredit", Constants.AMOUNT.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(1))
                .createAccount(Constants.CUSTOMER_ID, Constants.AMOUNT);
    }

    @Test
    void test_createAccount_when_only_customerId_provided() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(CREATE_ACCOUNT_ENDPOINT)
                        .param("customerId", Constants.CUSTOMER_ID.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(1))
                .createAccount(Constants.CUSTOMER_ID, null);
    }

    @Test
    void test_createAccount_when_no_input_provided() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post(CREATE_ACCOUNT_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(0))
                .createAccount(null, null);
    }

    @Test
    void test_createAccount_when_exception_throws() throws Exception{
        Mockito.when(accountService.createAccount(Constants.CUSTOMER_ID, Constants.AMOUNT))
                .thenThrow(APIException.class);

        mvc.perform(MockMvcRequestBuilders.post(CREATE_ACCOUNT_ENDPOINT)
                        .param("customerId", Constants.CUSTOMER_ID.toString())
                        .param("initialCredit", Constants.AMOUNT.toString()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(1))
                .createAccount(Constants.CUSTOMER_ID, Constants.AMOUNT);
    }

    @Test
    void test_fetchAccountById_when_valid_request_provided() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get(FETCH_ACCOUNT_DETAILS_ENDPOINT)
                        .param("accountId", Constants.CURRENT_ACCOUNT_ID.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(1))
                .fetchCustomerAccountByAccountId(Constants.CURRENT_ACCOUNT_ID);
    }

    @Test
    void test_fetchAccountById_when_no_input_provided() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get(FETCH_ACCOUNT_DETAILS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(0))
                .fetchCustomerAccountByAccountId(Constants.CURRENT_ACCOUNT_ID);
    }

    @Test
    void test_fetchAccountById_when_exception_throws() throws Exception{
        Mockito.when(accountService.fetchCustomerAccountByAccountId(-88765875))
                .thenThrow(CustomerNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get(FETCH_ACCOUNT_DETAILS_ENDPOINT)
                        .param("accountId", "-88765875"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
        Mockito.verify(accountService, Mockito.times(1))
                .fetchCustomerAccountByAccountId(-88765875);
    }
}