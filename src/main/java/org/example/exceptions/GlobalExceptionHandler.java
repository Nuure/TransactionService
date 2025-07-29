package org.example.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.example.dto.ErrorResponseDto;
import org.example.validation.CreateAccountValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.example.validation.ValidationErrors.INVALID_FORMAT;
import static org.example.validation.ValidationErrors.INVALID_REQUEST_FORMAT;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        logger.warn("Validation failed: {} — details: {}", exception.getBindingResult().getTarget(), details);


        ErrorResponseDto error = new ErrorResponseDto(
                "VALIDATION_ERROR",
                "Invalid input data",
                details
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto(exception.getErrorCode(), exception.getMessage(), exception.getDetails()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidFormat(HttpMessageNotReadableException exception) {
        String message = INVALID_REQUEST_FORMAT;

        if (exception.getCause() instanceof InvalidFormatException formatException) {
            String fieldName = formatException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("unknown");
            message = "Invalid format for field '" + fieldName + "'";

            logger.warn("Invalid format for field '{}': provided value = {}, expected type = {}",
                    fieldName,
                    formatException.getValue(),
                    formatException.getTargetType().getSimpleName()
            );
        }

        ErrorResponseDto error = new ErrorResponseDto(
                INVALID_FORMAT,
                message,
                List.of(exception.getLocalizedMessage())
        );

        return ResponseEntity.badRequest().body(error);
    }

}
