package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    private Integer serviceProviderId; // Added missing field
    private String name;
    private String description;
    private java.math.BigDecimal price; // Added missing field
    private String estimatedTime; // Added missing field
    private String reminderTime; // Added missing field
    private Boolean isAvailable; // Added missing field
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}