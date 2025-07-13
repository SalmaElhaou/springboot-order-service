package com.maestro.commande.client.dto;

import lombok.Data;

@Data
public class ProductDTO {
	private String id;
    private String name;
    private double price;
    private int quantity;
    
    public ProductDTO(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    
	
	
}
