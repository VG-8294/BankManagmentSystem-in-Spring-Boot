package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import com.sevabank.SevaBank.dto.request.BalanceReq;
import com.sevabank.SevaBank.dto.response.BalanceResDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.InterestResponseDto;
import com.sevabank.SevaBank.service.BankServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankAccount")
@Tag(name="Bank APIs", description = "create, deposit, withdraw, balance, check balance, interest")
public class BankAccountController {

    BankServices bankAccountService;

    public BankAccountController(BankServices bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public GenericDto<BankAccountResponseDto> createBankAccount(@RequestBody CreateBankAccountRequest bankReq){
        BankAccountResponseDto bankDto =  bankAccountService.createBankAccount(bankReq);
        return new GenericDto<BankAccountResponseDto>(HttpStatus.CREATED, "Account created", bankDto);

    }

    @PostMapping("/deposit/{id}")
    public GenericDto<BankAccountResponseDto> deposit(@PathVariable Long id, @RequestBody BalanceReq balanceReq){
        BankAccountResponseDto depositedAccount = bankAccountService.depositInAccount(id, balanceReq.getBalance());

        return new GenericDto<BankAccountResponseDto>(HttpStatus.ACCEPTED, "Amount deposited!", depositedAccount);
    }

    @PostMapping("/withdraw/{id}")
    public GenericDto<BankAccountResponseDto> withdraw(@PathVariable Long id, @RequestBody BalanceReq balanceReq){
        BankAccountResponseDto withdrawnInAccount = bankAccountService.withdrawInAccount(id, balanceReq.getBalance());

        return new GenericDto<BankAccountResponseDto>(HttpStatus.ACCEPTED, "Amount withdrawn", withdrawnInAccount);
    }

    @GetMapping("/balance/{id}")
    public GenericDto<BalanceResDto> checkBalance(@PathVariable Long id){
        BalanceResDto balance =  bankAccountService.checkBalance(id);
        return new GenericDto<BalanceResDto>(HttpStatus.ACCEPTED, "" ,  balance);
    }

    @GetMapping("/interest/{id}")
        public GenericDto<InterestResponseDto> checkInterest(@PathVariable Long id){
        InterestResponseDto interest =  bankAccountService.calculateInterest(id);
        if(interest == null){
            return new GenericDto<InterestResponseDto>(HttpStatus.NOT_FOUND, "account not found");
        }
        return new GenericDto<InterestResponseDto>(HttpStatus.ACCEPTED, "" ,  interest);
    }

}
