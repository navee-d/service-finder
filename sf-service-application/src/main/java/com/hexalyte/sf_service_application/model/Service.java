package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_service_application.model.deserializer.CategoryConverter;
import com.hexalyte.sf_service_application.model.deserializer.SubCategoryConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "solutionId")
public class Service {

    @Id
    @Column(name = "ServiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer solutionId;

    @Column(name = "Name", length = 100, nullable = false)
    @NotBlank(message = "Service name cannot be null")
    @Length(max = 100, message = "Service name cannot exceed 100 characters")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    @JsonDeserialize(converter = CategoryConverter.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubcategoryID")
    @JsonDeserialize(converter = SubCategoryConverter.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubCategory subCategory;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

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

    @Column(name = "isAvailable") // ✅ FIXED
    @Builder.Default
    private Boolean isAvailable = true;

    @Column(name = "isActive") // ✅ FIXED
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<CategorySolution> categorySolutions = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<SubCategorySolution> subCategorySolutions = new ArrayList<>();


    public Category getCategory() {
        if (category != null) return category;
        if (subCategory != null && subCategory.getCategory() != null) {
            return subCategory.getCategory();
        }
        return null;
    }

    @PrePersist
    private void prePersist() {
        if (isAvailable == null) isAvailable = true;
        if (isActive == null) isActive = true;
    }

    @PreRemove
    private void preRemove() {
        if (categorySolutions != null) {
            categorySolutions.clear();
        }
        if (subCategorySolutions != null) {
            subCategorySolutions.clear();
        }
    }
}