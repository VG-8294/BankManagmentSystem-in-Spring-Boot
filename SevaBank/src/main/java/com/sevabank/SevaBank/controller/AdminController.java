package com.sevabank.SevaBank.controller;

import com.sevabank.SevaBank.dto.AgeReqDto;
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
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = adminService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUsers);
    }

    @GetMapping("/getUsers/{amount}")
    public ResponseEntity<List<User>> getUsersLessThanBal(@PathVariable Double amount){
        List<User> users = adminService.getUsersLessThanBal(amount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/getUsersHavingSaving")
    public ResponseEntity<List<User>> getUsersHavingSaving(){
        List<User> users = adminService.getUsersHavingSaving();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/getUsersHavingCurrent")
    public ResponseEntity<List<User>> getUsersHavingCurrent(){
        List<User> users = adminService.getUsersHavingCurrent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/getOldAgeUsers")
    public ResponseEntity<List<User>> getOldAgeUsers(){
        List<User> users = adminService.getOldAgeUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/getUsersByEmail/{email}")
    public ResponseEntity<User> getUsersByEmail(@PathVariable String email){
        User user = adminService.getUsersByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/getAllUsersEmail")
    public ResponseEntity<List<String>> getAllUsersEmail(){
        List<String> emails = adminService.getAllUsersEmail();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(emails);
    }

    @GetMapping("/getTotalNoAcc")
    public ResponseEntity<Integer> getTotalNoAcc(){
        Integer acc = adminService.getTotalNoAcc();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(acc);
    }

    @GetMapping("/v1/getTotalNoAcc")
    public ResponseEntity<Long> getTotalNoAccV1(){
        Long acc = adminService.getTotalNoAccV1();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(acc);
    }

    @GetMapping("/getTotalMoney")
    public ResponseEntity<Double> getTotalMoney(){
        Double money = adminService.getTotalMoneyInBank();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(money);
    }

//    @GetMapping("/getUserWithMaxBal")
//    public ResponseEntity<User> getUserWithMaxBal(){
//        User user = adminService.getUserWithMaxBal();
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(user);
//    }

    @GetMapping("/getUsersOverCertainBal/{amt}")
    public ResponseEntity<List<User>> getUserWithSpecificBal(@PathVariable Double amt){
        List<User> user = adminService.getUserOverSpecificBal(amt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/getUsersAboveSomeAge/{age}")
    public ResponseEntity<List<User>> getUserAboveAge(@PathVariable Integer age){
        List<User> user = adminService.getUserAboveAge(age);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/getUserByAccNo/{accNo}")
    public ResponseEntity<User> getUserByAccNo(@PathVariable Long accNo){
        User user = adminService.getUserByAccNo(accNo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/getUsersBwAge")
    public ResponseEntity<List<User>> getUserBwAge(@RequestBody AgeReqDto ageReqDto){
        List<User> users = adminService.getUserBwAge(ageReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }




 }
