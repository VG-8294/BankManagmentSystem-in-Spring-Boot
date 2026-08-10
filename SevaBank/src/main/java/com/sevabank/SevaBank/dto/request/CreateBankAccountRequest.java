package com.sevabank.SevaBank.dto.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBankAccountRequest {

    private Long userId;
    private double balance;
    private String accountType;
    private Double interestRate;
    private Double overdraftLimit;
}
