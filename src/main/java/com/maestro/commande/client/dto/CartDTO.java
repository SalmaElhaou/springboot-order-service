package com.maestro.commande.client.dto;

import lombok.Data;

import java.util.Collection;
import java.util.List;

import com.maestro.commande.client.dto.CartItemDTO;
import com.maestro.commande.model.OrderItem;

@Data
public class CartDTO {
	private String id;
	private String userId;
    private List<CartItemDTO> items;
	

}
