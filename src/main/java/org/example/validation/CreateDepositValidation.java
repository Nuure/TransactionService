package org.example.validation;

import org.example.dto.DepositRequestDto;
import org.example.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.validation.ValidationErrors.*;

public class CreateDepositValidation {

    private static final Logger logger = LoggerFactory.getLogger(CreateDepositValidation.class);

    public static void validateDepositRequest(DepositRequestDto request) {
        List<String> details = new ArrayList<>();

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
            logger.warn("Validation failed for deposit request: {} — errors: {}", request, details);
            throw new ValidationException(
                    INVALID_INPUT_DATA,
                    VALIDATION_ERROR,
                    details
            );
        }
    }
}
