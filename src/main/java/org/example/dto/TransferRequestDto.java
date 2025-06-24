package org.example.dto;

import lombok.Data;

@Data
public class TransferRequestDto {
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String currency;
}
