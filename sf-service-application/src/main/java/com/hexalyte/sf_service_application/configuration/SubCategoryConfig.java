package com.hexalyte.sf_service_application.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.repository.SubCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@Profile("development")
@AutoConfigureAfter(CategoryConfig.class)
@ConditionalOnBean(CategoryConfig.class)
@DependsOn(value = "categoryConfig")
public class SubCategoryConfig implements CommandLineRunner {

    private final SubCategoryRepository repository;
    private final ObjectMapper objectMapper;

    public SubCategoryConfig(SubCategoryRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/Data/subcategories.json")) {
            repository.saveAll(objectMapper.readValue(inputStream, new TypeReference<List<SubCategory>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
