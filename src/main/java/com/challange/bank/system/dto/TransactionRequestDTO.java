package com.challange.bank.system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequestDTO (
        @NotNull(message = "Value não pode ser vazio")
        @DecimalMin(value = "0.01", inclusive = true, message = "Value deve ser maior que zero")
        BigDecimal value,
        @NotNull(message = "Payer não pode ser vazio")
        Long payerId,
        @NotNull(message = "Payee não pode ser vazio")
        Long payeeId){
}
