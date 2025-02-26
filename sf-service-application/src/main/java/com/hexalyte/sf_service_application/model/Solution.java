package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "solution")
public record Solution(
        @Id
        @Column(name = "SolutionID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long solutionID,

        @Column(name = "UserID")
        Integer userID,

        @Column(name = "Name", length = 100, nullable = false)
        String name,

        @Column(name = "Description", columnDefinition = "TEXT")
        String description,

        @Column(name = "Price", nullable = false, columnDefinition = "DECIMAL(10,2)")
        Double price,

        @Column(name = "EstimatedTime")
        Integer estimatedTime,

        @Column(name = "ReminderTime", columnDefinition = "TIME")
        LocalTime reminderTime,

        @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP")
        @ColumnDefault("CURRENT_TIMESTAMP")
        LocalDateTime createdAt,

        @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
        @ColumnDefault("CURRENT_TIMESTAMP")
        LocalDateTime updatedAt
) {
}
