package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.Service;
import com.hexalyte.sf_service_application.model.SubCategorySolution;

import java.util.List;
import java.util.Optional;

public interface ServiceService {

    List<Service> getServices();

    Optional<Service> getServiceById(Integer id);

    List<CategorySolution> getServiceCategories(Integer id);

    List<SubCategorySolution> getServiceSubCategories(Integer id);

    Optional<Service> addService(Service service);

    Optional<Service> updateService(Integer id, Service service);

    void deleteService(Integer id);

}
