package validation;

import org.example.dto.TransferRequestDto;
import org.example.exceptions.ValidationException;
import org.example.validation.CreateTransferValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.example.validation.ValidationErrors.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateTransferValidationTest {

    static Stream<Arguments> invalidTransfers() {
        return Stream.of(
                Arguments.of(null, "to", 10.0, "USD", List.of(FROM_ACCOUNT_IS_REQUIRED)),
                Arguments.of("from", null, 10.0, "USD", List.of(TO_ACCOUNT_IS_REQUIRED)),
                Arguments.of("", "to", 10.0, "USD", List.of(FROM_ACCOUNT_IS_REQUIRED)),
                Arguments.of("from", "", 10.0, "USD", List.of(TO_ACCOUNT_IS_REQUIRED)),
                Arguments.of("from", "to", null, "USD", List.of(AMOUNT_IS_REQUIRED)),
                Arguments.of("from", "to", 0.0, "USD", List.of(AMOUNT_MUST_BE_GREATER_THAN_0)),
                Arguments.of("from", "to", -5.0, "USD", List.of(AMOUNT_MUST_BE_GREATER_THAN_0)),
                Arguments.of("from", "to", 10.0, null, List.of(CURRENCY_IS_REQUIRED)),
                Arguments.of("from", "to", 10.0, "", List.of(CURRENCY_IS_REQUIRED)),
                Arguments.of("from", "to", 10.0, "usd", List.of(CURRENCY_FORMAT_ERROR)),
                Arguments.of("from", "to", 10.0, "US", List.of(CURRENCY_FORMAT_ERROR)),
                Arguments.of(null, null, null, null, List.of(
                        FROM_ACCOUNT_IS_REQUIRED,
                        TO_ACCOUNT_IS_REQUIRED,
                        AMOUNT_IS_REQUIRED,
                        CURRENCY_IS_REQUIRED
                )),
                Arguments.of("", null, -1.0, "qwerty", List.of(
                        FROM_ACCOUNT_IS_REQUIRED,
                        TO_ACCOUNT_IS_REQUIRED,
                        AMOUNT_MUST_BE_GREATER_THAN_0,
                        CURRENCY_FORMAT_ERROR
                )),
                Arguments.of("from", "", 0.0, "us", List.of(
                        TO_ACCOUNT_IS_REQUIRED,
                        AMOUNT_MUST_BE_GREATER_THAN_0,
                        CURRENCY_FORMAT_ERROR
                ))
        );
    }

    @ParameterizedTest(name = "from={0}, to={1}, amount={2}, currency={3} → {4}")
    @MethodSource("invalidTransfers")
    void invalidTransferFailsValidation(String from, String to, Double amount, String currency, List<String> expectedDetails) {
        TransferRequestDto dto = new TransferRequestDto(from, to, amount, currency);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> CreateTransferValidation.validateTransferRequest(dto)
        );
        assertEquals(INVALID_INPUT_DATA, exception.getMessage());
        assertEquals(VALIDATION_ERROR, exception.getErrorCode());
        for (String expected : expectedDetails) {
            assertTrue(exception.getDetails().contains(expected));
        }
    }

    static Stream<Arguments> validTransfers() {
        return Stream.of(
                Arguments.of("from", "to", 10000.0, "USD"),
                Arguments.of("12344", "1234", 10000.0, "USD"),
                Arguments.of("abc", "xyz", 0.01, "EUR")
        );
    }

    @ParameterizedTest(name = "Valid transfer: from={0}, to={1}, amount={2}, currency={3}")
    @MethodSource("validTransfers")
    void validTransferPassesValidation(String from, String to, Double amount, String currency) {
        TransferRequestDto dto = new TransferRequestDto(from, to, amount, currency);
        assertDoesNotThrow(() -> CreateTransferValidation.validateTransferRequest(dto));
    }
}
