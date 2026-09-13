package com.hexalyte.sf_review_application.service;

import com.hexalyte.sf_review_application.model.Review;
import java.util.List;
import java.util.UUID; // FIX: Import UUID

public interface ReviewService {

    Review addReview(Review review);

    Review getReviewById(Integer reviewId);

    // FIX: Changed to Long
    List<Review> getReviewsByServiceProvider(Long serviceProviderId);

    // FIX: Changed to UUID
    List<Review> getReviewsByUser(UUID userId);

    void deleteReview(Integer reviewId);

    // FIX: Changed to Long
    Double getAverageRating(Long serviceProviderId);
}