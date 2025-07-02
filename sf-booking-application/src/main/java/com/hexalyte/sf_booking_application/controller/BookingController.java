package com.hexalyte.sf_booking_application.controller;

import com.hexalyte.sf_booking_application.model.Booking;
import com.hexalyte.sf_booking_application.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<Booking>> getAllBookings(){
        return ResponseEntity.ofNullable(service.getAllBookings());
    }

    @GetMapping("{id}")
    private ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        return ResponseEntity.of(service.getBookingById(id));
    }
}
