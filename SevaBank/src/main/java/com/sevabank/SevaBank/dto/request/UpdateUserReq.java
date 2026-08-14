package com.sevabank.SevaBank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserReq {
    private String name;
    private String email;
    private String password;
    private int age;
}
