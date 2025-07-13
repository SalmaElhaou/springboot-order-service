package com.maestro.commande.client.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
	private String productId;
    private int quantity;
    private double unitPrice;
	
	

}
