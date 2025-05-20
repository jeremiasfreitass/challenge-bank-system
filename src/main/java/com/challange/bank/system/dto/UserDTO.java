package com.challange.bank.system.dto;

import com.challange.bank.system.model.enums.UserTypeEnum;

import java.math.BigDecimal;

public record UserDTO (
        Long id,
        String firstName,
        String lastName,
        String email,
        UserTypeEnum userType,
        BigDecimal balance) {
}
