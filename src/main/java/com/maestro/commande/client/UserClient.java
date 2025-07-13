package com.maestro.commande.client;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

import com.maestro.commande.client.dto.UserDTO;

@Service
public class UserClient {

    private final WebClient webClient;

    @Autowired
    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/mock").build();
    }

    // Récupérer l'utilisateur par ID
    public Mono<UserDTO> getUserById(String userId) {
        // Mock temporaire
        UserDTO user = new UserDTO();
        user.setId(userId);
        user.setName("Mock User");
        return Mono.just(user);
    }

    // Valider si l'utilisateur est authentifié via /users/validate
    public Mono<Boolean> validateUser(String userId) {
        Map<String, String> requestBody = Map.of("userId", userId);

        return webClient.post()
            .uri("/users/validate")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Boolean.class)
            .onErrorReturn(false);  // si erreur, considère non validé
    }
}
