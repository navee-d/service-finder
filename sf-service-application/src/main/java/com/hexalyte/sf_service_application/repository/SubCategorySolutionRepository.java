package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.model.SubCategorySolutionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategorySolutionRepository extends JpaRepository<SubCategorySolution, SubCategorySolutionKey> {


    List<SubCategorySolution> findByService_SolutionId(int id);

    List<SubCategorySolution> findBySubCategory_SubCategoryId(int subCategorySubCategoryId);
}