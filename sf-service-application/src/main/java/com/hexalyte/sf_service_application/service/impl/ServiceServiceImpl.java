package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.*;
import com.hexalyte.sf_service_application.repository.CategorySolutionRepository;
import com.hexalyte.sf_service_application.repository.ServiceRepository;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.repository.SubCategorySolutionRepository;
import com.hexalyte.sf_service_application.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategorySolutionRepository categorySolutionRepository;
    private final SubCategorySolutionRepository subCategorySolutionRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository,
                              SubCategoryRepository subCategoryRepository,
                              CategorySolutionRepository categorySolutionRepository,
                              SubCategorySolutionRepository subCategorySolutionRepository) {
        this.serviceRepository = serviceRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySolutionRepository = categorySolutionRepository;
        this.subCategorySolutionRepository = subCategorySolutionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Service> getServices() {
        List<Service> services = serviceRepository.findAll();

        // 🔧 HERE IS THE FIX:
        // Force the lazy-loaded category and subcategory to load
        services.forEach(service -> {
            if (service.getCategory() != null) {
                service.getCategory().getName(); // "touch" the category
            }
            if (service.getSubCategory() != null) {
                service.getSubCategory().getName(); // "touch" the subcategory
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
        List<CategorySolution> categorySolutions = categorySolutionRepository.findByService_SolutionId(id);
        return categorySolutions.isEmpty() ? null : categorySolutions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategorySolution> getServiceSubCategories(Integer id) {
        List<SubCategorySolution> subCategorySolutions = subCategorySolutionRepository.findByService_SolutionId(id);
        return subCategorySolutions.isEmpty() ? null : subCategorySolutions;
    }

    @Override
    public Optional<Service> addService(Service service) {
        // Validation algorithm: Check subcategory exists under given category
        if (service.getSubCategory() != null && service.getCategory() != null) {
            boolean exists = subCategoryRepository.existsBySubCategoryIdAndCategory_CategoryId(
                    service.getSubCategory().getSubCategoryId(),
                    service.getCategory().getCategoryId()
            );

            if (!exists) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No such subcategory found under " + service.getCategory().getName() + " category."
                );
            }
        }

        Service savedService = serviceRepository.save(service);
        createRelationshipEntries(savedService);
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

        // Validation algorithm
        if (service.getSubCategory() != null && service.getCategory() != null) {
            boolean exists = subCategoryRepository.existsBySubCategoryIdAndCategory_CategoryId(
                    service.getSubCategory().getSubCategoryId(),
                    service.getCategory().getCategoryId()
            );

            if (!exists) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No such subcategory found under " + service.getCategory().getName() + " category."
                );
            }
        }

        updatingService.setServiceProviderId(service.getServiceProviderId());
        updatingService.setCategory(service.getCategory());
        updatingService.setSubCategory(service.getSubCategory());
        updatingService.setDescription(service.getDescription());
        updatingService.setPrice(service.getPrice());
        updatingService.setEstimatedTime(service.getEstimatedTime());
        updatingService.setReminderTime(service.getReminderTime());
        updatingService.setIsAvailable(service.getIsAvailable());
        updatingService.setIsActive(service.getIsActive());

        // Delete existing relationships
        categorySolutionRepository.deleteAll(
                categorySolutionRepository.findByService_SolutionId(id)
        );
        subCategorySolutionRepository.deleteAll(
                subCategorySolutionRepository.findByService_SolutionId(id)
        );

        serviceRepository.flush();

        // Create new relationships
        createRelationshipEntries(updatingService);

        return Optional.of(updatingService);
    }

    @Override
    public void deleteService(Integer id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
        );

        // Relationships will be deleted automatically due to cascade settings
        serviceRepository.delete(service);
    }


    private void createRelationshipEntries(Service service) {
        // Category Relationship
        if (service.getCategory() != null) {
            CategorySolution categorySolution = CategorySolution.builder()
                    .id(CategorySolutionKey.builder()
                            .categoryId(service.getCategory().getCategoryId())
                            .solutionId(service.getSolutionId())
                            .build())
                    .category(service.getCategory())
                    .service(service)
                    .build();

            categorySolutionRepository.save(categorySolution);
        }

        // SubCategory Relationship
        if (service.getSubCategory() != null) {
            SubCategorySolution subCategorySolution = SubCategorySolution.builder()
                    .id(SubCategorySolutionKey.builder()
                            .subCategoryId(service.getSubCategory().getSubCategoryId())
                            .solutionId(service.getSolutionId())
                            .build())
                    .subCategory(service.getSubCategory())
                    .service(service)
                    .build();

            subCategorySolutionRepository.save(subCategorySolution);
        }
    }
}