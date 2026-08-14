package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericDto<UserResponseDto> createUser(@RequestBody RegisterReqDto registerReqDto){
        UserResponseDto userDto =  userService.createUser(registerReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.CREATED, "user registered", userDto);
    }


    @PostMapping("/login")
    public GenericDto<UserResponseDto> loginUser(@RequestBody LoginReqDto loginReqDto){
        UserResponseDto loggedInUser = userService.login(loginReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfull", loggedInUser);
    }

    @PostMapping("/v1/login")
    public GenericDto<UserResponseDto> loginV1User(@RequestBody LoginReqDto loginReqDto){
        System.out.println("In User controller");
        UserResponseDto loggedInUser = userService.loginV1(loginReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfull", loggedInUser);
    }



}
