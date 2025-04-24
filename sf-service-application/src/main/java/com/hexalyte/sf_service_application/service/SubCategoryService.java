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

    Optional<SubCategory> updateSubCategory(SubCategory subCategory, Integer id);

    void deleteSubCategory(Integer id);
}
