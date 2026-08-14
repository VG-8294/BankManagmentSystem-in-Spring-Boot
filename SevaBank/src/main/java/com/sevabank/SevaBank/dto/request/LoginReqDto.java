package com.sevabank.SevaBank.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginReqDto {
    private Long accNo;
    private String email;
    private String password;
}
