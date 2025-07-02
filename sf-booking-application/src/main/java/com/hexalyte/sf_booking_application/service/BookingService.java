package com.hexalyte.sf_booking_application.service;

import com.hexalyte.sf_booking_application.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Optional<Booking> addBooking(Booking booking);
}
