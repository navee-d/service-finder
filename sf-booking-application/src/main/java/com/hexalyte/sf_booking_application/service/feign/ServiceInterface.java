package com.hexalyte.sf_booking_application.service.feign;

import com.hexalyte.sf_booking_application.model.feign.SolutionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sf-service-application")
public interface ServiceInterface {
    @GetMapping("/services/{id}")
    ResponseEntity<SolutionDTO> getServiceById(@PathVariable int id);
}