package com.maestro.commande.repository;

import com.maestro.commande.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface AddressRepository extends JpaRepository<Address, UUID> {
    // Pas de méthodes personnalisées pour l'instant
}
