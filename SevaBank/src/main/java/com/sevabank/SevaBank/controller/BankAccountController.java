package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.BalanceReq;
import com.sevabank.SevaBank.dto.response.BalanceResDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.InterestResponseDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.service.BankServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bankAccount")
@Tag(name="Bank APIs", description = "create, deposit, withdraw, balance, check balance, interest")
public class BankAccountController {

    BankServices bankAccountService;

    public BankAccountController(BankServices bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    @Operation(
            summary = "Creates bank account",
            description = "Creates bank account by providing basic details",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Bank account created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Wrong input given"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> createBankAccount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Bank Creation details",
            required = true
    ) @RequestBody CreateBankAccountRequest bankReq){
        BankAccountResponseDto bankDto =  bankAccountService.createBankAccount(bankReq);
        return new GenericDto<BankAccountResponseDto>(HttpStatus.CREATED, "Account created", bankDto);

    }

    @PostMapping("/deposit/{accNo}")
    @Operation(
            summary = "Deposits money",
            description = "Deposit money in user account by providing user account number",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Money deposited successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }

    )
    public GenericDto<BankAccountResponseDto> deposit(@Parameter(
            description = "Account number",
            example="65"
    ) @PathVariable Long accNo, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Amount to be deposited",
            required = true
    ) @RequestBody BalanceReq balanceReq){
        BankAccountResponseDto depositedAccount = bankAccountService.depositInAccount(accNo, balanceReq.getBalance());

        return new GenericDto<BankAccountResponseDto>(HttpStatus.ACCEPTED, "Amount deposited!", depositedAccount);
    }

    @PostMapping("/withdraw/{accNo}")
    @Operation(
            summary = "Withdraws money",
            description = "Withdraw money in user account by providing user account number",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Money withdrawn successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Some error due to your side"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error"
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> withdraw( @Parameter(
            description = "Account number",
            example="65"
    ) @PathVariable Long accNo, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Amount to be withdrawn",
            required = true
    ) @RequestBody BalanceReq balanceReq){
        BankAccountResponseDto withdrawnInAccount = bankAccountService.withdrawInAccount(accNo, balanceReq.getBalance());

        return new GenericDto<BankAccountResponseDto>(HttpStatus.ACCEPTED, "Amount withdrawn", withdrawnInAccount);
    }

    @GetMapping("/balance/{accNo}")
    @Operation(
            summary = "Check bank account balance",
            description = "Check balance of a user account by providing user account number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Checked balance of account successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public GenericDto<BalanceResDto> checkBalance(@Parameter(
            description = "Account number",
            example = "65"
    ) @PathVariable Long accNo){
        BalanceResDto balance =  bankAccountService.checkBalance(accNo);
        return new GenericDto<BalanceResDto>(HttpStatus.ACCEPTED, "" ,  balance);
    }

    @GetMapping("/interest/{accNo}")
    @Operation(
            summary = "Check interest of a account",
            description = "Check interest of a user account getting on that account by providing user account number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Checked interest of account successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
        public GenericDto<InterestResponseDto> checkInterest( @Parameter(
            description = "Account number",
            example = "65"
    ) @PathVariable Long accNo){
        InterestResponseDto interest =  bankAccountService.calculateInterest(accNo);
        if(interest == null){
            return new GenericDto<InterestResponseDto>(HttpStatus.NOT_FOUND, "account not found");
        }
        return new GenericDto<InterestResponseDto>(HttpStatus.ACCEPTED, "" ,  interest);
    }

    @GetMapping("/getBankAccountDetails/{accNo}")
    @Operation(
            summary = " Retrieve bank account details",
            description = "Retrieve bank account details of the logged in user by taking it's account number",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Got Bank account details"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    public GenericDto<BankAccountResponseDto> getBankAccountDetails( @Parameter(
            description = "Account number",
            example = "65"
    ) @PathVariable Long accNo){
        BankAccountResponseDto bankAccount = bankAccountService.getBankAccountDetails(accNo);
        return new GenericDto<BankAccountResponseDto>(HttpStatus.OK, "Here are your bank details: ", bankAccount);
    }


}
