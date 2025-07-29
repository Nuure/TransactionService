package validation;

import org.example.controller.AccountsController;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.example.validation.ValidationErrors.CURRENCY_FORMAT_ERROR;
import static org.example.validation.ValidationErrors.CURRENCY_IS_REQUIRED;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountsController.class)
@ContextConfiguration(classes = org.example.Application.class)
class CreateAccountRequestDtoValidationTest {

    @Autowired
    private MockMvc mockMvc;

    static Stream<Arguments> invalidAccounts() {
        return Stream.of(
                Arguments.of("{\"currency\": \"\"}", CURRENCY_IS_REQUIRED),
                Arguments.of("{\"currency\": null}", CURRENCY_IS_REQUIRED),
                Arguments.of("{\"currency\": \"eur\"}", CURRENCY_FORMAT_ERROR),
                Arguments.of("{\"currency\": \"AL\"}", CURRENCY_FORMAT_ERROR),
                Arguments.of("{}", CURRENCY_IS_REQUIRED)
        );
    }

    @ParameterizedTest(name = "Invalid currency: {0}")
    @MethodSource("invalidAccounts")
    void invalidCreateAccountRequestFailsValidation(String json, String expectedError) throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", hasItem(org.hamcrest.Matchers.containsString(expectedError))));
    }

    static Stream<String> validAccounts() {
        return Stream.of(
                "{\"currency\": \"USD\"}",
                "{\"currency\": \"EUR\"}",
                "{\"currency\": \"JPY\"}",
                "{\"currency\": \"QWE\"}"
        );
    }

    @ParameterizedTest(name = "Valid currency: {0}")
    @MethodSource("validAccounts")
    void validCreateAccountRequestPassesValidation(String json) throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
