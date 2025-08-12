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
        if (services.isEmpty())
            return null;
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
        List<CategorySolution> categorySolutions = categorySolutionRepository.findBySolution_SolutionId(id);
        if (categorySolutions.isEmpty()) return null;
        return categorySolutions;
    }

    @Override
    public List<SubCategorySolution> getServiceSubCategories(Integer id) {
        List<SubCategorySolution> subCategorySolutions = subCategorySolutionRepository.findBySolution_SolutionId(id);
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

        solution.setCategorySolutions(List.of(
                CategorySolution.builder()
                        .id(
                                CategorySolutionKey.builder()
                                        .categoryId(solution.getCategory().getCategoryId())
                                        .build()
                        )
                        .solution(solution)
                        .category(solution.getCategory())
                        .build()
        ));

        solution.setSubCategorySolutions(List.of(
                SubCategorySolution.builder()
                        .id(
                                SubCategorySolutionKey.builder()
                                        .subCategoryId(solution.getSubCategory().getSubCategoryId())
                                        .build()
                        )
                        .solution(solution)
                        .subCategory(solution.getSubCategory())
                        .build()
        ));
        return Optional.of(solutionRepository.save(solution));
    }

    @Override
    @Transactional
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

        updatingSolution
                .setServiceProviderId(solution.getServiceProviderId())
                .setName(solution.getName())
                .setCategory(solution.getCategory())
                .setSubCategory(solution.getSubCategory())
                .setDescription(solution.getDescription())
                .setPrice(solution.getPrice())
                .setEstimatedTime(solution.getEstimatedTime())
                .setReminderTime(solution.getReminderTime())
                .setIsAvailable(solution.getIsAvailable())
                .setIsActive(solution.getIsActive());

        categorySolutionRepository.deleteAll(categorySolutionRepository.findBySolution_SolutionId(id));
        subCategorySolutionRepository.deleteAll(subCategorySolutionRepository.findBySolution_SolutionId(id));

        solutionRepository.flush();

        categorySolutionRepository.save(
                CategorySolution.builder()
                        .id(
                                CategorySolutionKey.builder()
                                        .categoryId(updatingSolution.getCategory().getCategoryId())
                                        .build()
                        )
                        .solution(updatingSolution)
                        .category(updatingSolution.getCategory())
                        .build()
        );

        subCategorySolutionRepository.save(
                SubCategorySolution.builder()
                        .id(
                                SubCategorySolutionKey.builder()
                                        .subCategoryId(updatingSolution.getSubCategory().getSubCategoryId())
                                        .build()
                        )
                        .solution(updatingSolution)
                        .subCategory(updatingSolution.getSubCategory())
                        .build()
        );

        return Optional.of(updatingSolution);
    }

    @Override
    public void deleteService(Integer id) {
        solutionRepository.delete(
                solutionRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID")
                )
        );
    }

}
