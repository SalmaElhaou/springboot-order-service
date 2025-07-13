package com.maestro.commande.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.maestro.commande.client.dto.UserDTO;

@RestController
@RequestMapping("/mock/users")
public class MockUserController {

    @GetMapping("/profile/{id}")
    public UserDTO getUser(@PathVariable String id) {
        // Retourne un utilisateur factice pour le test
        return new UserDTO(id, "salma@example.com", "Salma");
    }

    // Endpoint mocké pour valider un utilisateur (exemple)
    @PostMapping("/validate")
    public boolean validateUser(@RequestBody Map<String, String> body) {
        // Juste un mock qui retourne toujours true
        return true;
    }

    // DTO simple pour représenter l'utilisateur
    public static class UserDTO {
        private String id;
        private String email;
        private String name;

        public UserDTO(String id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }

        public String getId() { return id; }
        public String getEmail() { return email; }
        public String getName() { return name; }

        public void setId(String id) { this.id = id; }
        public void setEmail(String email) { this.email = email; }
        public void setName(String name) { this.name = name; }
    }
}


