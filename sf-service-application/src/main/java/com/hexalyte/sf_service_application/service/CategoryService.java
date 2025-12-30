package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.CategorySolution;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryById(Integer id);

    List<CategorySolution> getCategoryServices(Integer id);

    Optional<Category> addCategory(Category category);

    Optional<Category> updateCategory(Integer id, Category category);

    void deleteCategory(Integer id);

}
