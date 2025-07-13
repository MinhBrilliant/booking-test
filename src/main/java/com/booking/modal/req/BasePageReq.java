package com.booking.modal.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageReq {
    private int pageNo = 1;
    private int pageSize;
}
