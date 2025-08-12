package com.hexalyte.sf_booking_application.model.feign;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ServiceProviderDTO {
    private Long providerId;
    private String businessName;
    private LocalTime workingHours;
    private String holidays;
    private String staffAvailability;
}
