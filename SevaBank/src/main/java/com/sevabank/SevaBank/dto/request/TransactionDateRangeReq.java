package com.sevabank.SevaBank.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDateRangeReq {
    private LocalDateTime from;
    private LocalDateTime to;
}
