package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.Booking;
import com.hexalyte.sf_booking_application.repository.BookingRepository;
import com.hexalyte.sf_booking_application.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty())
            return null;
        return bookings;
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find booking by this ID");
        return booking;
    }

    @Override
    public Optional<Booking> addBooking(Booking booking) {
        return Optional.empty();
    }
}
