package com.hexalyte.sf_user_management_application.service;

import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface UserManagementService {
    List<UserRepresentation> getAllUsers();
    Optional<UserRepresentation> getUserById(String id);
    Optional<List<UserRepresentation>> getUsersInGroup(String id);
    void assignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd);
    void assignUserClientRole(String id, List<RoleRepresentation> rolesToAdd);
    void unassignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd);
    void unassignUserClientRole(String id, List<RoleRepresentation> rolesToAdd);
    void assignUserGroup(String userId, String groupId);
    void unassignUserGroup(String userId, String groupId);
    void assignRealmRolesToGroup(String groupId, List<RoleRepresentation> rolesToAdd);
    void unassignRealmRolesToGroup(String groupId, List<RoleRepresentation> rolesToAdd);
    List<UserRepresentation> getUsersBySearch(String query, int pageNumber, int resultsPerPage);
}
