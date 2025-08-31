package com.hexalyte.sf_booking_application.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_booking_application.model.deserializer.BookingStatusDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@Accessors(chain = true)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID")
    private Long bookingId;

    @Column(name = "UserID")
    private UUID userId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ServiceID")
    private int serviceId;

    @Column(name = "StartTime", nullable = false)
    @FutureOrPresent(message = "Start time cannot be a past date and time")
    @NotNull(message = "Start time cannot be empty")
    private LocalDateTime startTime;

    @Column(name = "EndTime", nullable = false)
    @FutureOrPresent(message = "Start time cannot be a past date and time")
    @NotNull(message = "Start time cannot be empty")
    private LocalDateTime endTime;

    @Column(name = "Status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Booking Status cannot be empty")
    @JsonDeserialize(converter = BookingStatusDeserializer.class)
    private BookingStatus status;

    @Column(name = "TotalPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @PositiveOrZero(message = "Total price cannot be a negative number")
    @NotNull(message = "Total price cannot be empty")
    private BigDecimal totalPrice;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
