package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static org.example.validation.ValidationErrors.*;

public record TransferRequestDto(

        @NotBlank(message = FROM_ACCOUNT_IS_REQUIRED)
        String fromAccount,

        @NotBlank(message = TO_ACCOUNT_IS_REQUIRED)
        String toAccount,

        @NotNull(message = AMOUNT_IS_REQUIRED)
        @DecimalMin(value = "0.01", inclusive = true, message = AMOUNT_MUST_BE_GREATER_THAN_0)
        Double amount,

        @NotBlank(message = CURRENCY_IS_REQUIRED)
        @Pattern(regexp = "^[A-Z]{3}$", message = CURRENCY_FORMAT_ERROR)
        String currency
) {
}
