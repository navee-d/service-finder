package com.hexalyte.sf_user_management_application.service.impl;

import com.hexalyte.sf_user_management_application.service.UserManagementService;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.GroupResource;
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
        getUserById(id);

        List<RoleRepresentation> addingRolesList = new ArrayList<>();
        RoleScopeResource userRealmLevelRoles = realm.users().get(id).roles().realmLevel();

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userRealmRoles = userRealmLevelRoles.listAll();
                if (userRealmRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Realm role: " + role.getName() + " is already assigned to the user.");
                addingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Realm role named " + role.getName() + " cannot be found.");
            }
        }
        userRealmLevelRoles.add(addingRolesList);
    }

    @Override
    public void assignUserClientRole(String id, List<RoleRepresentation> rolesToAdd) {
        getUserById(id);

        List<RoleRepresentation> addingRolesList = new ArrayList<>();
        RoleScopeResource userClientLevelRoles = realm.users().get(id).roles().clientLevel(clientUuid);

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.clients().get(clientUuid).roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userClientRoles = userClientLevelRoles.listAll();
                if (userClientRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Client role: " + role.getName() + " is already assigned to the user.");
                addingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client role named " + role.getName() + " cannot be found.");
            }
        }
        userClientLevelRoles.add(addingRolesList);
    }

    @Override
    public void unassignUserRealmRole(String id, List<RoleRepresentation> rolesToAdd) {
        getUserById(id);

        List<RoleRepresentation> removingRolesList = new ArrayList<>();
        RoleScopeResource userRealmLevelRoles = realm.users().get(id).roles().realmLevel();

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userRealmRoles = userRealmLevelRoles.listAll();
                if (!userRealmRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.GONE, "Realm role: " + role.getName() + " is already unassigned from the user.");
                removingRolesList.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Realm role named " + role.getName() + " cannot be found.");
            }
        }
        userRealmLevelRoles.remove(removingRolesList);
    }

    @Override
    public void unassignUserClientRole(String id, List<RoleRepresentation> rolesToAdd) {
        getUserById(id);

        List<RoleRepresentation> removingRolesList = new ArrayList<>();
        RoleScopeResource userClientLevelRoles = realm.users().get(id).roles().clientLevel(clientUuid);

        for (RoleRepresentation role : rolesToAdd) {
            try {
                RoleRepresentation addingRole = realm.clients().get(clientUuid).roles().get(role.getName()).toRepresentation();
                List<RoleRepresentation> userClientRoles = userClientLevelRoles.listAll();
                if (!userClientRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.GONE, "Client role: " + role.getName() + " is already unassigned from the user.");
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
    public void unassignUserGroup(String userId, String groupId) {
        try {
            realm.groups().group(groupId).toRepresentation();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find group with such ID");
        }

        try {
            boolean groupAvailable = false;
            UserResource user = realm.users().get(userId);
            for (GroupRepresentation userGroup : user.groups()) {
                if (userGroup.getId().equals(groupId)) {
                    groupAvailable = true;
                    break;
                }
            }
            if (!groupAvailable)
                throw new ResponseStatusException(HttpStatus.GONE, "This group is already unassigned from the user " +
                        "or has not assigned to the user");
            user.leaveGroup(groupId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user is found with such ID");
        }
    }

    @Override
    public void assignRealmRolesToGroup(String groupId, List<RoleRepresentation> rolesToAdd) {
        List<RoleRepresentation> addingRoles = new ArrayList<>();
        GroupResource group = realm.groups().group(groupId);

        try {
            group.toRepresentation();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find group with such ID");
        }

        for (RoleRepresentation role : rolesToAdd) {
            try {
                List<RoleRepresentation> groupRealmRoles = group.roles().realmLevel().listAll();
                RoleRepresentation addingRole = realm.roles().get(role.getName()).toRepresentation();
                if (groupRealmRoles.contains(addingRole))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, role.getName() + " realm role is already assigned to this group");
                addingRoles.add(addingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find realm role named " + role.getName());
            }
        }

        group.roles().realmLevel().add(addingRoles);
    }

    @Override
    public void unassignRealmRolesToGroup(String groupId, List<RoleRepresentation> rolesToRemove) {
        List<RoleRepresentation> removingRoles = new ArrayList<>();
        GroupResource group = realm.groups().group(groupId);

        try {
            group.toRepresentation();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find group with such ID");
        }

        for (RoleRepresentation role : rolesToRemove) {
            try {
                List<RoleRepresentation> groupRealmRoles = group.roles().realmLevel().listAll();
                RoleRepresentation removingRole = realm.roles().get(role.getName()).toRepresentation();
                if (!groupRealmRoles.contains(removingRole))
                    throw new ResponseStatusException(HttpStatus.GONE, role.getName() + " realm role is " +
                            "already unassigned or has not assigned to this group");
                removingRoles.add(removingRole);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find realm role named " + role.getName());
            }
        }

        group.roles().realmLevel().remove(removingRoles);
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
