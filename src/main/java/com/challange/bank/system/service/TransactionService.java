package com.challange.bank.system.service;

import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;

/**
 * Interface responsável por definir os métodos de serviço relacionados a transações.
 */
public interface TransactionService {
    TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO);
}
