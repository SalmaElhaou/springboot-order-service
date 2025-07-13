package com.maestro.commande.repository;

import com.maestro.commande.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    // Trouver tous les articles d'une commande
    List<OrderItem> findByOrderId(String orderId);
}
