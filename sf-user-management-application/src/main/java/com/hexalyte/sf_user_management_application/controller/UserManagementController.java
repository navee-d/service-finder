package com.hexalyte.sf_user_management_application.controller;

import com.hexalyte.sf_user_management_application.service.UserManagementService;
import org.keycloak.representations.idm.UserRepresentation;
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
                                                                     @RequestParam(value = "pageNum",defaultValue = "1") int pageNumber,
                                                                     @RequestParam(value = "resultsPerPage",defaultValue = "5") int resultsPerPage) {
        return ResponseEntity.ofNullable(service.getUsersBySearch(query, pageNumber, resultsPerPage));
    }

}
