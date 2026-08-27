package com.sevabank.SevaBank.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    @Schema(
            description = "Unique identifier of the user",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "Name of the user",
            example = "Vishal Gautam"
    )
    private String name;

    @Schema(
            description = "Email address",
            example = "vishal@gmail.com"
    )
    private String email;

    @Schema(
            description = "Age of the user",
            example = "25"
    )
    private Integer age;
}
