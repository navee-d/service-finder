package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.Service;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.repository.ServiceRepository;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository,
                              SubCategoryRepository subCategoryRepository) {
        this.serviceRepository = serviceRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getServices() {
        List<Service> services = serviceRepository.findAll();
        // Force lazy loading
        services.forEach(service -> {
            if (service.getCategory() != null) {
                service.getCategory().getName();
            }
            if (service.getSubCategory() != null) {
                service.getSubCategory().getName();
            }
        });
        return services.isEmpty() ? null : services;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceById(Integer id) {
        Optional<Service> service = serviceRepository.findById(id);
        if (service.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        }
        return service;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorySolution> getServiceCategories(Integer id) {
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategorySolution> getServiceSubCategories(Integer id) {
        return Collections.emptyList();
    }

    @Override
    public Optional<Service> addService(Service service) {
        validateCategoryAndSubCategory(service);
        Service savedService = serviceRepository.save(service);
        return Optional.of(savedService);
    }

    @Override
    public Optional<Service> updateService(Integer id, Service service) {
        if (service.getDescription() != null && service.getDescription().isBlank()) {
            service.setDescription(null);
        }

        Service updatingService = serviceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
        );

        validateCategoryAndSubCategory(service);

        // Update fields matching the Entity
        updatingService.setName(service.getName());
        updatingService.setCategory(service.getCategory());
        updatingService.setSubCategory(service.getSubCategory());
        updatingService.setDescription(service.getDescription());
        updatingService.setIsActive(service.getIsActive());

        serviceRepository.save(updatingService);

        return Optional.of(updatingService);
    }

    @Override
    public void deleteService(Integer id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
        );
        serviceRepository.delete(service);
    }

    private void validateCategoryAndSubCategory(Service service) {
        if (service.getSubCategory() != null && service.getCategory() != null) {
            boolean exists = subCategoryRepository.existsBySubCategoryIdAndCategory(
                    service.getSubCategory().getSubCategoryId(),
                    service.getCategory()
            );

            if (!exists) {
                String catName = (service.getCategory().getName() != null) ? service.getCategory().getName() : "Unknown";
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No such subcategory found under " + catName + " category."
                );
            }
        }
    }
}