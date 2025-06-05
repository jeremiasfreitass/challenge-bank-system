package com.challange.bank.system.external;

import com.challange.bank.system.dto.NotificationRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationService {
    private final WebClient webClient;
    public void notifyUser(NotificationRequestDTO request) {
        webClient.post()
                .uri("https://util.devi.tools/api/v1/notify")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("") //Envio de dados não necessário no momento
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(response -> {
                    log.info("NotificationService: Notificação enviada com sucesso.");
                })
                .doOnError(error -> {
                    log.error("NotificationService: Erro ao enviar notificação: {}", error.getMessage());
                })
                .subscribe();
    }
}
