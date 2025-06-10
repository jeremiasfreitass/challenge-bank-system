package com.challange.bank.system.service.impl;


import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.exception.BusinessException;
import com.challange.bank.system.model.User;
import com.challange.bank.system.model.enums.UserTypeEnum;
import com.challange.bank.system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ValidationServiceImpl validationService;

    private User payer;

    @BeforeEach
    void setUp() {

        payer = new User();payer = new User();
        payer.setId(1L);
        payer.setFirstName("Payer");
        payer.setLastName("User");
        payer.setDocument("12345678900");
        payer.setPassword("securePassword");
        payer.setEmail("payer@example.com");
        payer.setBalance(new BigDecimal("500.00"));
        payer.setUserType(UserTypeEnum.COMMON);
    }

    @Test
    void validateTransaction_shouldPass_whenPayerIsValidAndHasSufficientBalance() {
        TransactionRequestDTO validRequest = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);

        when(userRepository.findById(validRequest.payerId())).thenReturn(Optional.of(payer));

        assertDoesNotThrow(() -> validationService.validateTransaction(validRequest));
    }

    @Test
    void validateTransaction_notShouldPass_whenPayerIsMerchant() {
        payer.setUserType(UserTypeEnum.MERCHANT);
        TransactionRequestDTO validRequest = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);

        when(userRepository.findById(validRequest.payerId())).thenReturn(Optional.of(payer));

        assertThrows(BusinessException.class, () -> validationService.validateTransaction(validRequest));
    }

    @Test
    void validateTransaction_notShouldPass_whenPayerIsNotSufficientBalance() {
        payer.setBalance(new BigDecimal("50.00"));
        TransactionRequestDTO validRequest = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);

        when(userRepository.findById(validRequest.payerId())).thenReturn(Optional.of(payer));

        assertThrows(BusinessException.class, () -> validationService.validateTransaction(validRequest));
    }

    @Test
    void validateTransaction_notShouldPass_whenPayerIsNotFound() {
        TransactionRequestDTO validRequest = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);

        when(userRepository.findById(validRequest.payerId())).thenThrow(new BusinessException("Usuário pagador não encontrado"));

        assertThrows(BusinessException.class, () -> validationService.validateTransaction(validRequest));
    }
}