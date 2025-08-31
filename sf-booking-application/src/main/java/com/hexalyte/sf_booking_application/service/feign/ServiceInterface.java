package com.hexalyte.sf_booking_application.service.feign;

import com.hexalyte.sf_booking_application.model.feign.SolutionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-interface",url = "http://localhost:8081/services")
public interface ServiceInterface {
    @GetMapping("/{id}")
    ResponseEntity<SolutionDTO> getServiceById(@PathVariable int id);
}
