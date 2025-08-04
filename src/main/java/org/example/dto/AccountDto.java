package org.example.dto;

public record AccountDto(
        String accountId,
        Double balance,
        String currency
        ) {
}
