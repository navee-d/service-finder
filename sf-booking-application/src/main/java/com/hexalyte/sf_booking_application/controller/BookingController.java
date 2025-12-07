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
    @RequestMapping("/bookings")
    public class BookingController {

        private final BookingService service;

        public BookingController(BookingService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<List<Booking>> getAllBookings() {
            List<Booking> bookings = service.getAllBookings();
            if (bookings.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(bookings);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
            return ResponseEntity.of(service.getBookingById(id));
        }

        @GetMapping("/users/{userId}")
        public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable UUID userId) {
            List<Booking> bookings = service.getBookingByUserId(userId);
            if (bookings.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(bookings);
        }

        @GetMapping("/service-providers/{spId}")
        public ResponseEntity<List<Booking>> getBookingByServiceProviderId(@PathVariable Long spId) {
            List<Booking> bookings = service.getBookingByServiceProviderId(spId);
            if (bookings.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(bookings);
        }

        @PostMapping
        public ResponseEntity<Booking> addBooking(@Valid @RequestBody Booking booking) {
            return service.addBooking(booking)
                    .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(b))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking booking) {
            return ResponseEntity.of(service.updateBooking(id, booking));
        }

        @PutMapping("/{id}/rating")
        public ResponseEntity<Booking> rateBooking(@PathVariable Long id, @RequestParam("rating") Double rating) {
            if (rating < 0 || rating > 5) return ResponseEntity.badRequest().build();

            Optional<Booking> updatedBooking = service.addRating(id, rating);
            return updatedBooking.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
            boolean deleted = service.deleteBooking(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
    }
