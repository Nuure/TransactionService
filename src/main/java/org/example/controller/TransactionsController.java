package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.TransferRequestDto;
import org.example.validation.CreateTransferValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/transactions")
public class TransactionsController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @PostMapping("/transfer")
//    public ResponseEntity<String> createTransfer(@RequestBody @Valid TransferRequestDto body) {
    public ResponseEntity<String> createTransfer(@RequestBody TransferRequestDto body) {
        CreateTransferValidation.validateTransferRequest(body);
        logger.info("Received request to transfer");
        logger.debug("Request body: {}", body);
        return ResponseEntity.ok("Transfer successful");
    }
}
