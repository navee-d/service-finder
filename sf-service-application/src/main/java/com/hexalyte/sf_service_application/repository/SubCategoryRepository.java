package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    boolean existsBySubCategoryIdAndCategory(int subCategoryId, Category category);

}
