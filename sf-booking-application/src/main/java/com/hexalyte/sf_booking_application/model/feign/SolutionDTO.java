package com.hexalyte.sf_booking_application.model.feign;

import lombok.Data;

import java.time.LocalTime;

@Data
public class SolutionDTO {
    private int solutionId;
    private String name;
    private String description;
    private double price;
    private int estimatedTime;
    private LocalTime reminderTime;
    private Boolean isAvailable;
    private Boolean isActive;
    private Long serviceProviderId;
}
