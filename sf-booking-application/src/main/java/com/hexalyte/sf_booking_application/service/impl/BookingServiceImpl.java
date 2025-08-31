package com.hexalyte.sf_booking_application.service.impl;

import com.hexalyte.sf_booking_application.model.Booking;
import com.hexalyte.sf_booking_application.model.feign.SolutionDTO;
import com.hexalyte.sf_booking_application.repository.BookingRepository;
import com.hexalyte.sf_booking_application.service.BookingService;
import com.hexalyte.sf_booking_application.service.feign.ServiceInterface;
import com.hexalyte.sf_booking_application.service.feign.ServiceProviderInterface;
import com.hexalyte.sf_booking_application.service.feign.UserInterface;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserInterface userInterface;
    private final ServiceInterface serviceInterface;
    private final ServiceProviderInterface serviceProviderInterface;

    public BookingServiceImpl(BookingRepository bookingRepository, UserInterface userInterface, ServiceInterface serviceInterface, ServiceProviderInterface serviceProviderInterface) {
        this.bookingRepository = bookingRepository;
        this.userInterface = userInterface;
        this.serviceInterface = serviceInterface;
        this.serviceProviderInterface = serviceProviderInterface;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty())
            return null;
        return bookings;
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find booking by this ID");
        return booking;
    }

    @Override
    public Optional<List<Booking>> getBookingByUserId(UUID id) {
        try {
            userInterface.getUserById(id);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user with such ID");
        }

        Optional<List<Booking>> bookings = bookingRepository.findByUserId(id);
        if (bookings.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find any bookings under this user");
        return bookings;
    }

    @Override
    public Optional<List<Booking>> getBookingByServiceProviderId(Long id) {
        try {
            serviceProviderInterface.getProviderByID(id);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service provider with such ID");
        }

        Optional<List<Booking>> bookings = bookingRepository.findByServiceProviderId(id);
        if (bookings.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Cannot find any bookings under this user");
        return bookings;
    }

    @Override
    public Optional<Booking> addBooking(Booking booking) {

        try {
            userInterface.getUserById(booking.getUserId());
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user with such ID");
        }

        try {
            SolutionDTO service = serviceInterface.getServiceById(booking.getServiceId()).getBody();

            if (!service.getServiceProviderId().equals(booking.getServiceProviderId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service provider does not provide " + service.getName());

        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        }

        if (booking.getStartTime().isAfter(booking.getEndTime()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time cannot be a time after end time");

        return Optional.of(bookingRepository.save(booking));
    }

    @Override
    public Optional<Booking> updateBooking(Long id, Booking booking) {
        Booking updatingBooking = bookingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find booking by this ID")
        );

        try {
            userInterface.getUserById(booking.getUserId());
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user with such ID");
        }

        try {
            SolutionDTO service = serviceInterface.getServiceById(booking.getServiceId()).getBody();

            if (!service.getServiceProviderId().equals(booking.getServiceProviderId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This service provider does not provide " + service.getName());

        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find service with such ID");
        }

        if (booking.getStartTime().isAfter(booking.getEndTime()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time cannot be a time after end time");

        updatingBooking
                .setServiceId(booking.getServiceId())
                .setServiceProviderId(booking.getServiceProviderId())
                .setUserId(booking.getUserId())
                .setStatus(booking.getStatus())
                .setTotalPrice(booking.getTotalPrice());

        if (!updatingBooking.getStartTime().isEqual(booking.getStartTime()))
            updatingBooking.setStartTime(booking.getStartTime());
        if (!updatingBooking.getEndTime().equals(booking.getEndTime()))
            updatingBooking.setEndTime(booking.getEndTime());

        return Optional.of(bookingRepository.save(updatingBooking));
    }
}
