package validation;

import org.example.dto.CreateAccountRequestDto;
import org.example.exceptions.ValidationException;
import org.example.validation.CreateAccountValidation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.example.validation.ValidationErrors.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateAccountValidationTest {

    static Stream<Arguments> invalidCurrencies() {
        return Stream.of(
                Arguments.of(null, CURRENCY_IS_REQUIRED),
                Arguments.of("", CURRENCY_IS_REQUIRED),
                Arguments.of("usd", CURRENCY_FORMAT_ERROR),
                Arguments.of("US", CURRENCY_FORMAT_ERROR)
        );
    }

    @ParameterizedTest(name = "When currency = \"{0}\", expect ValidationException: {1}")
    @MethodSource("invalidCurrencies")
    void invalidCurrencyFailsValidation(String currency, String expectedDetail) {
        CreateAccountRequestDto dto = new CreateAccountRequestDto(currency);
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> CreateAccountValidation.validateCreateAccount(dto)
        );
        assertEquals(INVALID_INPUT_DATA, exception.getMessage());
        assertEquals(VALIDATION_ERROR, exception.getErrorCode());
        assertTrue(exception.getDetails().contains(expectedDetail));
    }

    static Stream<String> validCurrencies() {
        return Stream.of("USD", "EUR", "JPY");
    }

    @ParameterizedTest(name = "Valid currency \"{0}\" passes validation")
    @MethodSource("validCurrencies")
    void validCurrencyPassesValidation(String currency) {
        CreateAccountRequestDto dto = new CreateAccountRequestDto(currency);
        assertDoesNotThrow(() -> CreateAccountValidation.validateCreateAccount(dto));
    }
}
