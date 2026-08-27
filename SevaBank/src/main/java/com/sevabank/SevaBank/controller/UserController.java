package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            description = "Registers a new user in the system using the provided user details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                implementation = GenericDto.class
                                )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }

    )
    @ResponseStatus(HttpStatus.CREATED)
    public GenericDto<UserResponseDto> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration details",
            required = true
    ) @RequestBody RegisterReqDto registerReqDto){
        UserResponseDto userDto =  userService.createUser(registerReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.CREATED, "user registered", userDto);
    }


    @PostMapping("/login")
    @Operation(
            summary = "Logins user",
            description = "User gets logged in by providing account number, email and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public GenericDto<UserResponseDto> loginUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration details",
            required = true
    ) @RequestBody LoginReqDto loginReqDto){
        UserResponseDto loggedInUser = userService.login(loginReqDto);
        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfull", loggedInUser);
    }

//    @PostMapping("/v1/login")
//    public GenericDto<UserResponseDto> loginV1User(@RequestBody LoginReqDto loginReqDto){
//        System.out.println("In User controller");
//        UserResponseDto loggedInUser = userService.loginV1(loginReqDto);
//        return new GenericDto<UserResponseDto>(HttpStatus.ACCEPTED, "Login successfull", loggedInUser);
//    }

    @GetMapping("/getMyDetails/{accNo}")
    @Operation(
            summary = "Retrieves user details",
            description = "Retrieves user detail by providing account number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User details retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }

    )
    public GenericDto<UserResponseDto> getMyDetails(@Parameter(
            description = "Account number",
            example = "65"
    ) @PathVariable Long accNo){
        UserResponseDto user = userService.getUserDetails(accNo);
        return new GenericDto<UserResponseDto>(HttpStatus.OK, "Here are your details: ", user);
    }


}
