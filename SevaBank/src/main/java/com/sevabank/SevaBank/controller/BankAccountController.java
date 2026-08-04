package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody CreateBankAccountRequest bankReq){
        BankAccount createdBankAccount = bankAccountService.createBankAccount(bankReq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBankAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BankAccount>> getBankAccount(@PathVariable Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bankAccount);
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts(){
        List<BankAccount> allBankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allBankAccounts);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBankAccount(@PathVariable Long id){
        Boolean isAccountDeleted = bankAccountService.deleteAccountById(id);
        if(!isAccountDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Account Deleted!");
    }
}
