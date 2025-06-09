package com.challange.bank.system.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        List<String> message,
        String path
) {
}
