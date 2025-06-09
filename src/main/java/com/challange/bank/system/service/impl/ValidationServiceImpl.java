package com.challange.bank.system.service.impl;

import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.exception.BusinessException;
import com.challange.bank.system.exception.ResourceNotFoundException;
import com.challange.bank.system.model.User;
import com.challange.bank.system.model.enums.UserTypeEnum;
import com.challange.bank.system.repository.UserRepository;
import com.challange.bank.system.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final UserRepository userRepository;

    @Override
    public void validateTransaction(TransactionRequestDTO transactionRequestDTO) {
        log.info(">>>[ValidationServiceImpl]: Iniciando validações de transação.");

        User payer = userRepository.findById(transactionRequestDTO.payerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário pagador não encontrado"));

        validateTransfer(payer.getUserType());
        validateSufficientBalancePayer(payer.getBalance(), transactionRequestDTO);
    }

    private void validateTransfer(UserTypeEnum userType) {
        if(userType == UserTypeEnum.MERCHANT) {
            throw new BusinessException("Transação inválida: o pagador não pode ser lojista.");
        }
        log.info(">>>[ValidationServiceImpl]: Validações de Pagador concluídas com sucesso.");
    }

    private void validateSufficientBalancePayer(BigDecimal balancePayer, TransactionRequestDTO transactionRequestDTO) {
        if (balancePayer.compareTo(transactionRequestDTO.value()) < 0){
            throw new BusinessException("Transação inválida: saldo insuficiente.");
        }
        log.info(">>>[ValidationServiceImpl]: Validações de Saldo concluídas com sucesso.");
    }

}
