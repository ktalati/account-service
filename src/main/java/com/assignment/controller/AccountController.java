package com.assignment.controller;

import com.assignment.model.CreateAccountResponse;
import com.assignment.model.CustomerResponse;
import com.assignment.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController{

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestParam Integer customerId, @RequestParam(required = false) BigDecimal initialCredit) throws Exception {
        log.trace("Create Current Account Initiated...");
        return new ResponseEntity<>(accountService.createAccount(customerId, initialCredit), HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerResponse> fetchAccountById(@RequestParam Integer accountId) throws Exception{
        log.trace("Fetch Details for Account {} initiated", accountId);
        CustomerResponse account = accountService.fetchCustomerAccountByAccountId(accountId);
        log.trace("Fetch Details for Account {} completed", accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}