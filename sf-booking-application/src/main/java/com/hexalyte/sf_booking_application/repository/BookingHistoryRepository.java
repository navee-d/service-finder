// src/main/java/com/hexalyte/sf_booking_application/repository/BookingHistoryRepository.java
package com.hexalyte.sf_booking_application.repository;

import com.hexalyte.sf_booking_application.model.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
    Optional<List<BookingHistory>> findByUserId(UUID userId);
    Optional<List<BookingHistory>> findByServiceProviderId(Long serviceProviderId);
}