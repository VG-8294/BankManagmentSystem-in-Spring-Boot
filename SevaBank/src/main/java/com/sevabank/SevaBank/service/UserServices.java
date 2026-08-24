package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.UserResponseDto;

public interface UserServices {
    UserResponseDto createUser(RegisterReqDto dto);

    UserResponseDto login(LoginReqDto loginReqDto);

    UserResponseDto loginV1(LoginReqDto loginReqDto);

    UserResponseDto getUserDetails(Long id);


//    UserResponseDto updateUser(Long id, UpdateUserReq updateReqUser);
//
//    UserResponseDto updateDetailsUser(Long id, UpdateUserReq updateReqUser);
}
