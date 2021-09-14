package com.assignment.client;

import com.assignment.model.CreateTransactionResponse;
import com.assignment.model.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "transaction-service")
public interface TransactionServiceClient {

    @PostMapping("/transaction/create")
    ResponseEntity<CreateTransactionResponse> createTransaction(@RequestParam Integer accountId, @RequestParam BigDecimal amount);

    @GetMapping("/transaction/fetchTransactionByAccount")
    ResponseEntity<TransactionResponse> fetchTransactionByAccountId(@RequestParam Integer accountId);

}
