package com.sevabank.SevaBank.dto.request;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateBankAccountRequest {
    @NotNull(message = "User Id cannot be null")
    private Long userId;
    @NotBlank(message = "Balance cannot be null as well as blank")
    private double balance;
    @NotBlank(message = "Account type cannot be blank")
    private String accountType;
}
