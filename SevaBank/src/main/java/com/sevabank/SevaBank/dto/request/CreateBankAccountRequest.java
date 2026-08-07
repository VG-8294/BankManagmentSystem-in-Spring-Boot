package com.sevabank.SevaBank.dto.request;

import com.sevabank.SevaBank.Enum.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBankAccountRequest {

    private Long userId;
    private double balance;
    private AccountType accountType;
    private Double interestRate;
    private Double overdraftLimit;
}
