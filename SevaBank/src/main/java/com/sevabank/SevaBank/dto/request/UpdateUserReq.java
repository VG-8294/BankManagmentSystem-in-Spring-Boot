package com.sevabank.SevaBank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserReq {
    @Schema(
            description = "name",
            example = "Vishal"
    )
    private String name;
    @Schema(
            description = "email",
            example = "vishal@mail.com"
    )
    private String email;
    @Schema(
            description = "password",
            example = "vishal@123"
    )
    private String password;
    @Schema(
            description = "age",
            example = "28"
    )
    private int age;
}
