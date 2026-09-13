package com.hexalyte.sf_review_application.repository;

import com.hexalyte.sf_review_application.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID; // FIX: Import UUID

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // FIX: Changed parameter to Long
    List<Review> findByServiceProviderId(Long serviceProviderId);

    // FIX: Changed parameter to UUID
    List<Review> findByUserId(UUID userId);
}