package com.maestro.commande.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maestro.commande.client.dto.NotificationRequest;

import org.springframework.http.MediaType;

import reactor.core.publisher.Mono;

@Service
public class NotificationClient {

    private final WebClient webClient;

    public NotificationClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://app.swaggerhub.com/apis-docs/none-769/accent/1.0.0#") // ⚠️ عوضها بالرابط الصحيح
                .build();
    }

    public Mono<Void> sendOrderConfirmation(NotificationRequest request) {
        return webClient.post()
                .uri("/Notifications/sendNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .then(); // return Mono<Void>
    }
}

