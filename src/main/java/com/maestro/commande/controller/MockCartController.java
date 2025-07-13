package com.maestro.commande.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.maestro.commande.client.dto.CartDTO;
import com.maestro.commande.client.dto.CartItemDTO;

@RestController
@RequestMapping("/mock/cart")
public class MockCartController {

	@GetMapping("/{userId}")
    public CartDTO getCartByUserId(@PathVariable String userId) {
        CartItemDTO item1 = new CartItemDTO();
        item1.setProductId("prod_67890");
        item1.setQuantity(2);
        item1.setUnitPrice(29.99);
        item1.setProductName("samsung");

        CartDTO cart = new CartDTO();
        cart.setUserId(userId);
        cart.setItems(List.of(item1));

        return cart;
    }
}



