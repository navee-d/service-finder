package com.hexalyte.sf_user_management_application.service.impl;

import com.hexalyte.sf_user_management_application.service.UserManagementService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return realm.users().list();
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
    public List<RoleRepresentation> getRealmRoles() {
        return realm.roles().list();
    }

    @Override
    public void createRealmRole(RoleRepresentation role) {
        try {
            realm.roles().create(role);
        } catch (ClientErrorException e) {
            if (e.getMessage().equals("HTTP 409 Conflict"))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "A realm role name " + role.getName() + " already exists.");
            else
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void createClientRole(RoleRepresentation role) {
        try {
            realm.clients().get(clientUuid).roles().create(role);
        }  catch (ClientErrorException e) {
            if (e.getMessage().equals("HTTP 409 Conflict"))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "A client role name " + role.getName() + " already exists.");
            else
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<UserRepresentation> getUsersBySearch(String query, int pageNumber, int resultsPerPage) {
        List<UserRepresentation> userList = realm.users().searchByAttributes((pageNumber - 1) * resultsPerPage,
                resultsPerPage, true, false, query);
        if (userList.isEmpty())
            return null;
        return userList;
    }


}
