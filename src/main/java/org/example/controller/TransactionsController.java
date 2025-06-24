package org.example.controller;

import org.example.dto.TransferRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/transactions")
public class TransactionsController {
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping("/transfer")
    public ResponseEntity<String> createTransfer(@RequestBody TransferRequestDto body) {
        logger.info("Received request to transfer from {} to {} of {} {}",
                body.getFromAccount(),
                body.getToAccount(),
                body.getAmount(),
                body.getCurrency());

        String response = String.format(
                "Transferring %.2f %s from account %s to account %s",
                body.getAmount(),
                body.getCurrency(),
                body.getFromAccount(),
                body.getToAccount()
        );
        return ResponseEntity.status(201).body(response);
    }
}
