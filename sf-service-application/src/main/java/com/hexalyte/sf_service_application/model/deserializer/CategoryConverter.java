package com.hexalyte.sf_service_application.model.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CategoryConverter extends StdConverter<Integer, Category> {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category convert(Integer value) {
        Category category = repository.findByCategoryIdAndIsActive(value,true).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find category with such ID")
        );
        // 🔧 FIX: Removed block that threw an error for "None" category
        return category;
    }
}