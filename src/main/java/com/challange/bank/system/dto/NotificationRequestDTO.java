package com.challange.bank.system.dto;

import java.math.BigDecimal;

public record NotificationRequestDTO(
        String email,
        String message,
        String namePayer,
        BigDecimal value) {
}
