package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.model.CategorySolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySolutionRepository extends JpaRepository<CategorySolution, CategorySolutionKey> {
    // Finds entries by the 'solutionId' field inside the embedded 'id'
    List<CategorySolution> findById_SolutionId(Integer solutionId);

    // Finds entries by the 'categoryId' field inside the embedded 'id'
    List<CategorySolution> findById_CategoryId(Integer categoryId);
}