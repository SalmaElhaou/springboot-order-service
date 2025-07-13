package com.maestro.commande.repository;
import com.maestro.commande.model.Order;
import com.maestro.commande.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
public interface OrderRepository extends JpaRepository<Order, String> {
    // Trouver toutes les commandes d'un utilisateur
    List<Order> findByUserId(String userId);
    
    
    List<Order> findByCreatedAt(LocalDateTime createdAt);
    List<Order> findByUserIdAndCreatedAt(String userId, LocalDateTime createdAt);


    // Trouver les commandes par statut
    List<Order> findByStatus(OrderStatus status);

    // Trouver les commandes d'un utilisateur avec un statut spécifique
    List<Order> findByUserIdAndStatus(String userId, OrderStatus status);
}
