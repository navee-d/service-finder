package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer solutionId;

    private Integer serviceProviderId;
    private String name;
    private String description;
    private BigDecimal price;
    private String estimatedTime;
    private String reminderTime;
    private Boolean isAvailable;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    // --- Added missing lists ---
    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL)
    private List<CategorySolution> categorySolutions;

    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL)
    private List<SubCategorySolution> subCategorySolutions;
}