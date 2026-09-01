package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfully", loggedInUser);
    }
//
//    @PostMapping("/v1/login")
//    public GenericDto<UserResponseDto> loginV1User(@RequestBody LoginReqDto loginReqDto){
//        System.out.println("In User controller");
//        UserResponseDto loggedInUser = userService.loginV1(loginReqDto);
//        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfull", loggedInUser);
//    }
//
    @PutMapping("/update/{id}")
    public GenericDto<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserReq updateUserReq){
        UserResponseDto updatedUser = userService.updateUser(id, updateUserReq);
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "updated successfully", updatedUser);
    }

    @PatchMapping("/update/{id}")
    public GenericDto<UserResponseDto> updateDetailsUser(@PathVariable Long id, @RequestBody UpdateUserReq updateUserReq){
        UserResponseDto updatedUser = userService.updateDetailsUser(id, updateUserReq);
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "updated successfully", updatedUser);
    }


}
