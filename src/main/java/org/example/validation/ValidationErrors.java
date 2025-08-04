package org.example.validation;

public class ValidationErrors {
    public static final String CURRENCY_IS_REQUIRED = "Currency is required";
    public static final String CURRENCY_FORMAT_ERROR = "Currency must be in ISO 4217 format (e.g. USD, EUR)";
    public static final String AMOUNT_IS_REQUIRED = "Amount is required";
    public static final String AMOUNT_MUST_BE_GREATER_THAN_0 = "Amount must be greater than 0";
    public static final String FROM_ACCOUNT_IS_REQUIRED = "fromAccount is required";
    public static final String TO_ACCOUNT_IS_REQUIRED = "toAccount is required";

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String INVALID_FORMAT = "INVALID_FORMAT";
    public static final String INVALID_INPUT_DATA = "Invalid input data";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format";

}