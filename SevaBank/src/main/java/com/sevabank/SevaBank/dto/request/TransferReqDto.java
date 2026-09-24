package com.sevabank.SevaBank.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TransferReqDto {
    private long accNo1;
    private long accNo2;
    private double amt;
}
