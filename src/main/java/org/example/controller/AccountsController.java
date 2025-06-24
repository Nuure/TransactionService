package org.example.controller;

import org.example.dto.CreateAccountRequestDto;
import org.example.dto.DepositRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/accounts")
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequestDto body) {
        logger.info("Received request to create an account with currency {}.", body.getCurrency());
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<String> getAccount(@PathVariable("accountId") String accountId) {
        logger.info("Received request to get info about account {}.", accountId);
        return ResponseEntity.ok("Info for account " + accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> createDeposit(@PathVariable("accountId") String accountId,
                                                @RequestBody DepositRequestDto body) {
        logger.info("Received request to create a deposit for account {}: {} {}.", accountId,
                body.getAmount(), body.getCurrency());
        return ResponseEntity.status(201).body("Created deposit for account " + accountId);
    }
}
