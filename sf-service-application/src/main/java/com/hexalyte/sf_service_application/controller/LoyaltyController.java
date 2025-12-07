package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.Loyalty;
import com.hexalyte.sf_service_application.service.LoyaltyService;
import com.hexalyte.sf_service_application.model.feign.PointsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID; // Import UUID

@RestController
@RequestMapping("/api/v1/loyalty")
@RequiredArgsConstructor
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    @GetMapping("/{userId}")
    public ResponseEntity<Loyalty> getPoints(@PathVariable UUID userId) {
        return ResponseEntity.ok(loyaltyService.getLoyaltyByUserId(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Loyalty> addPoints(@PathVariable UUID userId, @RequestBody PointsRequestDto request) {
        return ResponseEntity.ok(loyaltyService.addPoints(userId, request.getPoints()));
    }

    @PostMapping("/{userId}/redeem")
    public ResponseEntity<Loyalty> redeemPoints(@PathVariable UUID userId, @RequestBody PointsRequestDto request) {
        return ResponseEntity.ok(loyaltyService.redeemPoints(userId, request.getPoints()));
    }
}