package com.maestro.commande.client.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
	private String id;
    private String email;
    private String name;
    
    
    public UserDTO() {
  	  
    }
      
      
      public UserDTO(String id) {
      	this.id=id;
      }
    
  

	public UserDTO(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}
