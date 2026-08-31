package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.*;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.service.AdminServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            description = "Retrieves a list of all registered users from the system."
            //Checking Jira
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
            description = "Retrieves users whose bank account balance is less than the specified amount."
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
            description = "Retrieves users whose bank account balance is less than the specified amount using the version 1 implementation."
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
            description = "Retrieves all users who have at least one savings account."
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
            description = "Retrieves all users who have at least one current account."
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
            description = "Retrieves users who satisfy the configured age criteria for senior users."
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
            description = "Retrieves users who satisfy the configured age criteria using the version 1 implementation."
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
            description = "Retrieves a user using the specified email address."
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
            description = "Retrieves the email addresses of all registered users."
    )
    public List<String> getAllUsersEmail() {
        return adminService.getAllUsersEmail();
    }

    @GetMapping("/getTotalNoAcc")
    @Operation(
            summary = "Retrieve total number of accounts",
            description = "Retrieves the total number of bank accounts registered in the system."
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
            description = "Retrieves the total number of bank accounts using the version 1 implementation."
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
            description = "Retrieves the total balance held across all bank accounts."
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
            description = "Retrieves the user associated with the bank account having the highest balance."
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
            description = "Retrieves users whose bank account balance is greater than the specified amount."
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
            description = "Retrieves users whose bank account balance is greater than the specified amount using the version 1 implementation."
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
            description = "Retrieves users whose age is greater than the specified age."
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
            description = "Retrieves users whose age is greater than the specified age using the version 1 implementation."
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
            description = "Retrieves the user associated with the specified bank account number."
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
            description = "Retrieves the user associated with the specified bank account number using the version 1 implementation."
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
            summary = "Retrieve users within an age range",
            description = "Retrieves users whose age falls within the specified age range."
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
            description = "Retrieves a list of all bank accounts registered in the system."
    )
    public List<BankAccountResponseDto> getAllBankAccounts() {
        System.out.println(adminService.getAllBankAccounts());
        return adminService.getAllBankAccounts();
    }

    @DeleteMapping("/deleteAccount/{id}")
    @Operation(
            summary = "Delete bank account",
            description = "Deletes the bank account associated with the specified account ID."
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
            description = "Retrieves users who have more than one bank account."
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
            summary = "Retrieve users with total balance above 100000",
            description = "Retrieves users whose combined balance across their bank accounts is greater than 100000."
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
            description = "Retrieves the average balance across all bank accounts."
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
            description = "Retrieves bank accounts whose balance is greater than the average balance of all bank accounts."
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
            description = "Updates the interest rate applied to the bank accounts."
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
            description = "Updates the overdraft limit for bank accounts."
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
            description = "Updates the user details associated with the specified user ID."
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
            description = "Updates selected user details associated with the specified user ID."
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
            description = "Deletes the user associated with the specified user ID."
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