package com.challange.bank.system.controller;

import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.service.TransactionService;
import com.challange.bank.system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequest) {

        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }
}
