package org.example.dto;

import lombok.Data;

@Data
public class DepositRequestDto {
    private double amount;
    private String currency;
}
