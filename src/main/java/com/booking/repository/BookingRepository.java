package com.booking.repository;

import com.booking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByOrderBySeatTypeCodeAsc();
    @Query(value = "select seat_type_cd from booking order by seat_type_cd desc limit 1", nativeQuery = true)
    String getLatestSeatTypeCode();

    Booking findFirstByOrderBySeatTypeCodeDesc();

    Optional<Booking> findByIdAndDeleted(Long id, boolean isDeleted);

    Page<Booking> findByBookedAndDeleted(boolean isBooked, boolean isDeleted, Pageable pageable);
}
