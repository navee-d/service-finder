// src/main/java/com/hexalyte/sf_booking_application/service/impl/BookingHistoryServiceImpl.java
package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.BookingHistory;
import com.hexalyte.sf_booking_application.repository.BookingHistoryRepository;
import com.hexalyte.sf_booking_application.service.BookingHistoryService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {

    private final BookingHistoryRepository historyRepository;

    public BookingHistoryServiceImpl(BookingHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<BookingHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    @Override
    public List<BookingHistory> getHistoryByUserId(UUID userId) {
        return historyRepository.findByUserId(userId).orElse(Collections.emptyList());
    }

    @Override
    public List<BookingHistory> getHistoryByServiceProviderId(Long serviceProviderId) {
        return historyRepository.findByServiceProviderId(serviceProviderId).orElse(Collections.emptyList());
    }
}