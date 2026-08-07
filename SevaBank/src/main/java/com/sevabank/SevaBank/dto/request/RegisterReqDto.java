package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReqDto {
    private String name;
    private String email;
    private String password;
    private int age;

    public RegisterReqDto() {
    }
}
