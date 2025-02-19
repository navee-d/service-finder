package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.Solution;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SolutionService {

    List<Solution> getServices();
    Optional<Solution> getServiceById(Long id);
    Optional<Solution> addService(Solution solution);
    Optional<Solution> updateService(Solution solution);
    void deleteService(Long id);

}
