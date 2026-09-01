package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BalanceReq {
    @NotBlank(message = "Balance cannot be null or blank")
    private double balance;
}
