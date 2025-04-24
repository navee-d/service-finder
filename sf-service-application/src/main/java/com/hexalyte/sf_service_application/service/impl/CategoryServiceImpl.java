package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import com.hexalyte.sf_service_application.repository.CategorySolutionRepository;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import com.hexalyte.sf_service_application.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CategorySolutionRepository categorySolutionRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository,
                               CategorySolutionRepository categorySolutionRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySolutionRepository = categorySolutionRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAllByIsActive(true);
    }

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findByCategoryIdAndIsActive(id, true);
        if (category.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        return category;
    }

    @Override
    public List<CategorySolution> getCategoryServices(Integer id) {
        List<CategorySolution> categorySolutions = categorySolutionRepository.findByCategory_CategoryId(id);
        if (categorySolutions.isEmpty()) return null;
        return categorySolutions;
    }

    @Override
    public Optional<Category> addCategory(Category category) {

        if (category.getDescription().isBlank())
            category.setDescription(null);

        category.setIsActive(true);

        return Optional.of(categoryRepository.save(category));
    }

    @Override
    public Optional<Category> updateCategory(Integer id, Category category) {

        if (category.getDescription().isBlank())
            category.setDescription(null);

        Category updatingCategory = categoryRepository.findByCategoryIdAndIsActive(id, true).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with such ID cannot be found.")
        );

        if (categoryRepository.getReferenceById(id).getName().equals("None"))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a category");

        updatingCategory.setName(category.getName());
        updatingCategory.setDescription(category.getDescription());

        return Optional.of(categoryRepository.save(updatingCategory));
    }

    @Override
    public void deleteCategory(Integer id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with such ID cannot be found.")
        );

        if (categoryRepository.getReferenceById(id).getName().equals("None"))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a category");

        category.setIsActive(false);

        Category none = categoryRepository.findByName("None").orElseGet(
                () -> categoryRepository.save(Category.builder().name("None").build())
        );

        category.getSolutions().forEach(
                solution -> solution.setCategory(none)
        );

        subCategoryRepository.deleteAll(category.getSubCategories());

        categorySolutionRepository.deleteAll(category.getCategorySolutions());

    }


}
