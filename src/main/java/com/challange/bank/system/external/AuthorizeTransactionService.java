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


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizeTransactionService{

    private final WebClient webClient;

    public void validateAuthorization() {
        String url = "https://util.devi.tools/api/v2/authorize";

        ClientResponse response = webClient
                .get()
                .uri(url)
                .exchangeToMono(Mono::just)
                .block(); // força execução sincrônica

        if (response.statusCode().is2xxSuccessful() ){
            log.info("AuthorizeTransactionService: Transação autorizada com sucesso.");
            return;
        }
        if (response.statusCode() == HttpStatus.FORBIDDEN) {
            throw new AuthorizationException("Transação não autorizada.");
        }
        throw new BusinessException("Falha para autorizar transação.");
    }
}
