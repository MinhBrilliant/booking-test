package com.booking.entity;

import com.booking.modal.req.SaveBookingReq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_type_cd", unique = true)
    private String seatTypeCode;
    @Column(name = "seat_type_nm", nullable = false)
    private String seatTypeName;
    @Column(name = "worker_memo")
    private String workerMemo;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "is_booked")
    private boolean booked;

    public Booking(SaveBookingReq req){
        this.seatTypeName = req.getSeatTypeName();
        this.workerMemo = req.getWorkerMemo();
        this.seatTypeCode = req.getSeatTypeCode();
    }
}
