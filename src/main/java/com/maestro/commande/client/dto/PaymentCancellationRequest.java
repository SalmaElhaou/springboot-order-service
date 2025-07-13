package com.maestro.commande.client.dto;


import lombok.Data;

@Data
public class PaymentCancellationRequest {
    private String paymentId;
    private String reason;
}
