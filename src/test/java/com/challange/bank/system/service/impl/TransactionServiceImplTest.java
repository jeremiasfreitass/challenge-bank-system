package com.challange.bank.system.service.impl;

import com.challange.bank.system.dto.NotificationRequestDTO;
import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.external.AuthorizeTransactionService;
import com.challange.bank.system.external.NotificationService;
import com.challange.bank.system.model.User;
import com.challange.bank.system.model.enums.UserTypeEnum;
import com.challange.bank.system.repository.UserRepository;
import com.challange.bank.system.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private ValidationService validationService;
    @Mock
    private AuthorizeTransactionService authorizeTransactionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    private TransactionRequestDTO validRequest;
    private User payer;
    private User payee;

    @BeforeEach
    void setUp() {
        validRequest = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);

        payer = new User();payer = new User();
        payer.setId(1L);
        payer.setFirstName("Payer");
        payer.setLastName("User");
        payer.setDocument("12345678900");
        payer.setPassword("securePassword");
        payer.setEmail("payer@example.com");
        payer.setBalance(new BigDecimal("500.00"));
        payer.setUserType(UserTypeEnum.COMMON);

        payee = new User();
        payee.setId(2L);
        payee.setFirstName("Payee");
        payee.setLastName("User2");
        payee.setDocument("12345678902");
        payee.setPassword("securePassword2");
        payee.setEmail("payee@example.com");
        payee.setBalance(new BigDecimal("500.00"));
        payee.setUserType(UserTypeEnum.MERCHANT);
    }


    @Test
    void createTransaction_shouldPerformTransactionSuccessfully_whenAllValidationsPass() {
        doNothing().when(validationService).validateTransaction(validRequest);
        doNothing().when(authorizeTransactionService).validateAuthorization();
        when(userRepository.findById(1L)).thenReturn(Optional.of(payer));
        when(userRepository.findById(2L)).thenReturn(Optional.of(payee));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(notificationService).notifyUser(any(NotificationRequestDTO.class));

        TransactionDTO result = transactionService.createTransaction(validRequest);

        assertNotNull(result);
        assertEquals("Transação realizada com sucesso.", result.message());
        verify(validationService, times(1)).validateTransaction(validRequest);
        verify(authorizeTransactionService, times(1)).validateAuthorization();
        verify(userRepository, times(2)).findById(anyLong());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void createTransaction_notShouldPerformTransactionSuccessfully_whenPayerIsMerchant() {
        doNothing().when(validationService).validateTransaction(validRequest);
        doNothing().when(authorizeTransactionService).validateAuthorization();
        when(userRepository.findById(1L)).thenReturn(Optional.of(payer));
        when(userRepository.findById(2L)).thenReturn(Optional.of(payee));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(notificationService).notifyUser(any(NotificationRequestDTO.class));

        TransactionDTO result = transactionService.createTransaction(validRequest);

        assertNotNull(result);
        assertEquals("Transação realizada com sucesso.", result.message());

        verify(validationService, times(1)).validateTransaction(validRequest);
        verify(authorizeTransactionService, times(1)).validateAuthorization();
        verify(userRepository, times(2)).findById(anyLong());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
    }
}