package com.hexalyte.sf_booking_application.repository;

import com.hexalyte.sf_booking_application.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    Optional<List<Booking>> findByUserId(UUID userId);
    Optional<List<Booking>> findByServiceProviderId(Long serviceProviderId);
}
