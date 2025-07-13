package com.maestro.commande.client.dto;

import java.util.List;
import java.util.UUID;

import com.maestro.commande.model.Address;

import lombok.Data;
@Data
public class OrderRequest {

	private String userId;
	private String cartId;
	private List<OrderItemRequest> items;
	private Address shippingAddress;

	
	
    
	
}
