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

    @GetMapping("/users")
    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
        return ResponseEntity.ofNullable(service.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRepresentation> getUserById(@PathVariable String id) {
        return ResponseEntity.of(service.getUserById(id));
    }

    @GetMapping("/users/groups/{id}")
    public ResponseEntity<List<UserRepresentation>> getUsersInGroup(@PathVariable String id) {
        return ResponseEntity.of(service.getUsersInGroup(id));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserRepresentation>> getUsersBySearch(@RequestParam("q") String query,
                                                                     @RequestParam("pageNum") int pageNumber,
                                                                     @RequestParam("resultsPerPage") int resultsPerPage) {
        return ResponseEntity.ofNullable(service.getUsersBySearch(query, pageNumber, resultsPerPage));
    }

    @GetMapping("/realm-roles")
    public ResponseEntity<List<RoleRepresentation>> getRealmRoles() {
        return ResponseEntity.ofNullable(service.getRealmRoles());
    }

    @GetMapping("/client-roles")
    public ResponseEntity<List<RoleRepresentation>> getClientRoles() {
        return ResponseEntity.ofNullable(service.getClientRoles());
    }

    @PostMapping("users/{id}/realm-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Assigned realm role to the user successfully")
    public void assignUserRealmRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.assignUserRealmRole(id, roles);
    }

    @PostMapping("users/{id}/client-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Assigned client role to the user successfully")
    public void assignUserClientRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.assignUserClientRole(id,roles);
    }

}
