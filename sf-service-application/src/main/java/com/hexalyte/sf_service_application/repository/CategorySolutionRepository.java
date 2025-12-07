package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.CategorySolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorySolutionRepository extends JpaRepository<CategorySolution, CategorySolutionKey> {
    List<CategorySolution> findByCategory_CategoryId(int id);


    List<CategorySolution> findByService_SolutionId(int id);
}