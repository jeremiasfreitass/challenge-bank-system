package com.challange.bank.system.external;


import com.challange.bank.system.dto.NotificationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;
    @InjectMocks
    private NotificationService notificationService;

    private final String URL = "https://util.devi.tools/api/v1/notify";
    private final String EXPECTED_EMPTY_BODY = "";

    private NotificationRequestDTO notificationRequest;

    @BeforeEach
    void setUp() {
        notificationRequest = new NotificationRequestDTO(
                "test@gmail.com",
                "Test message","Name Test", BigDecimal.TEN);

        // Configuração comum para a cadeia de mocks do WebClient
        when(webClientMock.post()).thenReturn(requestBodyUriSpecMock);
        when(requestBodyUriSpecMock.uri(eq(URL))).thenReturn(requestBodySpecMock);
        when(requestBodySpecMock.contentType(eq(MediaType.APPLICATION_JSON))).thenReturn(requestBodySpecMock); // contentType retorna RequestBodySpec
        when(requestBodySpecMock.bodyValue(eq(EXPECTED_EMPTY_BODY))).thenReturn(requestHeadersSpecMock); // bodyValue retorna RequestHeadersSpec
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    }

    @Test
    void notifyUser_shouldPass_whenExternalServiceSucceeds() {
        ResponseEntity<Void> mockResponseEntity = new ResponseEntity(HttpStatus.OK);

        when(responseSpecMock.toBodilessEntity()).thenReturn(Mono.just(mockResponseEntity));

        notificationService.notifyUser(notificationRequest);

        // Verifica se a cadeia WebClient foi chamada corretamente
        verify(webClientMock).post();
        verify(requestBodyUriSpecMock).uri(URL);
        verify(requestBodySpecMock).contentType(MediaType.APPLICATION_JSON);
        verify(requestBodySpecMock).bodyValue(EXPECTED_EMPTY_BODY);
        verify(requestHeadersSpecMock).retrieve();
        verify(responseSpecMock).toBodilessEntity();
    }

    @Test
    void notifyUser_notShouldPass_whenExternalServiceFails() {

        WebClientResponseException mockHttpError = new WebClientResponseException(
                "Client Error", HttpStatus.BAD_REQUEST.value(), "Bad Request", null, null, null);

        when(responseSpecMock.toBodilessEntity()).thenReturn(Mono.error(mockHttpError));

        notificationService.notifyUser(notificationRequest);

        // Verifica se a cadeia WebClient foi chamada corretamente
        verify(webClientMock).post();
        verify(requestBodyUriSpecMock).uri(URL);
        verify(requestBodySpecMock).contentType(MediaType.APPLICATION_JSON);
        verify(requestBodySpecMock).bodyValue(EXPECTED_EMPTY_BODY);
        verify(requestHeadersSpecMock).retrieve();
        verify(responseSpecMock).toBodilessEntity();
    }
}