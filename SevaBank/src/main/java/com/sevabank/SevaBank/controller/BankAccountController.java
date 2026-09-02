package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.BalanceReq;
import com.sevabank.SevaBank.dto.response.BalanceResDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.InterestResponseDto;
import com.sevabank.SevaBank.service.BankServices;
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
@RequestMapping("/api/bankAccount")
@Tag(
        name = "Bank APIs",
        description = "create, deposit, withdraw, balance, check balance, interest"
)
public class BankAccountController {

    BankServices bankAccountService;

    public BankAccountController(BankServices bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @PostMapping
    @Operation(
            summary = "Create a bank account",
            description = "Creates a new bank account for an existing user using the provided account details.",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bank account creation details",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateBankAccountRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Request",
                                            summary = "Valid bank account creation request",
                                            value = "{"
                                                    + "\"userId\":37,"
                                                    + "\"balance\":24567.0,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Current Account",
                                            summary = "Create a current bank account",
                                            value = "{"
                                                    + "\"userId\":37,"
                                                    + "\"balance\":50000.0,"
                                                    + "\"accountType\":\"CURRENT\""
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid Request",
                                            summary = "Invalid account creation request",
                                            value = "{"
                                                    + "\"userId\":null,"
                                                    + "\"balance\":-5000.0,"
                                                    + "\"accountType\":null"
                                                    + "}"
                                    )
                            }
                    )
            ),

            responses = {

                    @ApiResponse(
                            responseCode = "201",
                            description = "Bank account created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Bank account created successfully",
                                            value = "{"
                                                    + "\"status\":\"CREATED\","
                                                    + "\"message\":\"Account created\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"balance\":24567.0,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid account details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid User ID",
                                                    summary = "User ID is missing or invalid",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"User ID is required\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Balance",
                                                    summary = "Balance cannot be negative",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Balance cannot be negative\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Account Type",
                                                    summary = "Account type is missing or invalid",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Invalid account type\""
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
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "User Not Found",
                                            summary = "No user exists with the provided user ID",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"User not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while creating the account",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> createBankAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bank Creation details",
                    required = true
            )
            @RequestBody CreateBankAccountRequest bankReq) {

        BankAccountResponseDto bankDto =
                bankAccountService.createBankAccount(bankReq);

        return new GenericDto<BankAccountResponseDto>(
                HttpStatus.CREATED,
                "Account created",
                bankDto
        );
    }


    @PostMapping("/deposit/{accNo}")
    @Operation(
            summary = "Deposit money",
            description = "Deposits money into a bank account using the account number and deposit amount.",

            parameters = {
                    @Parameter(
                            name = "accNo",
                            description = "Bank account number into which the amount will be deposited",
                            required = true,
                            example = "65"
                    )
            },

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Amount to be deposited",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BalanceReq.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Request",
                                            summary = "Valid deposit amount",
                                            value = "{"
                                                    + "\"amount\":1000.0"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid Amount",
                                            summary = "Invalid deposit amount",
                                            value = "{"
                                                    + "\"amount\":-1000.0"
                                                    + "}"
                                    )
                            }
                    )
            ),

            responses = {

                    @ApiResponse(
                            responseCode = "202",
                            description = "Money deposited successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Amount deposited successfully",
                                            value = "{"
                                                    + "\"status\":\"ACCEPTED\","
                                                    + "\"message\":\"Amount deposited!\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"balance\":25567.0,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid deposit amount",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid Amount",
                                                    summary = "Amount must be greater than zero",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Amount must be greater than zero\""
                                                            + "}"
                                            )
                                    }
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Bank account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Account Not Found",
                                            summary = "No account exists with the provided account number",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while depositing money",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> deposit(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Amount to be deposited",
                    required = true
            )
            @RequestBody BalanceReq balanceReq) {

        BankAccountResponseDto depositedAccount =
                bankAccountService.depositInAccount(
                        accNo,
                        balanceReq.getBalance()
                );

        return new GenericDto<BankAccountResponseDto>(
                HttpStatus.ACCEPTED,
                "Amount deposited!",
                depositedAccount
        );
    }


    @PostMapping("/withdraw/{accNo}")
    @Operation(
            summary = "Withdraw money",
            description = "Withdraws money from a bank account using the account number and withdrawal amount.",

            parameters = {
                    @Parameter(
                            name = "accNo",
                            description = "Bank account number from which the amount will be withdrawn",
                            required = true,
                            example = "65"
                    )
            },

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Amount to be withdrawn",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BalanceReq.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid Request",
                                            summary = "Valid withdrawal amount",
                                            value = "{"
                                                    + "\"amount\":1000.0"
                                                    + "}"
                                    ),
                                    @ExampleObject(
                                            name = "Insufficient Balance",
                                            summary = "Withdrawal amount exceeds available balance",
                                            value = "{"
                                                    + "\"amount\":50000.0"
                                                    + "}"
                                    )
                            }
                    )
            ),

            responses = {

                    @ApiResponse(
                            responseCode = "202",
                            description = "Money withdrawn successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Amount withdrawn successfully",
                                            value = "{"
                                                    + "\"status\":\"ACCEPTED\","
                                                    + "\"message\":\"Amount withdrawn\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"balance\":23567.0,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid withdrawal request or insufficient balance",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Insufficient Balance",
                                                    summary = "Account does not have sufficient balance",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Insufficient balance\""
                                                            + "}"
                                            ),
                                            @ExampleObject(
                                                    name = "Invalid Amount",
                                                    summary = "Withdrawal amount must be greater than zero",
                                                    value = "{"
                                                            + "\"status\":\"BAD_REQUEST\","
                                                            + "\"message\":\"Amount must be greater than zero\""
                                                            + "}"
                                            )
                                    }
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Bank account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Account Not Found",
                                            summary = "No account exists with the provided account number",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while withdrawing money",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> withdraw(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Amount to be withdrawn",
                    required = true
            )
            @RequestBody BalanceReq balanceReq) {

        BankAccountResponseDto withdrawnInAccount =
                bankAccountService.withdrawInAccount(
                        accNo,
                        balanceReq.getBalance()
                );

        return new GenericDto<BankAccountResponseDto>(
                HttpStatus.ACCEPTED,
                "Amount withdrawn",
                withdrawnInAccount
        );
    }


    @GetMapping("/balance/{accNo}")
    @Operation(
            summary = "Check bank account balance",
            description = "Checks the current balance of a bank account using the account number.",

            parameters = {
                    @Parameter(
                            name = "accNo",
                            description = "Bank account number whose balance needs to be checked",
                            required = true,
                            example = "65"
                    )
            },

            responses = {

                    @ApiResponse(
                            responseCode = "202",
                            description = "Account balance retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Current account balance",
                                            value = "{"
                                                    + "\"status\":\"ACCEPTED\","
                                                    + "\"message\":\"Balance retrieved successfully\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"balance\":24567.0"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Bank account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Account Not Found",
                                            summary = "No account exists with the provided account number",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while checking balance",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BalanceResDto> checkBalance(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo) {

        BalanceResDto balance = bankAccountService.checkBalance(accNo);

        return new GenericDto<BalanceResDto>(
                HttpStatus.ACCEPTED,
                "",
                balance
        );
    }


    @GetMapping("/interest/{accNo}")
    @Operation(
            summary = "Check account interest",
            description = "Checks the interest earned on a bank account using the account number.",

            parameters = {
                    @Parameter(
                            name = "accNo",
                            description = "Bank account number whose interest needs to be checked",
                            required = true,
                            example = "65"
                    )
            },

            responses = {

                    @ApiResponse(
                            responseCode = "202",
                            description = "Account interest retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Account interest retrieved successfully",
                                            value = "{"
                                                    + "\"status\":\"ACCEPTED\","
                                                    + "\"message\":\"Interest retrieved successfully\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"interest\":1105.52"
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Bank account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Account Not Found",
                                            summary = "No account exists with the provided account number",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while checking interest",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<InterestResponseDto> checkInterest(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo) {

        InterestResponseDto interest =
                bankAccountService.calculateInterest(accNo);

        if (interest == null) {
            return new GenericDto<InterestResponseDto>(
                    HttpStatus.NOT_FOUND,
                    "account not found"
            );
        }

        return new GenericDto<InterestResponseDto>(
                HttpStatus.ACCEPTED,
                "",
                interest
        );
    }


    @GetMapping("/getBankAccountDetails/{accNo}")
    @Operation(
            summary = "Retrieve bank account details",
            description = "Retrieves the bank account details of the logged-in user using the account number.",

            parameters = {
                    @Parameter(
                            name = "accNo",
                            description = "Bank account number whose details need to be retrieved",
                            required = true,
                            example = "65"
                    )
            },

            responses = {

                    @ApiResponse(
                            responseCode = "200",
                            description = "Bank account details retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Success",
                                            summary = "Bank account details retrieved successfully",
                                            value = "{"
                                                    + "\"status\":\"OK\","
                                                    + "\"message\":\"Bank account details retrieved successfully\","
                                                    + "\"data\":{"
                                                    + "\"accNo\":65,"
                                                    + "\"balance\":24567.0,"
                                                    + "\"accountType\":\"SAVING\""
                                                    + "}"
                                                    + "}"
                                    )
                            )
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Bank account not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GenericDto.class),
                                    examples = @ExampleObject(
                                            name = "Account Not Found",
                                            summary = "No account exists with the provided account number",
                                            value = "{"
                                                    + "\"status\":\"NOT_FOUND\","
                                                    + "\"message\":\"Account not found\""
                                                    + "}"
                                    )
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
                                            summary = "Unexpected error occurred while retrieving account details",
                                            value = "{"
                                                    + "\"status\":\"INTERNAL_SERVER_ERROR\","
                                                    + "\"message\":\"Internal server error\""
                                                    + "}"
                                    )
                            )
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> getBankAccountDetails(
            @Parameter(
                    description = "Account number",
                    example = "65"
            )
            @PathVariable Long accNo) {

        BankAccountResponseDto bankAccount =
                bankAccountService.getBankAccountDetails(accNo);

        return new GenericDto<BankAccountResponseDto>(
                HttpStatus.OK,
                "Here are your bank details: ",
                bankAccount
        );
    }
}