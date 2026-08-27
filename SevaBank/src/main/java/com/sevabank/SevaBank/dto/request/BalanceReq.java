package com.sevabank.SevaBank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BalanceReq {
    @Schema(
            description = "Amount to be requested",
            example = "5000"
    )
    private double balance;
}
