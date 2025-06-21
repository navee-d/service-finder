package com.hexalyte.sf_user_management_application.service;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    List<UserRepresentation> getAllUsers();
    Optional<UserRepresentation> getUserById(String id);
    Optional<List<UserRepresentation>> getUsersInGroup(String id);
    List<UserRepresentation> getUsersBySearch(String query, int pageNumber, int resultsPerPage);
}
