package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Solution;
import com.hexalyte.sf_service_application.model.SolutionCategory;
import com.hexalyte.sf_service_application.model.SolutionCategoryKey;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import com.hexalyte.sf_service_application.repository.SolutionCategoryRepository;
import com.hexalyte.sf_service_application.repository.SolutionRepository;
import com.hexalyte.sf_service_application.service.SolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final SolutionCategoryRepository solutionCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SolutionServiceImpl(SolutionRepository solutionRepository, SolutionCategoryRepository solutionCategoryRepository, CategoryRepository categoryRepository) {
        this.solutionRepository = solutionRepository;
        this.solutionCategoryRepository = solutionCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Solution> getServices() {
        return solutionRepository.findAll();
    }


    @Override
    public Optional<Solution> getServiceById(Long id) {
        Optional<Solution> service = solutionRepository.findById(id);
        if (service.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        return service;
    }

    @Override
    public Optional<List<SolutionCategory>> getServiceCategories(Long id) {
        return solutionCategoryRepository.findBySolution_SolutionId(id);
    }

    @Override
    @Transactional
    public Optional<Solution> addService(Solution solution) {

        if (solution.getCreatedAt().isAfter(solution.getUpdatedAt()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created At date must be before the Updated At date");

        Solution savedSolution = solutionRepository.save(solution);
        List<SolutionCategory> solutionCategories = new ArrayList<>();
        solution.getCategoryIds().forEach(
                categoryId -> {

                    if (!categoryRepository.existsById(categoryId))
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");

                    SolutionCategory solutionCategory = SolutionCategory.
                            builder().
                            id(
                                    SolutionCategoryKey.
                                            builder().
                                            solutionId(solution.getSolutionId()).
                                            categoryId(categoryId).
                                            build()
                            ).solution(solution).
                            category(categoryRepository.getReferenceById(categoryId)).
                            build();

                    solutionCategories.add(solutionCategory);
                }
        );

        solutionCategoryRepository.saveAll(solutionCategories);
        return Optional.of(savedSolution);
    }

    @Override
    @Transactional
    public Optional<Solution> updateService(Long id, Solution solution) {

        if (!solutionRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        solution.setSolutionId(id);

        if (solution.getCreatedAt().isAfter(solution.getUpdatedAt()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created At date must be before the Updated At date");

        Solution savedSolution = solutionRepository.save(solution);
        solutionCategoryRepository.deleteAllBySolution_SolutionId(savedSolution.getSolutionId());

        List<SolutionCategory> solutionCategories = new ArrayList<>();
        solution.getCategoryIds().forEach(
                categoryId -> {

                    if (!categoryRepository.existsById(categoryId))
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");

                    SolutionCategory solutionCategory = SolutionCategory.
                            builder().
                            id(
                                    SolutionCategoryKey.
                                            builder().
                                            solutionId(solution.getSolutionId()).
                                            categoryId(categoryId).
                                            build()
                            ).solution(solution).
                            category(categoryRepository.findById(categoryId).get()).
                            build();

                    solutionCategories.add(solutionCategory);
                }
        );

        solutionCategoryRepository.saveAll(solutionCategories);
        return Optional.of(savedSolution);
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        solutionCategoryRepository.deleteAllBySolution_SolutionId(id);
        solutionRepository.deleteById(id);
    }

}
