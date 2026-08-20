//package com.sevabank.SevaBank.controller;
//
//import com.sevabank.SevaBank.dto.generic.GenericDto;
//import com.sevabank.SevaBank.dto.request.AgeReqDto;
//import com.sevabank.SevaBank.dto.response.BalanceResDto;
//import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
//import com.sevabank.SevaBank.dto.response.UserResponseDto;
//import com.sevabank.SevaBank.entity.User;
//import com.sevabank.SevaBank.service.AdminServices;
//import com.sevabank.SevaBank.service.UserServices;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//    private final AdminServices adminService;
//
//    public AdminController(AdminServices adminService) {
//        this.adminService = adminService;
//    }
//
//    @GetMapping("/getAllUsers")
//    public GenericDto<List<UserResponseDto>> getAllUsers(){
//        List<UserResponseDto> usersList =  adminService.getAllUsers();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getUsersLessThanBal/{amount}")
//    public GenericDto<List<UserResponseDto>> getUsersLessThanBal(@PathVariable Double amount){
//        List<UserResponseDto> usersList = adminService.getUsersLessThanBal(amount);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/v1/getUsersThanBal/{amount}")
//    public GenericDto<List<UserResponseDto>> getUsersLessThanBalV1(@PathVariable Double amount){
//        List<UserResponseDto> usersList = adminService.getUsersLessThanBalV1(amount);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getUsersHavingSaving")
//    public GenericDto<List<UserResponseDto>> getUsersHavingSaving(){
//
//        List<UserResponseDto> usersList = adminService.getUsersHavingSaving();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getUsersHavingCurrent")
//    public GenericDto<List<UserResponseDto>> getUsersHavingCurrent(){
//        List<UserResponseDto> usersList = adminService.getUsersHavingCurrent();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getOldAgeUsers")
//    public GenericDto<List<UserResponseDto>> getOldAgeUsers(){
//        List<UserResponseDto> usersList = adminService.getOldAgeUsers();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/v1/getOldAgeUsers")
//    public GenericDto<List<UserResponseDto>> getOldAgeUsersV1(){
//        List<UserResponseDto> usersList = adminService.getOldAgeUsersV1();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getUsersByEmail/{email}")
//    public GenericDto<UserResponseDto> getUsersByEmail(@PathVariable String email){
//        UserResponseDto user =  adminService.getUsersByEmail(email);
//        return new GenericDto<UserResponseDto>(HttpStatus.OK, "Here the user: " + user);
//    }
//
//    @GetMapping("/getAllUsersEmail")
//    public List<String> getAllUsersEmail(){
//         return adminService.getAllUsersEmail();
//    }
//
//    @GetMapping("/getTotalNoAcc")
//    public GenericDto<Integer> getTotalNoAcc(){
//        return new GenericDto<Integer>(HttpStatus.OK, "The total number of accounts in bank are: ", adminService.getTotalNoAcc());
//    }
//
//    @GetMapping("/v1/getTotalNoAcc")
//    public GenericDto<Long> getTotalNoAccV1(){
//        return new GenericDto<Long>(HttpStatus.OK, "The total number of accounts in bank are: ", adminService.getTotalNoAccV1());
//    }
//
//    @GetMapping("/getTotalMoney")
//    public GenericDto<Double> getTotalMoney(){
//        return new GenericDto<Double>(HttpStatus.OK, "The total in money in bank is: ", adminService.getTotalMoneyInBank());
//    }
//
//    @GetMapping("/getUserWithMaxBal")
//    public GenericDto<UserResponseDto> getUserWithMaxBal(){
//        return new GenericDto<UserResponseDto>(HttpStatus.OK, "The user with maximum balance is: ", adminService.getUserWithMaxBal());
//    }
//
//    @GetMapping("/getUsersOverCertainBal/{amt}")
//    public GenericDto<List<UserResponseDto>> getUserWithSpecificBal(@PathVariable Double amt){
//        List<UserResponseDto> usersList = adminService.getUserOverSpecificBal(amt);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/v1/getUsersOverCertainBal/{amt}")
//    public GenericDto<List<UserResponseDto>> getUserWithSpecificBalV1(@PathVariable Double amt){
//        List<UserResponseDto> usersList = adminService.getUserOverSpecificBalV1(amt);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getUsersAboveSomeAge/{age}")
//    public GenericDto<List<UserResponseDto>> getUserAboveAge(@PathVariable Integer age){
//        List<UserResponseDto> usersList = adminService.getUserAboveAge(age);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/v1/getUsersAboveSomeAge/{age}")
//    public GenericDto<List<UserResponseDto>> getUserAboveAgeV1(@PathVariable Integer age){
//        List<UserResponseDto> usersList = adminService.getUserAboveAgeV1(age);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//
//
//    @GetMapping("/getUserByAccNo/{accNo}")
//    public GenericDto<UserResponseDto> getUserByAccNo(@PathVariable Long accNo){
//        UserResponseDto dto =  adminService.getUserByAccNo(accNo);
//        return new GenericDto<UserResponseDto>(HttpStatus.OK, "All the users are: ", dto);
//    }
//
//    @GetMapping("/v1/getUserByAccNo/{accNo}")
//    public GenericDto<UserResponseDto> getUserByAccNoV1(@PathVariable Long accNo){
//        UserResponseDto dto = adminService.getUserByAccNoV1(accNo);
//        return new GenericDto<UserResponseDto>(HttpStatus.OK, "All the users are: ", dto);
//    }
//
//
//
//    @GetMapping("/getUsersBwAge")
//    public GenericDto<List<UserResponseDto>> getUserBwAge(@RequestBody AgeReqDto ageReqDto){
//        List<UserResponseDto> usersList = adminService.getUserBwAge(ageReqDto);
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.OK, "All the users are: ", usersList);
//    }
//
//    @GetMapping("/getAllBankAccounts")
//    public List<BankAccountResponseDto> getAllBankAccounts(){
//        return adminService.getAllBankAccounts();
//    }
//
//    @DeleteMapping("/deleteAccount/{id}")
//    public GenericDto<BankAccountResponseDto> deleteAccount(@PathVariable Long id){
//        Boolean isDeleted = adminService.deleteAccountById(id);
//        return new GenericDto<BankAccountResponseDto>(HttpStatus.OK, "Account deleted!");
//    }
//
//    @GetMapping("/getUsersWithMulAcc")
//    public GenericDto<List<UserResponseDto>> usersWithMulAcc(){
//        List<UserResponseDto> usersWithMulAcc = adminService.getUsersWithMulAcc();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.ACCEPTED, "users with multiple accounts", usersWithMulAcc);
//    }
//
//    @GetMapping("/getUsersHavingMoreThan100000")
//    public GenericDto<List<UserResponseDto>> usersHavingBalanceGreaterThan100000(){
//        List<UserResponseDto> users = adminService.getUsersHavingTotalBalGreaterThan100000();
//        return new GenericDto<List<UserResponseDto>>(HttpStatus.ACCEPTED, "users with balance greater than 100000", users);
//
//    }
//
//    @GetMapping("/getAvgBalOfAllAcc")
//    public GenericDto<BalanceResDto> avgBalOfAllAcc(){
//        BalanceResDto dto = adminService.getAvgBalOfAcc();
//        return new GenericDto<BalanceResDto>(HttpStatus.OK, "Average of balance of all accounts", dto);
//    }
//
//    @GetMapping("/getUsersWithBalGreaterThanAvgBal")
//    public GenericDto<List<BankAccountResponseDto>> getUsersWithBalGreaterThanAvgBal(){
//        List<BankAccountResponseDto> listOfAcc = adminService.getUsersWithBalGreaterThanAvgBal();
//        return new GenericDto<List<BankAccountResponseDto>>(HttpStatus.OK, "Users with balance greater than average of balance of all accounts", listOfAcc);
//    }
//
//
// }
