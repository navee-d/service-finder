package com.hexalyte.sf_booking_application.controller;

import com.hexalyte.sf_booking_application.model.BookingHistory;
import com.hexalyte.sf_booking_application.service.BookingHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-history")
public class BookingHistoryController {

    private final BookingHistoryService service;

    public BookingHistoryController(BookingHistoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BookingHistory>> getAllHistory() {
        List<BookingHistory> history = service.getAllHistory();
        if (history.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BookingHistory>> getHistoryByUserId(@PathVariable UUID userId) {
        List<BookingHistory> history = service.getHistoryByUserId(userId);
        if (history.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/service-providers/{spId}")
    public ResponseEntity<List<BookingHistory>> getHistoryByServiceProviderId(@PathVariable Long spId) {
        List<BookingHistory> history = service.getHistoryByServiceProviderId(spId);
        if (history.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(history);
    }
}
