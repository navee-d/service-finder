package com.hexalyte.sf_review_application.service;

import com.hexalyte.sf_review_application.model.Review;
import java.util.List;
import java.util.UUID; // Import UUID

public interface ReviewService {

    Review addReview(Review review);

    Review getReviewById(Integer reviewId);

    List<Review> getReviewsByServiceProvider(Integer serviceProviderId);

    // FIX: Change Integer to UUID
    List<Review> getReviewsByUser(UUID userId);

    void deleteReview(Integer reviewId);

    Double getAverageRating(Integer serviceProviderId);
}