package com.sevabank.SevaBank.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TransferResDto {
    private long from;
    private long to;
    private double amt;
}
