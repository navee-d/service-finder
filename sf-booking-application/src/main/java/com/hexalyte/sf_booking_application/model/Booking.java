package com.hexalyte.sf_booking_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID")
    private Long bookingId;

    @Column(name = "UserID")
    private Long userId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ServiceID")
    private Long serviceId;

    @Column(name = "Date", nullable = false)
    @FutureOrPresent(message = "Date cannot be a past date")
    private LocalDate date;

    @Column(name = "Time", nullable = false, columnDefinition = "TIME")
    @FutureOrPresent(message = "Time cannot be a past time")
    private LocalTime time;

    @Column(name = "Status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;

    @Column(name = "TotalPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @PositiveOrZero(message = "Total price cannot be a negative number")
    private Double totalPrice;

    @Column(name = "CreatedAt", columnDefinition = "TIMESTAMP", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
