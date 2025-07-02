package com.hexalyte.sf_booking_application.repository;

import com.hexalyte.sf_booking_application.model.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistory,Long> {
}
