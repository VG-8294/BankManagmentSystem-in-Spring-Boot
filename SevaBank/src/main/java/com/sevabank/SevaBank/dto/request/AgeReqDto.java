package com.sevabank.SevaBank.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgeReqDto {
    @Schema(
            description = "starting age",
            example = "25"
    )
    private int age1;
    @Schema(
            description = "ending age",
            example = "35"
    )
    private int age2;
}
