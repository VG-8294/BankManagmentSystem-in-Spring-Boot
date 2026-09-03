package com.sevabank.SevaBank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginReqDto {
    @Schema(
            description = "Account number of the user",
            example = "65"
    )
    private Long accNo;
    @Schema(
            description = "Email of the user",
            example = "vishal@gmail.com"
    )
    private String email;
    @Schema(
            description = "Password of the user",
            example = "Vishal@123"
    )
    private String password;
}
