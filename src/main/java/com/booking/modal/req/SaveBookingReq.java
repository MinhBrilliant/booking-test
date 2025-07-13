package com.booking.modal.req;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class SaveBookingReq{
    private Long id;
    @NotNull(message = "seatTypeCode value from 01 to 99!")
    @NotEmpty(message = "seatTypeCode value from 01 to 99!")
    @Size(min = 2, max = 2, message = "seatTypeCode value from 01 to 99!")
    private String seatTypeCode;
    @NotNull(message = "seatTypeName is null or empty!")
    @NotEmpty(message = "seatTypeName is null or empty!")
    @Size(max = 30, message = "Length of seatTypeName from 1 to 30!")
    private String seatTypeName;
    private String workerMemo;
}
