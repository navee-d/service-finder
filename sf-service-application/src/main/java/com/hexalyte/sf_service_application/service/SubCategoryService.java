package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.model.SubCategorySolution;

import java.util.List;
import java.util.Optional;

public interface SubCategoryService {
    List<SubCategory> getSubCategories();

    Optional<SubCategory> getSubCategoryById(Integer id);

    List<SubCategorySolution> getSubCategoryServices(Integer id);

    Optional<SubCategory> addSubCategory(SubCategory subCategory);

    // FIX: Ensure Integer id is the first argument
    Optional<SubCategory> updateSubCategory(Integer id, SubCategory subCategory);

    void deleteSubCategory(Integer id);
}