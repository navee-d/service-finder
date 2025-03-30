package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.model.SolutionCategory;

import java.util.List;
import java.util.Optional;

public interface SolutionService {

    List<Solution> getServices();

    Optional<Solution> getServiceById(Long id);

    Optional<List<SolutionCategory>> getServiceCategories(Long id);

    Optional<Solution> addService(Solution solution);

    Optional<Solution> updateService(Long id, Solution solution);

    void deleteService(Long id);

}
