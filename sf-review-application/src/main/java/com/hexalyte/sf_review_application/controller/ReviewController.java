package com.hexalyte.sf_review_application.controller;

import com.hexalyte.sf_review_application.model.Review;
import com.hexalyte.sf_review_application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID; // FIX: Import UUID

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review newReview = reviewService.addReview(review);
        return ResponseEntity.ok(newReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // FIX: Changed to Long
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Review>> getReviewsByProvider(@PathVariable Long providerId) {
        List<Review> reviews = reviewService.getReviewsByServiceProvider(providerId);
        return ResponseEntity.ok(reviews);
    }

    // FIX: Changed to Long
    @GetMapping("/provider/{providerId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long providerId) {
        Double average = reviewService.getAverageRating(providerId);
        return ResponseEntity.ok(average);
    }

    // FIX: Changed to UUID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable UUID userId) {
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}