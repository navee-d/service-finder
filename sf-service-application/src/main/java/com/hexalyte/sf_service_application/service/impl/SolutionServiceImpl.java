package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.*;
import com.hexalyte.sf_service_application.repository.CategorySolutionRepository;
import com.hexalyte.sf_service_application.repository.SolutionRepository;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.repository.SubCategorySolutionRepository;
import com.hexalyte.sf_service_application.service.SolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategorySolutionRepository categorySolutionRepository;
    private final SubCategorySolutionRepository subCategorySolutionRepository;

    public SolutionServiceImpl(SolutionRepository solutionRepository,
                               SubCategoryRepository subCategoryRepository,
                               CategorySolutionRepository categorySolutionRepository,
                               SubCategorySolutionRepository subCategorySolutionRepository) {
        this.solutionRepository = solutionRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySolutionRepository = categorySolutionRepository;
        this.subCategorySolutionRepository = subCategorySolutionRepository;
    }

    @Override
    public List<Solution> getServices() {
        List<Solution> services = solutionRepository.findAll();
        if (services.isEmpty()) return null;
        return services;
    }

    @Override
    public Optional<Solution> getServiceById(Integer id) {
        Optional<Solution> service = solutionRepository.findById(id);
        if (service.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        return service;
    }

    @Override
    public List<CategorySolution> getServiceCategories(Integer id) {
        // FIX: Use findById_SolutionId
        List<CategorySolution> categorySolutions = categorySolutionRepository.findById_SolutionId(id);
        if (categorySolutions.isEmpty()) return null;
        return categorySolutions;
    }

    @Override
    public List<SubCategorySolution> getServiceSubCategories(Integer id) {
        // FIX: Use findById_SolutionId
        List<SubCategorySolution> subCategorySolutions = subCategorySolutionRepository.findById_SolutionId(id);
        if (subCategorySolutions.isEmpty()) return null;
        return subCategorySolutions;
    }

    @Override
    public Optional<Solution> addService(Solution solution) {
        if (!subCategoryRepository.existsBySubCategoryIdAndCategory(solution.getSubCategory().getSubCategoryId(),
                solution.getCategory()))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No such subcategory found under " + solution.getCategory().getName() + " category."
            );

        // Save solution first to get ID
        Solution savedSolution = solutionRepository.save(solution);

        CategorySolution categorySolution = CategorySolution.builder()
                .id(CategorySolutionKey.builder()
                        .categoryId(savedSolution.getCategory().getCategoryId())
                        .solutionId(savedSolution.getSolutionId())
                        .build())
                .solution(savedSolution)
                .category(savedSolution.getCategory())
                .build();

        SubCategorySolution subCategorySolution = SubCategorySolution.builder()
                .id(SubCategorySolutionKey.builder()
                        .subCategoryId(savedSolution.getSubCategory().getSubCategoryId())
                        .solutionId(savedSolution.getSolutionId())
                        .build())
                .solution(savedSolution)
                .subCategory(savedSolution.getSubCategory())
                .build();

        categorySolutionRepository.save(categorySolution);
        subCategorySolutionRepository.save(subCategorySolution);

        return Optional.of(savedSolution);
    }

    @Override
    public Optional<Solution> updateService(Integer id, Solution solution) {
        if (solution.getDescription().isBlank())
            solution.setDescription(null);

        Solution updatingSolution = solutionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
        );

        if (!subCategoryRepository.existsBySubCategoryIdAndCategory(solution.getSubCategory().getSubCategoryId(),
                solution.getCategory()))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No such subcategory found under " + solution.getCategory().getName() + " category."
            );

        // FIX: Unchained setters to avoid 'void cannot be dereferenced' error
        updatingSolution.setServiceProviderId(solution.getServiceProviderId());
        updatingSolution.setName(solution.getName());
        updatingSolution.setCategory(solution.getCategory());
        updatingSolution.setSubCategory(solution.getSubCategory());
        updatingSolution.setDescription(solution.getDescription());
        updatingSolution.setPrice(solution.getPrice());
        updatingSolution.setEstimatedTime(solution.getEstimatedTime());
        updatingSolution.setReminderTime(solution.getReminderTime());
        updatingSolution.setIsAvailable(solution.getIsAvailable());
        updatingSolution.setIsActive(solution.getIsActive());

        // FIX: Use findById_SolutionId
        categorySolutionRepository.deleteAll(categorySolutionRepository.findById_SolutionId(id));
        subCategorySolutionRepository.deleteAll(subCategorySolutionRepository.findById_SolutionId(id));

        solutionRepository.flush();

        CategorySolution categorySolution = CategorySolution.builder()
                .id(CategorySolutionKey.builder()
                        .categoryId(updatingSolution.getCategory().getCategoryId())
                        .solutionId(updatingSolution.getSolutionId())
                        .build())
                .solution(updatingSolution)
                .category(updatingSolution.getCategory())
                .build();

        SubCategorySolution subCategorySolution = SubCategorySolution.builder()
                .id(SubCategorySolutionKey.builder()
                        .subCategoryId(updatingSolution.getSubCategory().getSubCategoryId())
                        .solutionId(updatingSolution.getSolutionId())
                        .build())
                .solution(updatingSolution)
                .subCategory(updatingSolution.getSubCategory())
                .build();

        categorySolutionRepository.save(categorySolution);
        subCategorySolutionRepository.save(subCategorySolution);

        return Optional.of(updatingSolution);
    }

    @Override
    public void deleteService(Integer id) {
        Solution solution = solutionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
        );
        solutionRepository.delete(solution);
    }
}