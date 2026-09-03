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

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterReqDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Request",
                                            summary = "Valid user registration request",
                                            value = "{"
                                                    + "\"name\":\"Hitesh\","
                                                    + "\"email\":\"hitesh@mail.com\","
                                                    + "\"password\":\"Hitesh@123\","
                                                    + "\"age\":44"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid Request",
                                            summary = "Request containing invalid field values",
                                            value = "{"
                                                    + "\"name\":\"\","
                                                    + "\"email\":\"invalid-email\","
                                                    + "\"password\":\"123\","
                                                    + "\"age\":15"
                                                    + "}"
                                    )
                            }
                    )
            ),

            responses = {

                    /*
                     * 201 - User successfully registered
                     */
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
                                            summary = "User registered successfully",
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

                    /*
                     * 400 - Validation failure
                     */
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid registration data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Required Field Missing",
                                                    summary = "One or more required fields are missing",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Name is required\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Email",
                                                    summary = "Email format is invalid",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Invalid email format\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Password",
                                                    summary = "Password does not satisfy validation rules",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Password must contain at least 8 characters\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Age",
                                                    summary = "Age is outside the allowed range",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Age must be greater than or equal to 18\""
                                                            + "}"
                                            )
                                    }
                            )
                    ),

                    /*
                     * 409 - Duplicate email
                     */
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already exists",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "Email Already Exists",
                                            summary = "A user with the provided email already exists",
                                            value = "{"
                                                    + "\"status\":\"CONFLICT\","
                                                    + "\"message\":\"Email already registered\""
                                                    + "}"
                                    )
                            )
                    ),

                    /*
                     * 500 - Internal server error
                     */
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
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
            description = "Authenticates a user using account number, email and password.",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginReqDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Login",
                                            summary = "Successful login request",
                                            value = "{"
                                                    + "\"accountNo\": 37,"
                                                    + "\"email\": \"farhad@gmail.com\","
                                                    + "\"password\": \"123456\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid Login",
                                            summary = "Invalid credentials",
                                            value = "{"
                                                    + "\"accountNo\": 37,"
                                                    + "\"email\": \"wrong@gmail.com\","
                                                    + "\"password\": \"wrong\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid Account number",
                                            summary = "Account number not found",
                                            value = "{"
                                                    + "\"accountNo\": 78,"
                                                    + "\"email\": \"farhad@gmail.com\","
                                                    + "\"password\": \"123456\""
                                                    + "}"
                                    )
                            }
                    )
            ),

            responses = {

                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Login Successful",
                                            value = "{"
                                                    + "\"status\": \"OK\","
                                                    + "\"message\": \"Login successful\","
                                                    + "\"data\": {"
                                                    + "\"id\": 37,"
                                                    + "\"name\": \"Farhad\","
                                                    + "\"email\": \"farhad@gmail.com\","
                                                    + "\"age\": 34"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found / invalid credentials",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid Credentials",
                                                    value = "{"
                                                            + "\"status\": \"NOT_FOUND\","
                                                            + "\"message\": \"Invalid credentials!\""
                                                            + "}"
                                            ),

                                            @ExampleObject(
                                                    name = "Account Not Found",
                                                    value = "{"
                                                            + "\"status\": \"NOT_FOUND\","
                                                            + "\"message\": \"Bank account not found!\""
                                                            + "}"
                                            )
                                    }
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\": \"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\": \"Internal server error\""
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