package com.maestro.commande.client.dto;

import lombok.Data;

@Data
public class CartItemDTO {
	private String productId;
    private int quantity;
    private double unitPrice;
    private String productName;
	
	

}
