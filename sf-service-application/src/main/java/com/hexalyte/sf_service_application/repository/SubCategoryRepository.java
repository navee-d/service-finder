package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    // Fixed method to check existence by subCategoryId and its Category's categoryId
    boolean existsBySubCategoryIdAndCategory_CategoryId(Integer subCategoryId, Integer categoryId);

}
