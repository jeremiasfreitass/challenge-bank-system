package com.challange.bank.system.external;


import com.challange.bank.system.exception.AuthorizationException;
import com.challange.bank.system.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizeTransactionServiceTest {
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @InjectMocks
    private AuthorizeTransactionService authorizeTransactionService;

    private final String URL = "https://util.devi.tools/api/v2/authorize";

    @BeforeEach
    void setUp() {
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(eq(URL))).thenReturn(requestHeadersSpecMock);
    }

    @Test
    void validateAuthorization_shouldPass_whenResponseIs2xx() {
        ClientResponse mockSuccessfulResponse = ClientResponse.create(HttpStatus.OK).build();

        // Configura o último passo da cadeia mockada para retornar o Mono com a resposta de sucesso
        when(requestHeadersSpecMock.exchangeToMono(any(Function.class)))
                .thenReturn(Mono.just(mockSuccessfulResponse));

        assertDoesNotThrow(() -> authorizeTransactionService.validateAuthorization());
    }

    @Test
    void validateAuthorization_notShouldPass_whenNotAuthorize() {
        ClientResponse mockNotAuthorizeResponse = ClientResponse.create(HttpStatus.FORBIDDEN).build();

        // Configura o último passo da cadeia mockada para retornar o Mono com a resposta de sucesso
        when(requestHeadersSpecMock.exchangeToMono(any(Function.class)))
                .thenReturn(Mono.just(mockNotAuthorizeResponse));

        assertThrows(AuthorizationException.class,() -> authorizeTransactionService.validateAuthorization());
    }

    @Test
    void validateAuthorization_notShouldPass_whenFailsRequest() {
        ClientResponse mockFailsResponse = ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build();

        // Configura o último passo da cadeia mockada para retornar o Mono com a resposta de sucesso
        when(requestHeadersSpecMock.exchangeToMono(any(Function.class)))
                .thenReturn(Mono.just(mockFailsResponse));

        assertThrows(BusinessException.class,() -> authorizeTransactionService.validateAuthorization());
    }

}