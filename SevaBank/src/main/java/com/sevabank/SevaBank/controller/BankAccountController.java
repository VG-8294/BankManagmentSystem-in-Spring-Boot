package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.request.BalanceReq;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.service.BankServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    BankServices bankAccountService;

    public BankAccountController(BankServices bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public BankAccountResponseDto createBankAccount(@RequestBody CreateBankAccountRequest bankReq){
        return  bankAccountService.createBankAccount(bankReq);
    }

    @PostMapping("/deposit/{id}")
    public String deposit(@PathVariable Long id, @RequestBody BalanceReq balanceReq){
        Boolean isDeposited = bankAccountService.depositInAccount(id, balanceReq.getBalance());
        if(!isDeposited){
            return "Not able to deposit your amount";
        }

        return "Amount deposited!";
    }

    @PostMapping("/withdraw/{id}")
    public String withdraw(@PathVariable Long id, @RequestBody BalanceReq balanceReq){
        Boolean isWithdrawlSuccess = bankAccountService.withdrawInAccount(id, balanceReq.getBalance());
        if(!isWithdrawlSuccess){
            return "Not able to withdraw your amount";
        }

        return "Amount withdrawal successful!";
    }

    @GetMapping("/balance/{id}")
    public Double checkBalance(@PathVariable Long id){
        return bankAccountService.checkBalance(id);
    }

    @GetMapping("/interest/{id}")
        public Double checkInterest(@PathVariable Long id){
        return bankAccountService.calculateInterest(id);
    }

}
