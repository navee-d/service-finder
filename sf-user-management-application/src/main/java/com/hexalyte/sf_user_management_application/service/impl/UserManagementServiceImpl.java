package com.hexalyte.sf_user_management_application.service.impl;

import com.hexalyte.sf_user_management_application.service.UserManagementService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private RealmResource realm;

    @Value("${keycloak.serverUrl}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realmName;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Value("${keycloak.clientUuid}")
    private String clientUuid;

    @PostConstruct
    private void init() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(serverUrl)
                .realm(realmName)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(OAuth2Constants.SCOPE_OPENID)
                .build();
        this.realm = keycloak.realm(realmName);
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        List<UserRepresentation> userList = realm.users().list();
        if (userList.isEmpty())
            return null;
        return userList;
    }

    @Override
    public Optional<UserRepresentation> getUserById(String id) {
        UserRepresentation user;
        try {
            user = realm.users().get(id).toRepresentation();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user is found with such ID");
        }
        return Optional.of(user);
    }

    @Override
    public Optional<List<UserRepresentation>> getUsersInGroup(String id) {
        List<UserRepresentation> groupMembers;
        try {
            groupMembers = realm.groups().group(id).members();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No group is found with such ID");
        }
        return Optional.of(groupMembers);
    }

    @Override
    public void assignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd) {
        List<RoleRepresentation> addingRolesList = new ArrayList<>();
        RoleScopeResource userRealmLevelRoles = realm.users().get(id).roles().realmLevel();

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userRealmRoles = userRealmLevelRoles.listAll();
                if (userRealmRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Realm role: " + role.getName() + " is already assigned to the user.");
                addingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Realm role named " + role.getName() + " cannot be found.");
            }
        }
        userRealmLevelRoles.add(addingRolesList);
    }

    @Override
    public void assignUserClientRole(String id, List<RoleRepresentation> rolesToAdd) {
        List<RoleRepresentation> addingRolesList = new ArrayList<>();
        RoleScopeResource userClientLevelRoles = realm.users().get(id).roles().clientLevel(clientUuid);

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.clients().get(clientUuid).roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userClientRoles = userClientLevelRoles.listAll();
                if (userClientRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client role: " + role.getName() + " is already assigned to the user.");
                addingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client role named " + role.getName() + " cannot be found.");
            }
        }
        userClientLevelRoles.add(addingRolesList);
    }

    @Override
    public void unassignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd) {
        List<RoleRepresentation> removingRolesList = new ArrayList<>();
        RoleScopeResource userRealmLevelRoles = realm.users().get(id).roles().realmLevel();

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userRealmRoles = userRealmLevelRoles.listAll();
                if (!userRealmRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Realm role: " + role.getName() + " is already unassigned from the user.");
                removingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Realm role named " + role.getName() + " cannot be found.");
            }
        }
        userRealmLevelRoles.remove(removingRolesList);
    }

    @Override
    public void unassignUserClientRole(String id, List<RoleRepresentation> rolesToAdd) {
        List<RoleRepresentation> removingRolesList = new ArrayList<>();
        RoleScopeResource userClientLevelRoles = realm.users().get(id).roles().clientLevel(clientUuid);

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.clients().get(clientUuid).roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userClientRoles = userClientLevelRoles.listAll();
                if (!userClientRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client role: " + role.getName() + " is already unassigned from the user.");
                removingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client role named " + role.getName() + " cannot be found.");
            }
        }
        userClientLevelRoles.remove(removingRolesList);
    }

    @Override
    public void assignUserGroup(String userId, String groupId) {
        try {
            realm.groups().group(groupId).toRepresentation();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find group with such ID");
        }

        try {
            UserResource user = realm.users().get(userId);
            for (GroupRepresentation userGroup : user.groups()) {
                if (userGroup.getId().equals(groupId))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "This group is already assigned to the user");
            }
            user.joinGroup(groupId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user is found with such ID");
        }
    }

    @Override
    public List<UserRepresentation> getUsersBySearch(String query, int pageNumber, int resultsPerPage) {
        PageRequest pageRequest;
        try {
            pageRequest = PageRequest.of(pageNumber, resultsPerPage);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        List<UserRepresentation> userList = realm.users().searchByAttributes(
                (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize(),
                pageRequest.getPageSize(), true, false, query);
        if (userList.isEmpty())
            return null;
        return userList;
    }


}
