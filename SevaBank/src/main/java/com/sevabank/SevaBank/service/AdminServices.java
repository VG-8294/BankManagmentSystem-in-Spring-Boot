package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.*;
import com.sevabank.SevaBank.entity.BankAccount;

import java.util.List;

public interface AdminServices {
    List<UserResponseDto> getAllUsers();

    List<UserResponseDto> getUsersLessThanBal(Double balance);

    List<UserResponseDto> getUsersHavingSaving();

    List<UserResponseDto> getUsersHavingCurrent();

    List<UserResponseDto> getOldAgeUsers();

    List<UserResponseDto> getOldAgeUsersV1();

    UserResponseDto getUsersByEmail(String email);

    List<String> getAllUsersEmail();

    Integer getTotalNoAcc();

    Double getTotalMoneyInBank();

    UserResponseDto getUserWithMaxBal();

    List<UserResponseDto> getUserOverSpecificBal(Double amt);

    List<UserResponseDto> getUserAboveAge(Integer age);

    UserResponseDto getUserByAccNo(Long accNo);

    List<UserResponseDto> getUserBwAge(AgeReqDto ageReqDto);

    Long getTotalNoAccV1();

    List<UserResponseDto> getUserAboveAgeV1(Integer age);

    UserResponseDto getUserByAccNoV1(Long accNo);

    List<UserResponseDto> getUserOverSpecificBalV1(Double amt);

    List<UserResponseDto> getUsersLessThanBalV1(Double amount);

    List<BankAccountResponseDto> getAllBankAccounts();

    Boolean deleteAccountById(Long id);

    Boolean deleteUserById(Long id);

    List<UserResponseDto> getUsersWithMulAcc();

    List<UserResponseDto> getUsersHavingTotalBalGreaterThan100000();

    BalanceResDto getAvgBalOfAcc();

    List<BankAccountResponseDto> getUsersWithBalGreaterThanAvgBal();

    UserResponseDto updateUser(Long id, UpdateUserReq updateReqUser);

    UserResponseDto updateDetailsUser(Long id, UpdateUserReq updateReqUser);

    InterestResponseDto setInterestLevel(Double interest);

    OverDraftLimitRes setOverDraftLimit(Double odl);
}
