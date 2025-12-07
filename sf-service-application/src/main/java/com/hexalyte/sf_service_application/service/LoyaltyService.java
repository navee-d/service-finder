package com.hexalyte.sf_service_application.service;

import com.hexalyte.sf_service_application.model.Loyalty;
import java.util.UUID; // Import UUID

public interface LoyaltyService {
    // FIX: Updated method signatures to use UUID
    Loyalty getLoyaltyByUserId(UUID userId);
    Loyalty addPoints(UUID userId, int pointsToAdd);
    Loyalty redeemPoints(UUID userId, int pointsToRedeem);
}