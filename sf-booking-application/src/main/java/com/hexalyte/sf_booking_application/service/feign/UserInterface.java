package com.hexalyte.sf_booking_application.service.feign;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "user-interface",url = "http://localhost:8080/realm/users")
public interface UserInterface {
    @GetMapping("{id}")
    ResponseEntity<UserRepresentation> getUserById(@PathVariable UUID id);
}
