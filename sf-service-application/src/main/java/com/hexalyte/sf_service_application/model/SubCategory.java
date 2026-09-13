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
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCategoryId;

    private String name;
    private String description; // Added missing field
    private Boolean isActive;   // Added missing field

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}