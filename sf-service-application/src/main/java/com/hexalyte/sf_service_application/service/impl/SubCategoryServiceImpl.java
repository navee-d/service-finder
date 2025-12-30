package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.repository.SubCategorySolutionRepository;
import com.hexalyte.sf_service_application.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategorySolutionRepository subCategorySolutionRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository,
                                  SubCategorySolutionRepository subCategorySolutionRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategorySolutionRepository = subCategorySolutionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategory> getSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategory> getSubCategoryById(Integer id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategorySolution> getSubCategoryServices(Integer id) {
        // FIX: Use findById_SubCategoryId
        List<SubCategorySolution> subCategorySolutions = subCategorySolutionRepository.findById_SubCategoryId(id);
        if (subCategorySolutions.isEmpty()) return null;
        return subCategorySolutions;
    }

    @Override
    public Optional<SubCategory> addSubCategory(SubCategory subCategory) {
        if (subCategory.getDescription() != null && subCategory.getDescription().isBlank())
            subCategory.setDescription(null);
        return Optional.of(subCategoryRepository.save(subCategory));
    }

    @Override
    public Optional<SubCategory> updateSubCategory(Integer id, SubCategory subCategory) {
        if (subCategory.getDescription() != null && subCategory.getDescription().isBlank())
            subCategory.setDescription(null);

        SubCategory updatingSubCategory = subCategoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find subcategory with such ID")
        );

        updatingSubCategory.setName(subCategory.getName());
        updatingSubCategory.setDescription(subCategory.getDescription());
        updatingSubCategory.setCategory(subCategory.getCategory());

        return Optional.of(subCategoryRepository.save(updatingSubCategory));
    }

    @Override
    public void deleteSubCategory(Integer id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find subcategory with such ID")
        );
        subCategoryRepository.delete(subCategory);
    }
}