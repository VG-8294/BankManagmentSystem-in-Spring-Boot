package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserResponseDto createUser(@RequestBody RegisterReqDto registerReqDto){
        return userService.createUser(registerReqDto);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody LoginReqDto loginReqDto){
        Boolean loggedInUser = userService.login(loginReqDto);
        if(!loggedInUser){
            return "Invalid email or password";
        }
        return "Logged in successfully!";
    }

    @PostMapping("/v1/login")
    public String loginV1User(@RequestBody LoginReqDto loginReqDto){
        Boolean loggedInUser = userService.loginV1(loginReqDto);
        if(!loggedInUser){
            return "Invalid email or password";
        }
        return "Logged in successfully!";
    }



}
