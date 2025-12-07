package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
