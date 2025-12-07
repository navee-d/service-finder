package com.hexalyte.sf_service_application.repository;

import com.hexalyte.sf_service_application.model.Loyalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID; // Import UUID

@Repository
// FIX: Changed ID type from Integer to UUID
public interface LoyaltyRepository extends JpaRepository<Loyalty, UUID> {
}