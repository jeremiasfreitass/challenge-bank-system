package com.challange.bank.system.external;

import com.challange.bank.system.exception.AuthorizationException;
import com.challange.bank.system.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizeTransactionService{

    private final WebClient webClient;

    public void validateAuthorization() {
        log.info(">>>[AuthorizeTransactionService]: Iniciando validação de autorização de transação.");
        ClientResponse response = webClient
                .get()
                .uri("https://util.devi.tools/api/v2/authorize")
                .exchangeToMono(Mono::just)
                .block(); // força execução sincrônica

        if (Objects.nonNull(response)) {
            if (response.statusCode().is2xxSuccessful()) {
                log.info(">>>[AuthorizeTransactionService]: Transação autorizada com sucesso.");
                return;
            }
            if (response.statusCode() == HttpStatus.FORBIDDEN) {
                log.warn(">>>[AuthorizeTransactionService]: Transação não autorizada.");
                throw new AuthorizationException("Transação não autorizada.");
            }
        }
        log.error(">>>[AuthorizeTransactionService]: Falha ao autorizar transação. Status: {}", response.statusCode());
        throw new BusinessException("Falha para autorizar transação.");
    }
}
