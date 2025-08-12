package com.hexalyte.sf_booking_application.service;

import com.hexalyte.sf_booking_application.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Optional<List<Booking>> getBookingByUserId(UUID id);
    Optional<List<Booking>> getBookingByServiceProviderId(Long id);
    Optional<Booking> addBooking(Booking booking);
    Optional<Booking> updateBooking(Long id, Booking booking);
}
