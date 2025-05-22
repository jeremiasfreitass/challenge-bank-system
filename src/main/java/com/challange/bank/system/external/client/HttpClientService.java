package com.challange.bank.system.external.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class HttpClientService {
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> sendRequest(
            String url,
            HttpMethod method,
            Object body,
            Class<T> responseType,
            HttpHeaders headers
    ) {
        HttpEntity<?> requestEntity = (body != null)
                ? new HttpEntity<>(body, headers)
                : new HttpEntity<>(headers);

        return restTemplate.exchange(url, method, requestEntity, responseType);
    }
}
