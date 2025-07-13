package com.maestro.commande.client.dto;

import lombok.Data;

@Data
public class PaymentConfirmationRequest {
	private String paymentId;
    private String paymentMethod;

}
