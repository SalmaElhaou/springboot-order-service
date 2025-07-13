package com.maestro.commande.client;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import com.maestro.commande.client.dto.ProductDTO;

import lombok.Data;
import reactor.core.publisher.Mono;



@Service
public class ProductClient {
	private final WebClient webClient;

    @Autowired
    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/mock").build();
    }

    public Mono<ProductDTO> getProductById(String productId) {
        return webClient.get()
                .uri("/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Produit non trouvé : " + productId)));
    }
}



