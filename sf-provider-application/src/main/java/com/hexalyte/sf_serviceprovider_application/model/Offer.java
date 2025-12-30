package com.hexalyte.sf_serviceprovider_application.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OfferID")
    private Long offerId;

    @ManyToOne
    @JoinColumn(name = "ServiceProviderID", nullable = false)
    @JsonIgnoreProperties("offer")
    private Provider provider;

    @Column(name = "Title", nullable = false, length = 100)
    @NotBlank(message = "Offer title cannot be null")
    @Length(max = 100,message = "Offer title cannot exceed 100 characters")
    private String title;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Discount", nullable = false, columnDefinition = "decimal(4,2)")
    @NotNull(message = "Discount cannot be null")
    @PositiveOrZero(message = "Discount cannot be negative")
    private Double discount;

    @Column(name = "ExpiryDate", columnDefinition = "date")
    @FutureOrPresent(message = "Expiry Date cannot be a past date")
    private LocalDate expiryDate;

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

}
