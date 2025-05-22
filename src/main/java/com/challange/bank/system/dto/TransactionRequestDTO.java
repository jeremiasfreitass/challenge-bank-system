package com.challange.bank.system.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO (
        BigDecimal value,
        Long payerId,
        Long payeeId){
}
