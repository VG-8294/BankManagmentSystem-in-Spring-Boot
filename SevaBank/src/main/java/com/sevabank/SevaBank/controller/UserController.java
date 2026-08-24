package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name="User APIs", description = "register, login, update")
public class UserController {

    private final UserServices userService;

    public UserController(UserServices userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user in the system using the provided user details."
    )
    @ResponseStatus(HttpStatus.CREATED)
    public GenericDto<UserResponseDto> createUser(@RequestBody RegisterReqDto registerReqDto){
        UserResponseDto userDto =  userService.createUser(registerReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.CREATED, "user registered", userDto);
    }


    @PostMapping("/login")
    @Operation(
            summary = "Logins user",
            description = "User gets logged in by providing account number, email and password"
    )
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

    @GetMapping("/getMyDetails/{accNo}")
    @Operation(
            summary = "Retrieves user details",
            description = "Retrieves user detail by providing account number"
    )
    public GenericDto<UserResponseDto> getMyDeatils(@PathVariable Long accNo){
        UserResponseDto user = userService.getUserDetails(accNo);
        return new GenericDto<UserResponseDto>(HttpStatus.OK, "Here are your details: ", user);
    }


}
