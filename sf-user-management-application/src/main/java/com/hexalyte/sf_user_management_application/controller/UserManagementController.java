package com.hexalyte.sf_user_management_application.controller;

import com.hexalyte.sf_user_management_application.service.UserManagementService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realm")
public class UserManagementController {

    private final UserManagementService service;

    public UserManagementController(UserManagementService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserRepresentation userRepresentation) {
        try {
            service.createUser(userRepresentation);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
        return ResponseEntity.ofNullable(service.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRepresentation> getUserById(@PathVariable String id) {
        return ResponseEntity.of(service.getUserById(id));
    }
}
