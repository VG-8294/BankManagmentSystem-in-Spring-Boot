package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.AgeReqDto;
import com.sevabank.SevaBank.dto.UserResponseDto;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
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

//    @GetMapping("/getUserWithMaxBal")
//    public ResponseEntity<User> getUserWithMaxBal(){
//        User user = adminService.getUserWithMaxBal();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(user);
//    }

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




 }
