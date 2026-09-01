package com.sevabank.SevaBank.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginReqDto {
    @NotBlank(message = "Account number cannot be null")
    private Long accNo;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
}
