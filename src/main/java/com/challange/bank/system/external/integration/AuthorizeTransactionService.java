package com.challange.bank.system.external.integration;

import com.challange.bank.system.dto.AuthResponseDTO;
import com.challange.bank.system.external.client.HttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizeTransactionService{

    private final HttpClientService httpClientService;

    public void validateTransactionAuthorization() {
        String url = "https://api.externa.com/authorize";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<AuthResponseDTO> response = httpClientService.sendRequest(
                url,
                HttpMethod.GET,
                null,
                AuthResponseDTO.class,
                headers
        );
        if (response.getStatusCode().value() != HttpStatus.OK.value()){
            throw new RuntimeException("Falha para autorizar transação.");
        }
        log.info("AuthorizeTransactionService: Transação autorizada com sucesso.");
    }
}
