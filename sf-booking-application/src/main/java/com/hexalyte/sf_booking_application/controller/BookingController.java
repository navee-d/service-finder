package com.hexalyte.sf_booking_application.controller;

import com.hexalyte.sf_booking_application.model.Booking;
import com.hexalyte.sf_booking_application.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ofNullable(service.getAllBookings());
    }

    @GetMapping("{id}")
    private ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.of(service.getBookingById(id));
    }

    @GetMapping("users/{id}")
    private ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable UUID id) {
        return ResponseEntity.of(service.getBookingByUserId(id));
    }

    @GetMapping("service-providers/{id}")
    private ResponseEntity<List<Booking>> getBookingByServiceProviderId(@PathVariable long id) {
        return ResponseEntity.of(service.getBookingByServiceProviderId(id));
    }

    @PostMapping
    private ResponseEntity<Optional<Booking>> addBooking(@Valid @RequestBody Booking booking) {
        return new ResponseEntity<>(service.addBooking(booking), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    private ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking booking) {
        return ResponseEntity.of(service.updateBooking(id, booking));
    }

}
