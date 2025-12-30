package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findByName(String name);

    List<Category> findAllByIsActive(Boolean isActive);

    Optional<Category> findByCategoryIdAndIsActive(Integer categoryId, Boolean isActive);
}
