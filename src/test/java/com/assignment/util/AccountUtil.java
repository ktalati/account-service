package com.assignment.util;

import com.assignment.Constants;
import com.assignment.entity.Account;
import com.assignment.entity.AccountType;
import com.assignment.entity.Customer;
import com.assignment.model.CreateAccountResponse;
import com.assignment.model.TransactionDetail;
import com.assignment.model.TransactionDetailInner;
import com.assignment.model.TransactionResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class AccountUtil {

    public static Customer prepareCustomer(){
        Customer customer = new Customer();
        customer.setId(Constants.CUSTOMER_ID);
        customer.setFirstName("Nick");
        customer.setMiddleName("N");
        customer.setLastName("Gough");
        return customer;
    }

    public static Customer prepareCustomerWithAccount(){
        Customer customer = new Customer();
        customer.setId(Constants.CUSTOMER_ID);
        customer.setFirstName("Nick");
        customer.setMiddleName("N");
        customer.setLastName("Gough");
        Account account = new Account();
        account.setId(Constants.CUSTOMER_ID);
        customer.setAccount(account);
        return customer;
    }

    public static AccountType prepareAccountType(){
        AccountType accountType = new AccountType();
        accountType.setId(123);
        accountType.setType("2");
        return accountType;
    }

    public static Account prepareAccount(){
        Account account = new Account();
        account.setId(Constants.CUSTOMER_ID);
        account.setCustomer(prepareCustomer());
        account.setAccountType(prepareAccountType());
        return account;
    }

    public static CreateAccountResponse prepareCreateAccountResponse(){
        CreateAccountResponse accountResponse = new CreateAccountResponse();
        accountResponse.message(Constants.CREATE_ACCOUNT_SUCCESS_MESSAGE);
        return accountResponse;
    }

    public static TransactionResponse prepareTransactionResponse(){

        TransactionDetailInner transactionDetailInner = new TransactionDetailInner();
        transactionDetailInner.setTransactionId(123456);
        transactionDetailInner.setType("CREDIT");
        transactionDetailInner.setAmount(new BigDecimal(100));
        transactionDetailInner.setCreatedAt(OffsetDateTime.now());

        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.add(transactionDetailInner);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactions(transactionDetail);
        transactionResponse.setAccountId(Constants.CURRENT_ACCOUNT_ID);
        transactionResponse.setBalance(new BigDecimal(100));

        return transactionResponse;
    }
}
