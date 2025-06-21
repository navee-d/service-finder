package com.hexalyte.sf_service_application.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.repository.CategoryRepository;
import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@Profile("development")
public class CategoryConfig implements CommandLineRunner {

    private final CategoryRepository repository;
    private final ObjectMapper objectMapper;

    @Getter
    private List<Category> categories;

    public CategoryConfig(CategoryRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/Data/categories.json")) {
            repository.saveAll(objectMapper.readValue(inputStream, new TypeReference<List<Category>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
