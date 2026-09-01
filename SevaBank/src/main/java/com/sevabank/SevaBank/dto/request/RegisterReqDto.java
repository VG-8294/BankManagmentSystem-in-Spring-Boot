package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterReqDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    @NotNull(message = "Age cannot be null")
    private int age;

    public RegisterReqDto() {
    }
}
