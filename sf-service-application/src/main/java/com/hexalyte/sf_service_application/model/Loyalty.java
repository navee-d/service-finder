package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID; // Import UUID

@Entity
@Table(name = "loyaltypoints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loyalty {

    @Id
    // FIX: Changed Integer to UUID and added binary(16) definition
    @Column(name = "UserID", columnDefinition = "binary(16)")
    private UUID userId;

    @Column(name = "Points", nullable = false)
    @ColumnDefault("0")
    @Builder.Default
    private Integer points = 0;

    @CreationTimestamp
    @Column(name = "CreatedAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;
}