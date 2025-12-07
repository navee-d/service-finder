// src/main/java/com/hexalyte/sf_booking_application/model/BookingHistory.java
package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookingshistory")
@Data
@NoArgsConstructor
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingHistoryID")
    private Long bookingHistoryId;

    // ⭐ FIX: Explicitly define UUID column for MySQL
    @Column(name = "UserID", columnDefinition = "binary(16)")
    private UUID userId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ServiceID")
    private Long serviceId;

    @Column(name = "StartTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "EndTime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "Status", nullable = false)
    private String status; // Store the status as a String

    @Column(name = "TotalPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalPrice;

    @Column(name = "Rating")
    private Double rating;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a history record from an active booking.
     * @param booking The booking to archive.
     */
    public BookingHistory(Booking booking) {
        this.userId = booking.getUserId();
        this.serviceProviderId = booking.getServiceProviderId();
        this.serviceId = booking.getServiceId();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();
        // ⭐ FIX: Use the correct enum values from BookingStatus.java
        this.status = booking.getStatus().name(); // Converts (Pending, Approved, etc.) to String
        this.totalPrice = booking.getTotalPrice();
        this.rating = booking.getRating();
        // The createdAt/updatedAt will be set by the database for the history record
    }
}