package com.challange.bank.system.service;

import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO);
}
