package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User APIs", description = "register, login, update")
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
                                    ),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"CREATED\","
                                                    + "\"message\":\"user registered\","
                                                    + "\"data\":{"
                                                    + "\"id\":47,"
                                                    + "\"name\":\"Hitesh\","
                                                    + "\"email\":\"hitesh@mail.com\","
                                                    + "\"age\":44"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\","
                                                    + "\"data\":null"
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public GenericDto<UserResponseDto> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration details",
                    required = true
            )
            @RequestBody RegisterReqDto registerReqDto) {

        UserResponseDto userDto = userService.createUser(registerReqDto);

        return new GenericDto<UserResponseDto>(
                HttpStatus.CREATED,
                "user registered",
                userDto
        );
    }


    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "User gets logged in by providing account number, email and password.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Login successful\","
                                                    + "\"data\":{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "User Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Invalid credentials!\","
                                                    + "\"data\":null"
                                                    + "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\","
                                                    + "\"data\":null"
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> loginUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login details containing account number, email and password",
                    required = true
            )
            @RequestBody LoginReqDto loginReqDto) {

        UserResponseDto loggedInUser = userService.login(loginReqDto);

        return new GenericDto<UserResponseDto>(
                HttpStatus.ACCEPTED,
                "Login successful",
                loggedInUser
        );
    }


    @GetMapping("/getMyDetails/{accNo}")
    @Operation(
            summary = "Retrieve user details",
            description = "Retrieves user details by providing account number.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Here are your details:\","
                                                    + "\"data\":{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "User Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\","
                                                    + "\"data\":null"
                                                    + "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\","
                                                    + "\"data\":null"
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> getMyDetails(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo) {

        UserResponseDto user = userService.getUserDetails(accNo);

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "Here are your details: ",
                user
        );
    }
}