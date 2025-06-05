package com.challange.bank.system.service.impl;

import com.challange.bank.system.dto.NotificationRequestDTO;
import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.exception.ResourceNotFoundException;
import com.challange.bank.system.external.AuthorizeTransactionService;
import com.challange.bank.system.external.NotificationService;
import com.challange.bank.system.model.User;
import com.challange.bank.system.repository.UserRepository;
import com.challange.bank.system.service.TransactionService;
import com.challange.bank.system.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ValidationService validationService;
    private final AuthorizeTransactionService authorizeTransactionService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        validationService.validateTransaction(transactionRequestDTO);
        authorizeTransactionService.validateAuthorization();

        return performTransaction(transactionRequestDTO);
    }

    private TransactionDTO performTransaction(TransactionRequestDTO transactionRequestDTO) {
        User payer = userRepository.findById(transactionRequestDTO.payerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário pagador não encontrado"));

        User payee = userRepository.findById(transactionRequestDTO.payeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário recebedor não encontrado"));

        payer.setBalance(payer.getBalance().subtract(transactionRequestDTO.value()));
        payee.setBalance(payee.getBalance().add(transactionRequestDTO.value()));

        userRepository.save(payer);
        userRepository.save(payee);
        sendNotification(payer, payee, transactionRequestDTO);

        return new TransactionDTO();
    }

    private void sendNotification(User payer, User payee, TransactionRequestDTO transactionRequestDTO) {
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(
                payee.getEmail(),
                "Pagamento de " + transactionRequestDTO.value() + " realizado com sucesso",
                payer.getFirstName() + " " + payer.getLastName(),
                transactionRequestDTO.value()
        );
        notificationService.notifyUser(notificationRequestDTO);
    }
}
