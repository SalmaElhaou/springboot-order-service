package com.maestro.commande.model;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String country;
    
    public Address() {}

	public Address( String street, String city, String postalCode, String country) {
		super();
		
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	
	

	
}
