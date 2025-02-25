package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import com.hexalyte.sf_service_application.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getCategories() {
        return repository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        Optional<Category> category = repository.findById(id);
        if (category.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        return category;
    }

    @Override
    public Optional<Category> addCategory(Category category) {

        category.setDescription(
                category.getDescription().isBlank() ? null : category.getDescription()
        );

        return Optional.of(repository.save(category));
    }

    @Override
    public Optional<Category> updateCategory(Long id,Category category) {

        category.setDescription(
                category.getDescription().isBlank() ? null : category.getDescription()
        );

        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        category.setCategoryId(id);
        return Optional.of(repository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {

        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID");
        repository.deleteById(id);

    }


}
