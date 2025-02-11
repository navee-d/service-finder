package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution,Long> {
}
