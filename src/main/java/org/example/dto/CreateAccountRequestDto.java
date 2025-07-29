package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static org.example.validation.ValidationErrors.CURRENCY_FORMAT_ERROR;
import static org.example.validation.ValidationErrors.CURRENCY_IS_REQUIRED;

public record CreateAccountRequestDto(

        @NotBlank(message = CURRENCY_IS_REQUIRED)
        @Pattern(regexp = "^[A-Z]{3}$", message = CURRENCY_FORMAT_ERROR)
        String currency
) {
}
