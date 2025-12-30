package com.hexalyte.sf_service_application.service.impl;

import com.hexalyte.sf_service_application.model.Loyalty;
import com.hexalyte.sf_service_application.repository.LoyaltyRepository;
import com.hexalyte.sf_service_application.service.LoyaltyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID; // Import UUID

@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyRepository loyaltyRepository;

    @Override
    @Transactional(readOnly = true)
    public Loyalty getLoyaltyByUserId(UUID userId) {
        return loyaltyRepository.findById(userId)
                .orElseGet(() -> createDefaultLoyalty(userId));
    }

    @Override
    @Transactional
    public Loyalty addPoints(UUID userId, int pointsToAdd) {
        if (pointsToAdd <= 0) {
            throw new IllegalArgumentException("Points must be positive.");
        }
        Loyalty loyalty = loyaltyRepository.findById(userId)
                .orElseGet(() -> createDefaultLoyalty(userId));

        loyalty.setPoints(loyalty.getPoints() + pointsToAdd);
        return loyaltyRepository.save(loyalty);
    }

    @Override
    @Transactional
    public Loyalty redeemPoints(UUID userId, int pointsToRedeem) {
        if (pointsToRedeem <= 0) {
            throw new IllegalArgumentException("Points must be positive.");
        }
        Loyalty loyalty = loyaltyRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " has no loyalty record."));

        if (loyalty.getPoints() < pointsToRedeem) {
            throw new RuntimeException("Not enough points.");
        }
        loyalty.setPoints(loyalty.getPoints() - pointsToRedeem);
        return loyaltyRepository.save(loyalty);
    }

    private Loyalty createDefaultLoyalty(UUID userId) {
        Loyalty newLoyalty = new Loyalty();
        newLoyalty.setUserId(userId);
        newLoyalty.setPoints(0);
        return newLoyalty;
    }
}