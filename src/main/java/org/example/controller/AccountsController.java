package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.AccountDto;
import org.example.dto.CreateAccountRequestDto;
import org.example.dto.DepositRequestDto;
import org.example.validation.CreateAccountValidation;
import org.example.validation.CreateDepositValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/accounts")
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid CreateAccountRequestDto body) {
//    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequestDto body) {
        CreateAccountValidation.validateCreateAccount(body);
        logger.info("Received request to create an account");
        logger.debug("Request body: {}", body);
        AccountDto response = new AccountDto("123", 0.0, body.currency()); //todo change to business logic
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("accountId") String accountId) {
        logger.info("Received request to get info about account {}.", accountId);
        AccountDto response = new AccountDto("123", 0.0, "USD"); //todo change to business logic
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDto> createDeposit(@PathVariable("accountId") String accountId,
                                                @RequestBody @Valid DepositRequestDto body) {
//                                                @RequestBody DepositRequestDto body) {
        CreateDepositValidation.validateDepositRequest(body);
        logger.info("Received request to create a deposit for account {}.", accountId);
        logger.debug("Request body: {}", body);
        AccountDto response = new AccountDto("123", 100.0, "USD"); //todo change to business logic
        return ResponseEntity.ok(response);
    }
}
