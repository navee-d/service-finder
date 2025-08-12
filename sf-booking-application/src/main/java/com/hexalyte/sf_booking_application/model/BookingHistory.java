package com.hexalyte.sf_booking_application.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_booking_application.model.deserializer.BookingStatusDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "BookingsHistory")
@Data
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingHistoryID")
    private Long bookingHistoryId;

    @Column(name = "UserID")
    private Long userId;

    @Column(name = "ServiceProviderID")
    private Long serviceProviderId;

    @Column(name = "ServiceID")
    private Long serviceId;

    @Column(name = "Date", nullable = false)
    @Past(message = "Date must be a past date")
    @NotNull(message = "Date cannot be empty")
    private LocalDate date;

    @Column(name = "Time", nullable = false, columnDefinition = "TIME")
    @Past(message = "Time must be a past time")
    @NotNull(message = "Time cannot be empty")
    private LocalTime time;

    @Column(name = "Status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Booking Status cannot be empty")
    @JsonDeserialize(converter = BookingStatusDeserializer.class)
    private BookingStatus status;

    @Column(name = "TotalPrice", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @PositiveOrZero(message = "Total price cannot be a negative number")
    @NotNull(message = "Total price cannot be empty")
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
