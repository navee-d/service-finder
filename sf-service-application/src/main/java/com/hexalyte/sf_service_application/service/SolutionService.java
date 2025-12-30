package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.model.SubCategorySolution;

import java.util.List;
import java.util.Optional;

public interface SolutionService {
    List<Solution> getServices();

    Optional<Solution> getServiceById(Integer id);

    List<CategorySolution> getServiceCategories(Integer id);

    List<SubCategorySolution> getServiceSubCategories(Integer id);

    Optional<Solution> addService(Solution solution);

    Optional<Solution> updateService(Integer id, Solution solution);

    void deleteService(Integer id);
}