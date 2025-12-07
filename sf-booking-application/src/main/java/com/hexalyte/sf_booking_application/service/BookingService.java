package com.hexalyte.sf_booking_application.service;

import com.hexalyte.sf_booking_application.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {

    List<Booking> getAllBookings();

    Optional<Booking> getBookingById(Long id);

    List<Booking> getBookingByUserId(UUID userId);

    List<Booking> getBookingByServiceProviderId(Long serviceProviderId);

    Optional<Booking> addBooking(Booking booking);

    Optional<Booking> updateBooking(Long id, Booking booking);

    Optional<Booking> addRating(Long id, Double rating);

    // ⭐ New delete method
    boolean deleteBooking(Long id);
}
