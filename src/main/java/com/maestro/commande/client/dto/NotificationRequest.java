package com.maestro.commande.client.dto;



import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;



import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String type;
    private NotificationData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NotificationData {
        private String orderNumber;
        private String customerName;
        private String email;
        private String paymentMethod;
        private List<ItemDTO> items;
        private Shipping shipping;
        private ShippingAddress shippingAddress;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDTO {
        private String name;
        private double price;
        private int quantity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Shipping {
        private String cost; // "to be calculated"
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShippingAddress {
        private String street;
        private String city;
        private String zip;
        private String country;
    }
}


