package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.SolutionCategory;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import com.hexalyte.sf_service_application.repository.SolutionCategoryRepository;
import com.hexalyte.sf_service_application.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SolutionCategoryRepository solutionCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, SolutionCategoryRepository solutionCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.solutionCategoryRepository = solutionCategoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        return category;
    }

    @Override
    public Optional<List<SolutionCategory>> getCategoryServices(Long id) {
        return solutionCategoryRepository.findByCategory_CategoryId(id);
    }

    @Override
    public Optional<Category> addCategory(Category category) {

        category.setDescription(
                category.getDescription().isBlank() ? null : category.getDescription()
        );

        return Optional.of(categoryRepository.save(category));
    }

    @Override
    public Optional<Category> updateCategory(Long id,Category category) {

        category.setDescription(
                category.getDescription().isBlank() ? null : category.getDescription()
        );

        if (!categoryRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        category.setCategoryId(id);
        return Optional.of(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        solutionCategoryRepository.deleteAllByCategory_CategoryId(id);
        categoryRepository.deleteById(id);

    }


}
