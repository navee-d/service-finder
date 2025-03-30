package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.SolutionCategory;
import com.hexalyte.sf_service_application.model.SolutionCategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolutionCategoryRepository extends JpaRepository<SolutionCategory, SolutionCategoryKey> {
    Optional<List<SolutionCategory>> findBySolution_SolutionId(Long id);
    Optional<List<SolutionCategory>> findByCategory_CategoryId(Long id);
    void deleteAllBySolution_SolutionId(Long id);
    void deleteAllByCategory_CategoryId(Long id);
}
