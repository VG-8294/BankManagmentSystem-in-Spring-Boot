package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import com.sevabank.SevaBank.service.AdminServices;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServicesImpln implements AdminServices {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    public AdminServicesImpln(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<UserResponseDto> getOldAgeUsersV1() {
        return userRepository.findByAgeGreaterThanEqual(60)
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

    @Override
    public UserResponseDto getUsersByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }


    @Override
    public List<String> getAllUsersEmail() {
        return userRepository.findAll()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getTotalNoAcc() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return Math.toIntExact(accounts
                .stream()
                .count());
    }


    @Override
    public Double getTotalMoneyInBank() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getIsDeleted() == false)
                .mapToDouble(BankAccount::getBalance)
                .sum();
    }

    @Override
    public UserResponseDto getUserWithMaxBal() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .max(Comparator.comparing(BankAccount::getBalance))
                .map(BankAccount::getUser)
                .map(x -> {
                    UserResponseDto dto = new UserResponseDto();
                    dto.setId(x.getId());
                    dto.setName(x.getName());
                    dto.setEmail(x.getEmail());
                    return dto;
                })
                .get();
    }

    @Override
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

    @Override
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



    @Override
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

    @Override
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

    @Override
    public Long getTotalNoAccV1() {
        return bankAccountRepository.count();
    }

    @Override
    public List<UserResponseDto> getUserAboveAgeV1(Integer age) {

        List<User> users = userRepository.findByAgeGreaterThan(age);

        return users.stream()
                .map(user -> {
                    UserResponseDto dto = new UserResponseDto();

                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
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


    @Override
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

    @Override
    public List<BankAccountResponseDto> getAllBankAccounts(){
        return bankAccountRepository.findAll()
                .stream()
                .filter(x -> x.getIsDeleted() == false)
                .map(x -> {
                    BankAccountResponseDto dto = new BankAccountResponseDto();
                    dto.setAccNo(x.getAccNo());
                    dto.setUser_name(x.getUser().getName());
                    dto.setEmail(x.getUser().getEmail());
                    dto.setAccountType(x.getAccountType());
                    dto.setBalance(x.getBalance());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteAccountById(Long id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if(!account.isPresent()){
            return false;
        }
        BankAccount accountToDel = account.get();
        accountToDel.setDeleted(true);
        bankAccountRepository.save(accountToDel);
        return accountToDel.getDeleted();
    }
}
