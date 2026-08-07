package com.sevabank.SevaBank.dto.response;

import com.sevabank.SevaBank.Enum.AccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountResponseDto {
    private Long accNo;
    private String user_name;
    private String email;
    private AccountType accountType;
    private Double balance;
}
