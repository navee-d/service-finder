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

        if (booking.getStartTime().isAfter(booking.getEndTime()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Start time cannot be a time after end time");

        return Optional.of(bookingRepository.save(booking));
    }

    @Override
    public Optional<Booking> updateBooking(Long id, Booking booking) {
        Booking updatingBooking = bookingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find booking by this ID")
        );

        if (booking.getStartTime().isAfter(booking.getEndTime()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Start time cannot be a time after end time");

        updatingBooking
                .setServiceId(booking.getServiceId())
                .setServiceProviderId(booking.getServiceProviderId())
                .setUserId(booking.getUserId())
                .setStatus(booking.getStatus())
                .setTotalPrice(booking.getTotalPrice());

        if (!updatingBooking.getStartTime().isEqual(booking.getStartTime()))
            updatingBooking.setStartTime(booking.getStartTime());
        if (!updatingBooking.getEndTime().equals(booking.getEndTime()))
            updatingBooking.setEndTime(booking.getEndTime());

        return Optional.of(bookingRepository.save(updatingBooking));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.delete(
                bookingRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find booking by this ID")
                )
        );
    }
}
