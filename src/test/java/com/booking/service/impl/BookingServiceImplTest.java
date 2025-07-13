package com.booking.service.impl;

import com.booking.entity.Booking;
import com.booking.modal.req.SaveBookingReq;
import com.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository mockBookingRepository;

    private BookingServiceImpl bookingServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        bookingServiceImplUnderTest = new BookingServiceImpl(mockBookingRepository);
    }

    @Test
    void testGetBookingById() {
        // Setup
        final Optional<Booking> expectedResult = Optional.of(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));

        // Configure BookingRepository.findByIdAndDeleted(...).
        final Optional<Booking> bookingOptional = Optional.of(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(bookingOptional);

        // Run the test
        final Optional<Booking> result = bookingServiceImplUnderTest.getBookingById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetBookingById_BookingRepositoryReturnsAbsent() {
        // Setup
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Booking> result = bookingServiceImplUnderTest.getBookingById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteEmployeeById() {
        // Setup
        // Configure BookingRepository.findByIdAndDeleted(...).
        final Optional<Booking> bookingOptional = Optional.of(
                new Booking(0L, "01", "seatTypeName", "workerMemo",false, false));
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(bookingOptional);

        // Run the test
        final boolean result = bookingServiceImplUnderTest.deleteEmployeeById(0L);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockBookingRepository).save(
                new Booking(0L, "01", "seatTypeName", "workerMemo",true, false));
    }

    @Test
    void testDeleteEmployeeById_BookingRepositoryFindByIdAndDeletedReturnsAbsent() {
        // Setup
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(Optional.empty());

        // Run the test
        final boolean result = bookingServiceImplUnderTest.deleteEmployeeById(0L);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testFindPaginatedBooking() {
        // Setup
        // Configure BookingRepository.findAll(...).
        final Page<Booking> bookings = new PageImpl<>(
                List.of(new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo"))));
        when(mockBookingRepository.findAll(any(Pageable.class))).thenReturn(bookings);

        // Run the test
        final Page<Booking> result = bookingServiceImplUnderTest.findPaginatedBooking(1, 5);

        // Verify the results
    }

    @Test
    void testFindPaginatedBooking_BookingRepositoryReturnsNoItems() {
        // Setup
        when(mockBookingRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Booking> result = bookingServiceImplUnderTest.findPaginatedBooking(1, 5);

        // Verify the results
    }

    @Test
    void testSaveNewBooking() {
        // Setup
        final SaveBookingReq request = new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo");

        // Run the test
        bookingServiceImplUnderTest.saveNewBooking(request);

        // Verify the results
        verify(mockBookingRepository).save(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));
    }

    @Test
    void testGetLatestSeatTypeCode() {
        // Setup
        when(mockBookingRepository.getLatestSeatTypeCode()).thenReturn("03");

        // Run the test
        final String result = bookingServiceImplUnderTest.getLatestSeatTypeCode();

        // Verify the results
        assertThat(result).isEqualTo("04");
    }

    @Test
    void testUpdateBooking() {
        // Setup
        final SaveBookingReq request = new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo");
        final Booking expectedResult = new Booking(
                new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo"));

        // Configure BookingRepository.findByIdAndDeleted(...).
        final Optional<Booking> bookingOptional = Optional.of(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(bookingOptional);

        // Run the test
        final Booking result = bookingServiceImplUnderTest.updateBooking(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockBookingRepository).save(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));
    }

    @Test
    void testUpdateBooking_BookingRepositoryFindByIdAndDeletedReturnsAbsent() {
        // Setup
        final SaveBookingReq request = new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo");
        when(mockBookingRepository.findByIdAndDeleted(0L, false)).thenReturn(Optional.empty());

        // Run the test
        final Booking result = bookingServiceImplUnderTest.updateBooking(request);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testFindPaginatedBookingSeats() {
        // Setup
        // Configure BookingRepository.findByBookedAndDeleted(...).
        final Page<Booking> bookings = new PageImpl<>(
                List.of(new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo"))));
        when(mockBookingRepository.findByBookedAndDeleted(eq(false), eq(false), any(Pageable.class)))
                .thenReturn(bookings);

        // Run the test
        final Page<Booking> result = bookingServiceImplUnderTest.findPaginatedBookingSeats(1, 5);

        // Verify the results
    }

    @Test
    void testFindPaginatedBookingSeats_BookingRepositoryReturnsNoItems() {
        // Setup
        when(mockBookingRepository.findByBookedAndDeleted(eq(false), eq(false), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Booking> result = bookingServiceImplUnderTest.findPaginatedBookingSeats(1, 5);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final Booking booking = new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo"));

        // Run the test
        bookingServiceImplUnderTest.update(booking);

        // Verify the results
        verify(mockBookingRepository).save(
                new Booking(new SaveBookingReq(0L, "01", "seatTypeName", "workerMemo")));
    }
}
