package com.hexalyte.sf_booking_application.service.feign;

import com.hexalyte.sf_booking_application.model.feign.ServiceProviderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sf-provider-application")
public interface ServiceProviderInterface {
    @GetMapping("/provider/{id}")
    ResponseEntity<ServiceProviderDTO> getProviderByID(@PathVariable Long id);
}