// src/main/java/com/hexalyte/sf_booking_application/service/BookingHistoryService.java
package com.hexalyte.sf_booking_application.service;

import com.hexalyte.sf_booking_application.model.BookingHistory;

import java.util.List;
import java.util.UUID;

public interface BookingHistoryService {
    List<BookingHistory> getAllHistory();
    List<BookingHistory> getHistoryByUserId(UUID userId);
    List<BookingHistory> getHistoryByServiceProviderId(Long serviceProviderId);
}