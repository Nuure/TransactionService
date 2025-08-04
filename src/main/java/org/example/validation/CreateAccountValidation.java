package org.example.validation;

import org.example.dto.CreateAccountRequestDto;
import org.example.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.validation.ValidationErrors.*;

public class CreateAccountValidation {

    private static final Logger logger = LoggerFactory.getLogger(CreateAccountValidation.class);

    public static void validateCreateAccount(CreateAccountRequestDto request) {
        List<String> details = new ArrayList<>();

        if (request.currency() == null || request.currency().isBlank()) {
            details.add(CURRENCY_IS_REQUIRED);
        } else if (!request.currency().matches("^[A-Z]{3}$")) {
            details.add(CURRENCY_FORMAT_ERROR);
        }

        if (!details.isEmpty()) {
            logger.warn("Validation failed for create account request: {} — errors: {}", request, details);
            throw new ValidationException(
                    INVALID_INPUT_DATA,
                    VALIDATION_ERROR,
                    details
            );
        }
    }
}
