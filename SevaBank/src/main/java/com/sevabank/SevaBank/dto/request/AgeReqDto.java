package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AgeReqDto {
    @NotNull(message = "Age cannot be null")
    private int age1;
    @NotNull(message = "Age cannot be null")
    private int age2;
}
