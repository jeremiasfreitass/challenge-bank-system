package com.challange.bank.system.service;

import com.challange.bank.system.dto.TransactionRequestDTO;

public interface ValidationService {
    void validateTransaction(TransactionRequestDTO transactionRequestDTO);
}
