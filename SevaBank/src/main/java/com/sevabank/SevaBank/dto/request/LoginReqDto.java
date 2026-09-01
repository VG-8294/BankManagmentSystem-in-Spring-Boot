package com.sevabank.SevaBank.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Password not valid")
    private String password;
}
