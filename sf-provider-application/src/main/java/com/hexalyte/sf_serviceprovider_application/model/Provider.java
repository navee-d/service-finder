package com.hexalyte.sf_serviceprovider_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // FIX: Imported UUID

@Entity
@Data
@Table(name = "serviceproviders")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ServiceProviderID")
    private Long providerId;

    // FIX: Changed to UUID and set column definition for MySQL
    @Column(name = "UserId", columnDefinition = "binary(16)")
    private UUID userId;

    @Column(name = "BusinessName", nullable = false, length = 100)
    @NotBlank(message = "Business name cannot be null")
    @Length(max = 100,message = "Business name cannot exceed 100 characters")
    private String businessName;

    @Column(name = "WorkingHours", columnDefinition = "TIME")
    private LocalTime workingHours;

    @Column(name = "Holidays")
    private String holidays;

    @Column(name = "StaffAvailability")
    private String staffAvailability;

    @Column(name = "CreatedAt" , columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    @PastOrPresent(message = "Created at date cannot be a future date")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt" , columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @PastOrPresent(message = "Updated at date cannot be a future date")
    private LocalDateTime updatedAt;

    @JsonIgnoreProperties("provider")
    @OneToMany(mappedBy = "provider")
    private List<ProviderSocialMedia> providerSocialMedia = new ArrayList<>();

    @OneToMany(mappedBy = "provider")
    private List<Offer> offer = new ArrayList<>();
}