package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_service_application.model.deserializer.CategoryConverter;
import com.hexalyte.sf_service_application.model.deserializer.SubCategoryConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Data
@Accessors(chain = true)
public class Solution {

    @Id
    @Column(name = "ServiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer solutionId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Name", length = 100, nullable = false)
    @NotBlank(message = "Service name cannot be null")
    @Length(max = 100, message = "Service name cannot exceed 100 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    @JsonDeserialize(converter = CategoryConverter.class)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SubcategoryID")
    @JsonDeserialize(converter = SubCategoryConverter.class)
    private SubCategory subCategory;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @NotNull(message = "Price cannot be null")
    private Double price;

    @Column(name = "EstimatedTime")
    @Positive(message = "Estimated time must be a positive value")
    private Integer estimatedTime;

    @Column(name = "ReminderTime", columnDefinition = "TIME")
    private LocalTime reminderTime;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    @PastOrPresent(message = "Created at date cannot be a future date")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @PastOrPresent(message = "Updated at date cannot be a future date")
    private LocalDateTime updatedAt;

    @Column(name = "IsAvailable")
    private Boolean isAvailable;

    @Column(name = "IsActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "solution", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CategorySolution> categorySolutions = new ArrayList<>();

    @OneToMany(mappedBy = "solution", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SubCategorySolution> subCategorySolutions;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;
}
