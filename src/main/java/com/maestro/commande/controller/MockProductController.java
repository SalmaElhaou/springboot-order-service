package com.maestro.commande.controller;

import org.springframework.web.bind.annotation.*;

import com.maestro.commande.client.dto.ProductDTO;

@RestController
@RequestMapping("/mock/products")
public class MockProductController {

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String id) {
        return new ProductDTO(id, "Produit Test", 49.99,2);
    }
}

