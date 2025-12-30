// src/main/java/com/hexalyte/sf_booking_application/service/impl/BookingServiceImpl.java
package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.Booking;
import com.hexalyte.sf_booking_application.model.BookingHistory;
import com.hexalyte.sf_booking_application.repository.BookingRepository;
import com.hexalyte.sf_booking_application.repository.BookingHistoryRepository;
import com.hexalyte.sf_booking_application.service.BookingService;
// ⭐ THIS IS THE MISSING IMPORT ⭐
import com.hexalyte.sf_booking_application.service.feign.UserInterface;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final BookingHistoryRepository historyRepository;
    private final UserInterface userInterface;

    public BookingServiceImpl(BookingRepository repository,
                              BookingHistoryRepository historyRepository,
                              UserInterface userInterface) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.userInterface = userInterface;
    }

    @Override
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Booking> getBookingByUserId(UUID userId) {
        return repository.findByUserId(userId).orElse(Collections.emptyList());
    }

    @Override
    public List<Booking> getBookingByServiceProviderId(Long serviceProviderId) {
        return repository.findByServiceProviderId(serviceProviderId).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Booking> addBooking(Booking booking) {
        // First, validate the user exists by calling the user-management-service
        // This will throw an exception if the user is not found
        userInterface.getUserById(booking.getUserId());

        // If the user exists, proceed to save the booking
        return Optional.of(repository.save(booking));
    }

    @Override
    public Optional<Booking> updateBooking(Long id, Booking booking) {
        // You could add user validation here too if needed
        return repository.findById(id).map(existing -> {
            booking.setBookingId(existing.getBookingId());
            return repository.save(booking);
        });
    }

    @Override
    public Optional<Booking> addRating(Long id, Double rating) {
        return repository.findById(id).map(booking -> {
            if (rating == null || rating < 0.0 || rating > 5.0) {
                throw new IllegalArgumentException("Rating must be between 0 and 5");
            }
            booking.setRating(rating);
            return repository.save(booking);
        });
    }

    @Override
    public boolean deleteBooking(Long id) {
        return repository.findById(id).map(booking -> {
            // 1. Create history record from the booking
            BookingHistory bookingHistory = new BookingHistory(booking);
            historyRepository.save(bookingHistory);

            // 2. Delete the original booking
            repository.delete(booking);
            return true;
        }).orElse(false);
    }
}