package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.*;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.service.AdminServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    private final AdminServices adminService;

    public AdminController(AdminServices adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAllUsers")
    @Operation(
            summary = "Retrieve all users",
            description = "Retrieves a list of all registered users from the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"All the users are:\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
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
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> usersList = adminService.getAllUsers();
        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUsersLessThanBal/{amount}")
    @Operation(
            summary = "Retrieve users with low balance",
            description = "Retrieves users whose bank account balance is less than the specified amount.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"All the users are:\","
                                                    + "\"data\":[]"
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
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUsersLessThanBal(
            @PathVariable Double amount) {

        List<UserResponseDto> usersList = adminService.getUsersLessThanBal(amount);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/v1/getUsersThanBal/{amount}")
    @Operation(
            summary = "Retrieve users with low balance (V1)",
            description = "Retrieves users whose bank account balance is less than the specified amount using V1 implementation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"All the users are:\","
                                                    + "\"data\":[]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Users not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Users not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUsersLessThanBalV1(
            @Parameter(
                    description = "amount",
                    example = "2000"
            )
            @PathVariable Double amount) {

        List<UserResponseDto> usersList = adminService.getUsersLessThanBalV1(amount);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUsersHavingSaving")
    @Operation(
            summary = "Retrieve users with savings accounts",
            description = "Retrieves all users who have at least one savings account.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users have a savings account.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users with savings accounts retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users with savings accounts found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users with savings accounts found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUsersHavingSaving() {

        List<UserResponseDto> usersList = adminService.getUsersHavingSaving();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUsersHavingCurrent")
    @Operation(
            summary = "Retrieve users with current accounts",
            description = "Retrieves all users who have at least one current account.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users have a current account.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users with current accounts retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users with current accounts found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users with current accounts found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUsersHavingCurrent() {

        List<UserResponseDto> usersList = adminService.getUsersHavingCurrent();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getOldAgeUsers")
    @Operation(
            summary = "Retrieve senior users",
            description = "Retrieves all users whose age qualifies them as senior users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Senior users retrieved successfully. Returns an empty list if no senior users are found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Senior users retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":65"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No senior users found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No senior users found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getOldAgeUsers() {

        List<UserResponseDto> usersList = adminService.getOldAgeUsers();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/v1/getOldAgeUsers")
    @Operation(
            summary = "Retrieve senior users (V1)",
            description = "Retrieves senior users using the V1 implementation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no senior users are found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{\n"
                                                    + "  \"status\": \"OK\",\n"
                                                    + "  \"message\": \"Senior users retrieved successfully\",\n"
                                                    + "  \"data\": [\n"
                                                    + "    {\n"
                                                    + "      \"id\": 37,\n"
                                                    + "      \"name\": \"Farhad\",\n"
                                                    + "      \"email\": \"farhad@gmail.com\",\n"
                                                    + "      \"age\": 65\n"
                                                    + "    }\n"
                                                    + "  ]\n"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No senior users found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No senior users found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getOldAgeUsersV1() {

        List<UserResponseDto> usersList = adminService.getOldAgeUsersV1();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUsersByEmail/{email}")
    @Operation(
            summary = "Retrieve user by email",
            description = "Retrieves a user using the specified email address.",
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
                                                    + "\"message\":\"User retrieved successfully\","
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
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> getUsersByEmail(
            @Parameter(
                    description = "email",
                    example = "vishal@mail.com"
            )
            @PathVariable String email) {

        UserResponseDto user = adminService.getUsersByEmail(email);

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "Here the user: " + user
        );
    }

    @GetMapping("/getAllUsersEmail")
    @Operation(
            summary = "Retrieve all user emails",
            description = "Retrieves the email addresses of all registered users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Emails retrieved successfully. Returns an empty list if no users are registered.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "[\"farhad@gmail.com\", \"chote@gmail.com\"]"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Users not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public List<String> getAllUsersEmail() {
        return adminService.getAllUsersEmail();
    }

    @GetMapping("/getTotalNoAcc")
    @Operation(
            summary = "Retrieve total number of accounts",
            description = "Retrieves the total number of bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Total number of accounts retrieved successfully. Returns 0 if no accounts exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Total number of accounts retrieved successfully\","
                                                    + "\"data\":9"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<Integer> getTotalNoAcc() {

        return new GenericDto<Integer>(
                HttpStatus.OK,
                "The total number of accounts in bank are: ",
                adminService.getTotalNoAcc()
        );
    }

    @GetMapping("/v1/getTotalNoAcc")
    @Operation(
            summary = "Retrieve total number of accounts (V1)",
            description = "Retrieves the total number of bank accounts using V1 implementation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Total number of accounts retrieved successfully. Returns 0 if no accounts exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Total number of accounts retrieved successfully\","
                                                    + "\"data\":9"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<Long> getTotalNoAccV1() {

        return new GenericDto<Long>(
                HttpStatus.OK,
                "The total number of accounts in bank are: ",
                adminService.getTotalNoAccV1()
        );
    }

    @GetMapping("/getTotalMoney")
    @Operation(
            summary = "Retrieve total money in bank",
            description = "Retrieves the total balance held across all bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Total money retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Total money retrieved successfully\","
                                                    + "\"data\":1234567.50"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<Double> getTotalMoney() {

        return new GenericDto<Double>(
                HttpStatus.OK,
                "The total in money in bank is: ",
                adminService.getTotalMoneyInBank()
        );
    }

    @GetMapping("/getUserWithMaxBal")
    @Operation(
            summary = "Retrieve user with maximum balance",
            description = "Retrieves the user associated with the account having the highest balance.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User with maximum balance retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"User with maximum balance retrieved successfully\","
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
                            description = "No user found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No user found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> getUserWithMaxBal() {

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "The user with maximum balance is: ",
                adminService.getUserWithMaxBal()
        );
    }

    @GetMapping("/getUsersOverCertainBal/{amt}")
    @Operation(
            summary = "Retrieve users above a specified balance",
            description = "Retrieves users whose bank account balance is greater than the specified amount.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users meet the specified balance criteria.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users above the specified balance retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found above the specified balance",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users found above the specified balance\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUserWithSpecificBal(
            @Parameter(
                    description = "amount",
                    example = "20000"
            )
            @PathVariable Double amt) {

        List<UserResponseDto> usersList = adminService.getUserOverSpecificBal(amt);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/v1/getUsersOverCertainBal/{amt}")
    @Operation(
            summary = "Retrieve users above a specified balance (V1)",
            description = "Retrieves users whose bank account balance is greater than the specified amount using V1 implementation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users meet the specified balance criteria.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users above the specified balance retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found above the specified balance",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users found above the specified balance\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUserWithSpecificBalV1(
            @Parameter(
                    description = "amount",
                    example = "20000"
            )
            @PathVariable Double amt) {

        List<UserResponseDto> usersList = adminService.getUserOverSpecificBalV1(amt);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUsersAboveSomeAge/{age}")
    @Operation(
            summary = "Retrieve users above specified age",
            description = "Retrieves users whose age is greater than the specified age.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users meet the specified age criteria.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users above the specified age retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found above the specified age",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users found above the specified age\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUserAboveAge(
            @Parameter(
                    description = "age",
                    example = "27"
            )
            @PathVariable Integer age) {

        List<UserResponseDto> usersList = adminService.getUserAboveAge(age);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/v1/getUsersAboveSomeAge/{age}")
    @Operation(
            summary = "Retrieve users above specified age (V1)",
            description = "Retrieves users whose age is greater than the specified age using V1 implementation.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users meet the specified age criteria.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users above the specified age retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found above the specified age",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users found above the specified age\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUserAboveAgeV1(
            @Parameter(
                    description = "age",
                    example = "27"
            )
            @PathVariable Integer age) {

        List<UserResponseDto> usersList = adminService.getUserAboveAgeV1(age);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getUserByAccNo/{accNo}")
    @Operation(
            summary = "Retrieve user by account number",
            description = "Retrieves the user associated with the specified account number.",
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
                                                    + "\"message\":\"User retrieved successfully\","
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
                            description = "Account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> getUserByAccNo(
            @Parameter(
                    description = "account number",
                    example = "62"
            )
            @PathVariable Long accNo) {

        UserResponseDto dto = adminService.getUserByAccNo(accNo);

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "All the users are: ",
                dto
        );
    }

    @GetMapping("/v1/getUserByAccNo/{accNo}")
    @Operation(
            summary = "Retrieve user by account number (V1)",
            description = "Retrieves the user associated with the specified account number using V1 implementation.",
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
                                                    + "\"message\":\"User retrieved successfully\","
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
                            description = "Account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> getUserByAccNoV1(
            @Parameter(
                    description = "account number",
                    example = "62"
            )
            @PathVariable Long accNo) {

        UserResponseDto dto = adminService.getUserByAccNoV1(accNo);

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "All the users are: ",
                dto
        );
    }

    @GetMapping("/getUsersBwAge")
    @Operation(
            summary = "Retrieve users within age range",
            description = "Retrieves users whose age falls within the specified range.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users fall within the specified age range.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users within the specified age range retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid age range",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Bad Request",
                                            value = "{"
                                                    + "\"status\":\"BAD_REQUEST\","
                                                    + "\"message\":\"Invalid age range\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No users found within the specified age range",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"No users found within the specified age range\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> getUserBwAge(
            @RequestBody AgeReqDto ageReqDto) {

        List<UserResponseDto> usersList = adminService.getUserBwAge(ageReqDto);

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.OK,
                "All the users are: ",
                usersList
        );
    }

    @GetMapping("/getAllBankAccounts")
    @Operation(
            summary = "Retrieve all bank accounts",
            description = "Retrieves all bank accounts registered in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bank accounts retrieved successfully. Returns an empty list if no bank accounts are registered.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "[{"
                                                    + "\"accNo\":41,"
                                                    + "\"balance\":24567,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}]"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public List<BankAccountResponseDto> getAllBankAccounts() {
        System.out.println(adminService.getAllBankAccounts());
        return adminService.getAllBankAccounts();
    }

    @DeleteMapping("/deleteAccount/{id}")
    @Operation(
            summary = "Delete bank account",
            description = "Deletes the bank account associated with the specified account ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Account deleted successfully\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> deleteAccount(
            @PathVariable Long id) {

        Boolean isDeleted = adminService.deleteAccountById(id);

        return new GenericDto<BankAccountResponseDto>(
                HttpStatus.OK,
                "Account deleted!"
        );
    }

    @GetMapping("/getUsersWithMulAcc")
    @Operation(
            summary = "Retrieve users with multiple accounts",
            description = "Retrieves users who have more than one bank account.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users have multiple accounts.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{\n"
                                                    + "  \"status\": \"OK\",\n"
                                                    + "  \"message\": \"Users with multiple accounts retrieved successfully\",\n"
                                                    + "  \"data\": [\n"
                                                    + "    {\n"
                                                    + "      \"id\": 37,\n"
                                                    + "      \"name\": \"Farhad\",\n"
                                                    + "      \"email\": \"farhad@gmail.com\",\n"
                                                    + "      \"age\": 34\n"
                                                    + "    }\n"
                                                    + "  ]\n"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> usersWithMulAcc() {

        List<UserResponseDto> usersWithMulAcc =
                adminService.getUsersWithMulAcc();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.ACCEPTED,
                "users with multiple accounts",
                usersWithMulAcc
        );
    }

    @GetMapping("/getUsersHavingMoreThan100000")
    @Operation(
            summary = "Retrieve users with balance greater than 100000",
            description = "Retrieves users whose total balance across accounts is greater than 100000.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users retrieved successfully. Returns an empty list if no users meet the balance criteria.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Users with balance greater than 100000 retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"id\":37,"
                                                    + "\"name\":\"Farhad\","
                                                    + "\"email\":\"farhad@gmail.com\","
                                                    + "\"age\":34"
                                                    + "}]"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<UserResponseDto>> usersHavingBalanceGreaterThan100000() {

        List<UserResponseDto> users =
                adminService.getUsersHavingTotalBalGreaterThan100000();

        return new GenericDto<List<UserResponseDto>>(
                HttpStatus.ACCEPTED,
                "users with balance greater than 100000",
                users
        );
    }

    @GetMapping("/getAvgBalOfAllAcc")
    @Operation(
            summary = "Retrieve average account balance",
            description = "Retrieves the average balance across all bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Average balance retrieved successfully. Returns 0.0 if no accounts exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Average balance retrieved successfully\","
                                                    + "\"data\":24567.50"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BalanceResDto> avgBalOfAllAcc() {

        BalanceResDto dto = adminService.getAvgBalOfAcc();

        return new GenericDto<BalanceResDto>(
                HttpStatus.OK,
                "Average of balance of all accounts",
                dto
        );
    }

    @GetMapping("/getUsersWithBalGreaterThanAvgBal")
    @Operation(
            summary = "Retrieve accounts above average balance",
            description = "Retrieves bank accounts whose balance is greater than the average balance across all bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Accounts retrieved successfully. Returns an empty list if no accounts have a balance greater than the average.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Accounts with balance greater than average retrieved successfully\","
                                                    + "\"data\":[{"
                                                    + "\"accNo\":41,"
                                                    + "\"balance\":45000,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}]"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<List<BankAccountResponseDto>> getUsersWithBalGreaterThanAvgBal() {

        List<BankAccountResponseDto> listOfAcc =
                adminService.getUsersWithBalGreaterThanAvgBal();

        return new GenericDto<List<BankAccountResponseDto>>(
                HttpStatus.OK,
                "Users with balance greater than average of balance of all accounts",
                listOfAcc
        );
    }

    @PutMapping("/setInterestLevel/{interest}")
    @Operation(
            summary = "Update interest rate",
            description = "Updates the interest rate applied to bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Interest rate updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Interest rate updated successfully\","
                                                    + "\"data\":{"
                                                    + "\"interestRate\":3.5"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid interest rate",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Bad Request",
                                            value = "{"
                                                    + "\"status\":\"BAD_REQUEST\","
                                                    + "\"message\":\"Invalid interest rate\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<InterestResponseDto> setInterest(
            @Parameter(
                    description = "interest",
                    example = "3.5"
            )
            @PathVariable Double interest) {

        InterestResponseDto intDto =
                adminService.setInterestLevel(interest);

        return new GenericDto<InterestResponseDto>(
                HttpStatus.OK,
                "interest level updated",
                intDto
        );
    }

    @PutMapping("/setOverDraftLimit/{odl}")
    @Operation(
            summary = "Update overdraft limit",
            description = "Updates the overdraft limit for bank accounts.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Overdraft limit updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Overdraft limit updated successfully\","
                                                    + "\"data\":{"
                                                    + "\"overdraftLimit\":12000"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid overdraft limit",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Bad Request",
                                            value = "{"
                                                    + "\"status\":\"BAD_REQUEST\","
                                                    + "\"message\":\"Invalid overdraft limit\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<OverDraftLimitRes> setOverDraftLimit(
            @Parameter(
                    description = "overdraft limit amount",
                    example = "12000"
            )
            @PathVariable Double odl) {

        OverDraftLimitRes odlRes =
                adminService.setOverDraftLimit(odl);

        return new GenericDto<OverDraftLimitRes>(
                HttpStatus.OK,
                "overdraft limit updated",
                odlRes
        );
    }

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update user",
            description = "Updates the user details associated with the specified user ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"User updated successfully\","
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
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> updateUser(
            @Parameter(
                    description = "user id",
                    example = "20"
            )
            @PathVariable Long id,
            @RequestBody UpdateUserReq updateUserReq) {

        UserResponseDto updatedUser =
                adminService.updateUser(id, updateUserReq);

        return new GenericDto<UserResponseDto>(
                HttpStatus.ACCEPTED,
                "updated successfully",
                updatedUser
        );
    }

    @PatchMapping("/update/{id}")
    @Operation(
            summary = "Partially update user",
            description = "Partially updates the details of an existing user. One or more fields can be provided in the request body; only the provided fields will be updated.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User partially updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"User partially updated successfully\","
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
                            responseCode = "400",
                            description = "Invalid update request",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid Email",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Invalid email address\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Age",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Invalid age\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "No Fields Provided",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"At least one field must be provided for update\""
                                                            + "}"
                                            )
                                    }
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> updateDetailsUser(
            @Parameter(
                    description = "user id",
                    example = "20"
            )
            @PathVariable Long id,
            @RequestBody UpdateUserReq updateUserReq) {

        UserResponseDto updatedUser =
                adminService.updateDetailsUser(id, updateUserReq);

        return new GenericDto<UserResponseDto>(
                HttpStatus.ACCEPTED,
                "updated successfully",
                updatedUser
        );
    }

    @DeleteMapping("/deleteUserById/{id}")
    @Operation(
            summary = "Delete user",
            description = "Deletes the user associated with the specified user ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"User deleted successfully\""
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
                                            name = "Not Found",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\""
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Internal Server Error",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Some error occurred\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<UserResponseDto> deleteUser(
            @Parameter(
                    description = "user id",
                    example = "20"
            )
            @PathVariable Long id) {

        Boolean isUserDel = adminService.deleteUserById(id);

        return new GenericDto<UserResponseDto>(
                HttpStatus.OK,
                "User deleted!"
        );
    }
}