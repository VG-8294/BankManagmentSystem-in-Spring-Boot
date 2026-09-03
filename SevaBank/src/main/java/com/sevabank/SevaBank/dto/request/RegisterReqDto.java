package com.sevabank.SevaBank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReqDto {
    @Schema(
            description = "Full name of the user",
            example = "Vishal Gautam"
    )
    private String name;

    @Schema(
            description = "User's email address",
            example = "vishal@gmail.com"
    )
    private String email;

    @Schema(
            description = "Password used for authentication",
            example = "Vishal@123"
    )
    private String password;

    @Schema(
            description = "Age of the user",
            example = "25"
    )
    private Integer age;

    public RegisterReqDto() {
    }
}
