package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.model.SubCategorySolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategorySolutionRepository extends JpaRepository<SubCategorySolution, SubCategorySolutionKey> {
    // Finds entries by the 'solutionId' field inside the embedded 'id'
    List<SubCategorySolution> findById_SolutionId(Integer solutionId);

    // Finds entries by the 'subCategoryId' field inside the embedded 'id'
    List<SubCategorySolution> findById_SubCategoryId(Integer subCategoryId);
}