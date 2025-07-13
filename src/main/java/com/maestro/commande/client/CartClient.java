package com.maestro.commande.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.maestro.commande.client.dto.CartDTO;

@Service
public class CartClient {

    private final WebClient webClient;

    @Autowired
    public CartClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("http://localhost:8080/mock") 
            .build();
    }


    public Mono<CartDTO> getCartByUserId(String userId) {
        return webClient.get()
                .uri("/cart/{userId}", userId)
                .retrieve()
                .bodyToMono(CartDTO.class);
    }
}
