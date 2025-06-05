package com.challange.bank.system.service.impl;

import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.exception.BusinessException;
import com.challange.bank.system.exception.ResourceNotFoundException;
import com.challange.bank.system.model.User;
import com.challange.bank.system.model.enums.UserTypeEnum;
import com.challange.bank.system.repository.UserRepository;
import com.challange.bank.system.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;

    @Override
    public void validateTransaction(TransactionRequestDTO transactionRequestDTO) {
        User payer = userRepository.findById(transactionRequestDTO.payerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário pagador não encontrado"));

        validateTransfer(payer.getUserType());
        validateSufficientBalancePayer(payer.getBalance(), transactionRequestDTO);
    }

    private void validateTransfer(UserTypeEnum userType) {
        if(userType == UserTypeEnum.MERCHANT) {
            throw new BusinessException("Transação inválida: o pagador não pode ser lojista.");
        }
    }

    private void validateSufficientBalancePayer(BigDecimal balancePayer, TransactionRequestDTO transactionRequestDTO) {
        if (balancePayer.compareTo(transactionRequestDTO.value()) < 0){
            throw new BusinessException("Transação inválida: saldo insuficiente.");
        }
    }

}
