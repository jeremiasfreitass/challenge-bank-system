package com.challange.bank.system.controller;

import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.dto.UserDTO;
import com.challange.bank.system.service.TransactionService;
import com.challange.bank.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;


    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequest) {

        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO request) {
        UserDTO created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
