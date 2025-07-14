package com.booking.service.impl;

import com.booking.modal.req.SaveBookingReq;
import com.booking.repository.BookingRepository;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;

    @Override
    public Optional<Booking> getBookingById(long id) {
        return bookingRepository.findByIdAndDeleted(id, false);
    }

    @Override
    public boolean deleteEmployeeById(long id) {
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndDeleted(id, false);
        if (bookingOptional.isEmpty()) return false;
        Booking booking = bookingOptional.get();
        booking.setDeleted(true);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public Page<Booking> findPaginatedBooking(int pageNo, int pageSize) {
        Sort sort = Sort.by("seatTypeCode").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return bookingRepository.findAll(pageable);
    }

    @Override
    public void saveNewBooking(SaveBookingReq request) {
        Booking booking = new Booking(request);
        bookingRepository.save(booking);

    }

    @Override
    public String getLatestSeatTypeCode() {
        String latestSeatTypeCode = "0";
        Booking booking = bookingRepository.findFirstByOrderBySeatTypeCodeDesc();
        if (!ObjectUtils.isEmpty(booking)){
            latestSeatTypeCode = booking.getSeatTypeCode();
        }
        int nextSeatTypeCode = Integer.parseInt(latestSeatTypeCode) + 1;
        if (nextSeatTypeCode < 10){
            latestSeatTypeCode = "0" + nextSeatTypeCode;
        }
        else {
            latestSeatTypeCode = "" + nextSeatTypeCode;
        }
        return latestSeatTypeCode;
    }

    @Override
    public Booking updateBooking(SaveBookingReq request) {
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndDeleted(request.getId(), false);
        if (bookingOptional.isEmpty()) return null;
        Booking booking = bookingOptional.get();
        booking.setSeatTypeName(request.getSeatTypeName());
        booking.setWorkerMemo(request.getWorkerMemo());
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Page<Booking> findPaginatedBookingSeats(int pageNo, int pageSize) {
        Sort sort = Sort.by("seatTypeCode").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return bookingRepository.findByBookedAndDeleted(false, false, pageable);
    }

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);
    }
}
