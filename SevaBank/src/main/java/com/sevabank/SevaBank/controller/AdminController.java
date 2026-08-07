package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.AdminServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminServices adminService;

    public AdminController(AdminServices adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAllUsers")
    public List<UserResponseDto> getAllUsers(){
        return adminService.getAllUsers();
    }

    @GetMapping("/getUsersLessThanBal/{amount}")
    public List<UserResponseDto> getUsersLessThanBal(@PathVariable Double amount){
        return adminService.getUsersLessThanBal(amount);
    }

    @GetMapping("/v1/getUsersThanBal/{amount}")
    public List<UserResponseDto> getUsersLessThanBalV1(@PathVariable Double amount){
        return adminService.getUsersLessThanBalV1(amount);
    }

    @GetMapping("/getUsersHavingSaving")
    public List<UserResponseDto> getUsersHavingSaving(){
        return adminService.getUsersHavingSaving();
    }

    @GetMapping("/getUsersHavingCurrent")
    public List<UserResponseDto> getUsersHavingCurrent(){
        return adminService.getUsersHavingCurrent();
    }

    @GetMapping("/getOldAgeUsers")
    public List<UserResponseDto> getOldAgeUsers(){
        return adminService.getOldAgeUsers();
    }

    @GetMapping("/getUsersByEmail/{email}")
    public UserResponseDto getUsersByEmail(@PathVariable String email){
        return adminService.getUsersByEmail(email);
    }

    @GetMapping("/getAllUsersEmail")
    public List<String> getAllUsersEmail(){
         return adminService.getAllUsersEmail();
    }

    @GetMapping("/getTotalNoAcc")
    public Integer getTotalNoAcc(){
        return adminService.getTotalNoAcc();
    }

    @GetMapping("/v1/getTotalNoAcc")
    public Long getTotalNoAccV1(){
        return adminService.getTotalNoAccV1();
    }

    @GetMapping("/getTotalMoney")
    public Double getTotalMoney(){
        return adminService.getTotalMoneyInBank();
    }

    @GetMapping("/getUserWithMaxBal")
    public UserResponseDto getUserWithMaxBal(){
        return adminService.getUserWithMaxBal();
    }

    @GetMapping("/getUsersOverCertainBal/{amt}")
    public List<UserResponseDto> getUserWithSpecificBal(@PathVariable Double amt){
        return adminService.getUserOverSpecificBal(amt);
    }

    @GetMapping("/v1/getUsersOverCertainBal/{amt}")
    public List<UserResponseDto> getUserWithSpecificBalV1(@PathVariable Double amt){
        return adminService.getUserOverSpecificBalV1(amt);
    }

    @GetMapping("/getUsersAboveSomeAge/{age}")
    public List<UserResponseDto> getUserAboveAge(@PathVariable Integer age){
        return adminService.getUserAboveAge(age);
    }

    @GetMapping("/v1/getUsersAboveSomeAge/{age}")
    public List<UserResponseDto> getUserAboveAgeV1(@PathVariable Integer age){
        return adminService.getUserAboveAgeV1(age);
    }



    @GetMapping("/getUserByAccNo/{accNo}")
    public UserResponseDto getUserByAccNo(@PathVariable Long accNo){
        return adminService.getUserByAccNo(accNo);
    }

    @GetMapping("/v1/getUserByAccNo/{accNo}")
    public UserResponseDto getUserByAccNoV1(@PathVariable Long accNo){
        return adminService.getUserByAccNoV1(accNo);
    }



    @GetMapping("/getUsersBwAge")
    public List<UserResponseDto> getUserBwAge(@RequestBody AgeReqDto ageReqDto){
        return adminService.getUserBwAge(ageReqDto);
    }

    @GetMapping("/getAllBankAccounts")
    public List<BankAccountResponseDto> getAllBankAccounts(){
        return adminService.getAllBankAccounts();
    }

    @GetMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable Long id){
        Boolean isDeleted = adminService.deleteAccountById(id);
        if(!isDeleted){
            return "Account not found!";
        }
        return "Account deleted";
    }




 }
