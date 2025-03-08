package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.SolutionCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();
    Optional<Category> getCategoryById(Long id);
    Optional<List<SolutionCategory>> getCategoryServices(Long id);
    Optional<Category> addCategory(Category category);
    Optional<Category> updateCategory(Long id,Category category);
    void deleteCategory(Long id);

}
