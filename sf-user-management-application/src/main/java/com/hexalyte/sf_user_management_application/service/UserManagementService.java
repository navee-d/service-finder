package com.hexalyte.sf_user_management_application.service;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    List<UserRepresentation> getAllUsers();
    Optional<UserRepresentation> getUserById(String id);
    Optional<List<UserRepresentation>> getUsersInGroup(String id);
    List<RoleRepresentation> getRealmRoles();
    List<RoleRepresentation> getClientRoles();
    void assignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd);
    void assignUserClientRole(String id, List<RoleRepresentation> rolesToAdd);
    List<UserRepresentation> getUsersBySearch(String query, int pageNumber, int resultsPerPage);
}
