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
                                                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
                                                                     @RequestParam(value = "resultsPerPage", defaultValue = "5") int resultsPerPage) {
        return ResponseEntity.ofNullable(service.getUsersBySearch(query, pageNumber, resultsPerPage));
    }

    @PostMapping("users/{id}/realm-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Assigned realm role to the user successfully")
    public void assignUserRealmRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.assignUserRealmRole(id, roles);
    }

    @PostMapping("users/{id}/client-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Assigned client role to the user successfully")
    public void assignUserClientRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.assignUserClientRole(id, roles);
    }

    @DeleteMapping("users/{id}/realm-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Unassigned realm role from the user successfully")
    public void unassignUserRealmRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.unassignUserRealmRole(id, roles);
    }

    @DeleteMapping("users/{id}/client-roles")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Unassigned client role from the user successfully")
    public void unassignUserClientRole(@PathVariable String id, @RequestBody List<RoleRepresentation> roles) {
        service.unassignUserClientRole(id, roles);
    }

    @PutMapping("users/{userId}/groups/{groupId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Assigned user group successfully")
    public void assignUserGroup(@PathVariable String userId, @PathVariable String groupId) {
        service.assignUserGroup(userId, groupId);
    }

}
