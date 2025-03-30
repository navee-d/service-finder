package com.hexalyte.sf_service_application.configuration;

import com.hexalyte.sf_service_application.model.SolutionCategory;
import com.hexalyte.sf_service_application.model.SolutionCategoryKey;
import com.hexalyte.sf_service_application.repository.SolutionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;

@Configuration
@Profile("development")
@Order
public class SolutionCategoryConfig implements CommandLineRunner {
    private final SolutionCategoryRepository repository;

    @Autowired
    private CategoryConfig categoryConfig;
    @Autowired
    private SolutionConfig solutionConfig;

    public SolutionCategoryConfig(SolutionCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        ArrayList<SolutionCategory> solutionCategoryList = new ArrayList<>();

        for (int x = 0; x < 8; x++) {

            int i = (int) (Math.random() * 3);
            int j = (int) (Math.random() * 3);

            SolutionCategory solutionCategory = SolutionCategory.
                    builder().
                    id(
                            SolutionCategoryKey.
                                    builder().
                                    solutionId(solutionConfig.getSolutions().get(i).getSolutionId()).
                                    categoryId(categoryConfig.getCategories().get(j).getCategoryId()).
                                    build()
                    ).solution(solutionConfig.getSolutions().get(i)).
                    category(categoryConfig.getCategories().get(j)).
                    build();

            solutionCategoryList.add(solutionCategory);
        }

        repository.saveAll(solutionCategoryList);
    }
}
