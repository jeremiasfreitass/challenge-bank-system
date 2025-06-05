package com.challange.bank.system.external.integration;

import com.challange.bank.system.dto.AuthResponseDTO;
import com.challange.bank.system.exception.BusinessException;
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

    public void validateAuthorization() {
        String url = "https://util.devi.tools/api/v2/authorize";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<AuthResponseDTO> response = httpClientService.sendRequest(
                url,
                HttpMethod.GET,
                null,
                AuthResponseDTO.class,
                headers
        );
        if (!response.getStatusCode().is2xxSuccessful() ){
            throw new BusinessException("Falha para autorizar transação.");
        }
        log.info("AuthorizeTransactionService: Transação autorizada com sucesso.");
    }
}
