package com.challange.bank.system.external.integration;

import com.challange.bank.system.dto.NotificationRequestDTO;
import com.challange.bank.system.external.client.HttpClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationService {
    private final HttpClientService httpClientService;
    public void notifyUser(NotificationRequestDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        httpClientService.sendRequest(
                "https://util.devi.tools/api/v1/notify",
                HttpMethod.POST,
                request,
                Void.class, // sem resposta de corpo
                headers
        );
    }

}
