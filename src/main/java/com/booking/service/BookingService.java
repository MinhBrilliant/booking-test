package com.booking.service;

import com.booking.entity.Booking;
import com.booking.modal.req.SaveBookingReq;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookingService {
    Optional<Booking> getBookingById(long id);

    boolean deleteEmployeeById(long id);

    Page<Booking> findPaginatedBooking(int pageNo, int pageSize);

    void saveNewBooking(SaveBookingReq request);

    String getLatestSeatTypeCode();

    Booking updateBooking(SaveBookingReq request);

    Page<Booking> findPaginatedBookingSeats(int pageNo, int pageSize);

    void update(Booking booking);
}
