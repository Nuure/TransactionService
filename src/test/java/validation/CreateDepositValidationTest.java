package validation;

import org.example.dto.DepositRequestDto;
import org.example.exceptions.ValidationException;
import org.example.validation.CreateDepositValidation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.example.validation.ValidationErrors.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateDepositValidationTest {

    static Stream<Arguments> invalidDeposits() {
        return Stream.of(
                Arguments.of(null, "USD", List.of(AMOUNT_IS_REQUIRED)),
                Arguments.of(0.0, "USD", List.of(AMOUNT_MUST_BE_GREATER_THAN_0)),
                Arguments.of(-5.0, "USD", List.of(AMOUNT_MUST_BE_GREATER_THAN_0)),
                Arguments.of(10.0, null, List.of(CURRENCY_IS_REQUIRED)),
                Arguments.of(436.3, "", List.of(CURRENCY_IS_REQUIRED)),
                Arguments.of(35324.2342, "usd", List.of(CURRENCY_FORMAT_ERROR)),
                Arguments.of(10.0, "US", List.of(CURRENCY_FORMAT_ERROR)),

                Arguments.of(null, null, List.of(AMOUNT_IS_REQUIRED, CURRENCY_IS_REQUIRED)),
                Arguments.of(null, "us", List.of(AMOUNT_IS_REQUIRED, CURRENCY_FORMAT_ERROR)),
                Arguments.of(0.0, null, List.of(AMOUNT_MUST_BE_GREATER_THAN_0, CURRENCY_IS_REQUIRED)),
                Arguments.of(-10.0, "", List.of(AMOUNT_MUST_BE_GREATER_THAN_0, CURRENCY_IS_REQUIRED)),
                Arguments.of(-10.0, "us", List.of(AMOUNT_MUST_BE_GREATER_THAN_0, CURRENCY_FORMAT_ERROR))

                // todo should we add cases for INVALID_FORMAT?
                );
    }

    @ParameterizedTest(name = "amount = {0}, currency = \"{1}\" → expect ValidationException: {2}")
    @MethodSource("invalidDeposits")
    void invalidDepositFailsValidation(Double amount, String currency, List<String> expectedDetails) {
        DepositRequestDto dto = new DepositRequestDto(amount, currency);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> CreateDepositValidation.validateDepositRequest(dto)
        );
        assertEquals(INVALID_INPUT_DATA, exception.getMessage());
        assertEquals(VALIDATION_ERROR, exception.getErrorCode());
        for (String expectedDetail : expectedDetails) {
            assertTrue(exception.getDetails().contains(expectedDetail));
        }
    }

    static Stream<Arguments> validDeposits() {
        return Stream.of(
                Arguments.of(10.0, "USD"),
                Arguments.of(0.01, "EUR"),
                Arguments.of(1000.0, "JPY")
        );
    }

    @ParameterizedTest(name = "Valid deposit: amount = {0}, currency = \"{1}\" passes validation")
    @MethodSource("validDeposits")
    void validDepositPassesValidation(Double amount, String currency) {
        DepositRequestDto dto = new DepositRequestDto(amount, currency);
        assertDoesNotThrow(() -> CreateDepositValidation.validateDepositRequest(dto));
    }
}
