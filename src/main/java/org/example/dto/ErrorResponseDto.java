package org.example.dto;

import java.util.List;

public record ErrorResponseDto(
        String errorCode,
        String message,
        List<String> details
) {}
