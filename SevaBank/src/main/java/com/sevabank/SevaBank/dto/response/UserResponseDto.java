package com.sevabank.SevaBank.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private int age;
}
