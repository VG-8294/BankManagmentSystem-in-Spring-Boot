package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.User;

public interface UserServices {
    UserResponseDto createUser(RegisterReqDto dto);

    Boolean login(LoginReqDto loginReqDto);

    Boolean loginV1(LoginReqDto loginReqDto);
}
