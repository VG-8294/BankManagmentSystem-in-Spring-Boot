package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.AgeReqDto;
import com.sevabank.SevaBank.dto.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private UserRepository userRepository;
    private BankAccountRepository bankAccountRepository;

    public AdminService(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUsersLessThanBal(Double balance){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts.stream()
                .filter(x -> x.getBalance() < balance)
                .map(BankAccount::getUser)
                .map(x -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(x.getId());
                    dto.setName(x.getName());
                    dto.setEmail(x.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUsersHavingSaving(){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getAccountType() == AccountType.SAVING)
                .map(BankAccount::getUser)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUsersHavingCurrent() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getAccountType() == AccountType.CURRENT)
                .map(BankAccount::getUser)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getOldAgeUsers() {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getAge() >= 60)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserResponseDto getUsersByEmail(String email) {
           return userRepository.findAll()
                .stream()
                .filter(x -> x.getEmail().equals(email))
                            .map(user -> {
                                UserResponseDto dto = new UserResponseDto();
                                dto.setId(user.getId());
                                dto.setName(user.getName());
                                dto.setEmail(user.getEmail());
                                return dto;
                            })
                        .findFirst()
                   .orElse(null);

    }


    public List<String> getAllUsersEmail() {
        return userRepository.findAll()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    public Integer getTotalNoAcc() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return Math.toIntExact(accounts
                .stream()
                .count());
    }


    public Double getTotalMoneyInBank() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();
    }

//    public User getUserWithMaxBal() {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        return accounts
//                .stream()
//                .max(Comparator.comparing(BankAccount::getBalance))
//                .map(BankAccount::getUser)
//                .get();
//    }

    public List<UserResponseDto> getUserOverSpecificBal(Double amt) {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getBalance() > amt)
                .map(BankAccount::getUser)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getUserAboveAge(Integer age) {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getAge() > age)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserByAccNo(Long accNo) {
        return bankAccountRepository.findAll()
                .stream()
                .filter(x -> x.getAccNo() == accNo)
                .map(BankAccount::getUser)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .findFirst()
                .orElse(null);
    }

    public List<UserResponseDto> getUserBwAge(AgeReqDto ageReqDto) {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getAge() > ageReqDto.getAge1() && x.getAge() <ageReqDto.getAge2())
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Long getTotalNoAccV1() {
        return bankAccountRepository.count();
    }

    public List<UserResponseDto> getUserAboveAgeV1(Integer age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    public UserResponseDto getUserByAccNoV1(Long accNo) {
        User user =  bankAccountRepository.findByAccNo(accNo)
                .map(BankAccount::getUser)
                .orElse(null);

        if(user == null){
            return null;
        }
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

    public List<UserResponseDto> getUserOverSpecificBalV1(Double amt) {
        return bankAccountRepository.findByBalanceGreaterThan(amt)
                .stream().map(BankAccount::getUser)
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());

    }


    public List<UserResponseDto> getUsersLessThanBalV1(Double amount) {
        return bankAccountRepository.findByBalanceLessThan(amount)
                .stream().map(BankAccount::getUser)
                .map(x -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(x.getId());
                    dto.setName(x.getName());
                    dto.setEmail(x.getEmail());
                    return dto;
                })

                .collect(Collectors.toList());
    }
}
