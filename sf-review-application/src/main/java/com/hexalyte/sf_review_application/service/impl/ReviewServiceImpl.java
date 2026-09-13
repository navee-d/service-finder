package com.hexalyte.sf_review_application.service.impl;

import com.hexalyte.sf_review_application.model.Review;
import com.hexalyte.sf_review_application.repository.ReviewRepository;
import com.hexalyte.sf_review_application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID; // FIX: Import UUID

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    }

    // FIX: Changed to Long
    @Override
    public List<Review> getReviewsByServiceProvider(Long serviceProviderId) {
        return reviewRepository.findByServiceProviderId(serviceProviderId);
    }

    // FIX: Changed to UUID
    @Override
    public List<Review> getReviewsByUser(UUID userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public void deleteReview(Integer reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review not found with id: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }

    // FIX: Changed to Long
    @Override
    public Double getAverageRating(Long serviceProviderId) {
        List<Review> reviews = reviewRepository.findByServiceProviderId(serviceProviderId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream().mapToInt(Review::getRating).sum();
        return sum / reviews.size();
    }
}