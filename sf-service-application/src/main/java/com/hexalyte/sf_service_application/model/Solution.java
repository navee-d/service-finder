package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solution")
@Data
public class Solution{

        @Id
        @Column(name = "SolutionID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long solutionId;

        @Column(name = "UserID")
        private Integer userId;

        @Column(name = "Name", length = 100, nullable = false)
        @Length(max = 100, message = "Name cannot exceed 100 characters")
        private String name;

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

        @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP",updatable = false)
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

        @JsonIgnoreProperties("solution")
        @OneToMany(mappedBy = "solution")
        private List<SolutionCategory> solutionCategories = new ArrayList<>();

        @Transient
        private List<Long> categoryIds = new ArrayList<>();

}
