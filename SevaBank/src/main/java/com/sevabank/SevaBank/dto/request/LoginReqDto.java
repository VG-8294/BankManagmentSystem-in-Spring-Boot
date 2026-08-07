package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginReqDto {
    private Long accNo;
    private String email;
    private String password;
}
