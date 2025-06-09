package com.challange.bank.system.service;

import com.challange.bank.system.dto.TransactionRequestDTO;

/**
 * Interface responsável por definir os métodos de validação de transações.
 */
public interface ValidationService {
    void validateTransaction(TransactionRequestDTO transactionRequestDTO);
}
