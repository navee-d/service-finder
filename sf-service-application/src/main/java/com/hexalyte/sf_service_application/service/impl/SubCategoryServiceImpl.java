package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.repository.SubCategorySolutionRepository;
import com.hexalyte.sf_service_application.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🔧 1. IMPORT THIS
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategorySolutionRepository subCategorySolutionRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategorySolutionRepository subCategorySolutionRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategorySolutionRepository = subCategorySolutionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategory> getSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();


        subCategories.forEach(sub -> {
            if (sub.getCategory() != null) {
                sub.getCategory().getName();
            }
        });

        return subCategories;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubCategory> getSubCategoryById(Integer id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategorySolution> getSubCategoryServices(Integer id) {
        List<SubCategorySolution> subCategorySolutions = subCategorySolutionRepository.findBySubCategory_SubCategoryId(id);
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
    public Optional<SubCategory> updateSubCategory(SubCategory subCategory, Integer id) {

        if (subCategory.getDescription() != null && subCategory.getDescription().isBlank())
            subCategory.setDescription(null);

        SubCategory updatingSubCategory = subCategoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find subcategory with such ID")
        );

        updatingSubCategory.setCategory(subCategory.getCategory());
        updatingSubCategory.setName(subCategory.getName());
        updatingSubCategory.setDescription(subCategory.getDescription());

        return Optional.of(subCategoryRepository.save(updatingSubCategory));
    }

    @Override
    public void deleteSubCategory(Integer id) {
        subCategoryRepository.delete(
                subCategoryRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find subcategory with such ID")
                )
        );
    }
}