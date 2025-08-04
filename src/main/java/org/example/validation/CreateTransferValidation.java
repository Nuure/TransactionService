package org.example.validation;

import org.example.dto.TransferRequestDto;
import org.example.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.validation.ValidationErrors.*;

public class CreateTransferValidation {

    private static final Logger logger = LoggerFactory.getLogger(CreateTransferValidation.class);

    public static void validateTransferRequest(TransferRequestDto request) {
        List<String> details = new ArrayList<>();

        // todo for now no validation for the format of accountId
        if (request.fromAccount() == null || request.fromAccount().isBlank()) {
            details.add(FROM_ACCOUNT_IS_REQUIRED);
        }

        // todo should we change here fromAccount != toAccount ?
        if (request.toAccount() == null || request.toAccount().isBlank()) {
            details.add(TO_ACCOUNT_IS_REQUIRED);
        }

        if (request.amount() == null) {
            details.add(AMOUNT_IS_REQUIRED);
        } else if (request.amount() < 0.01) {
            details.add(AMOUNT_MUST_BE_GREATER_THAN_0);
        }

        if (request.currency() == null || request.currency().isBlank()) {
            details.add(CURRENCY_IS_REQUIRED);
        } else if (!request.currency().matches("^[A-Z]{3}$")) {
            details.add(CURRENCY_FORMAT_ERROR);
        }

        if (!details.isEmpty()) {
            logger.warn("Validation failed for transfer request: {} — errors: {}", request, details);
            throw new ValidationException(
                    INVALID_INPUT_DATA,
                    VALIDATION_ERROR,
                    details
            );
        }
    }
}
