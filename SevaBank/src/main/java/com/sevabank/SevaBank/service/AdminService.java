package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.AgeReqDto;
import com.sevabank.SevaBank.dto.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    private UserRepository userRepository;
    private BankAccountRepository bankAccountRepository;

    public AdminService(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersLessThanBal(Double balance){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts.stream()
                .filter(x -> x.getBalance() < balance)
                .map(BankAccount::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUsersHavingSaving(){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getAccountType() == AccountType.SAVING)
                .map(BankAccount::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUsersHavingCurrent() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getAccountType() == AccountType.CURRENT)
                .map(BankAccount::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getOldAgeUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .filter(x -> x.getAge() >= 60)
                .collect(Collectors.toList());
    }

    public User getUsersByEmail(String email) {
        List<User> users = userRepository.findAll();
        Optional<User> user = users
                .stream()
                .filter(x -> x.getEmail().equals(email))
                .findFirst();

        return user.orElse(null);

    }


    public List<String> getAllUsersEmail() {
        List<User> users = userRepository.findAll();
        return users
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

    public List<User> getUserOverSpecificBal(Double amt) {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts
                .stream()
                .filter(x -> x.getBalance() > amt)
                .map(BankAccount::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUserAboveAge(Integer age) {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getAge() > age)
                .collect(Collectors.toList());
    }

    public User getUserByAccNo(Long accNo) {
        return bankAccountRepository.findAll()
                .stream()
                .filter(x -> x.getAccNo() == accNo)
                .map(BankAccount::getUser)
                .findFirst()
                .orElse(null);
    }

    public List<User> getUserBwAge(AgeReqDto ageReqDto) {
        return userRepository.findAll()
                .stream()
                .filter(x -> x.getAge() > ageReqDto.getAge1() && x.getAge() <ageReqDto.getAge2())
                .collect(Collectors.toList());
    }

    public Long getTotalNoAccV1() {
        return bankAccountRepository.count();
    }

    public List<User> getUserAboveAgeV1(Integer age) {
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

    public List<User> getUserOverSpecificBalV1(Double amt) {
        return bankAccountRepository.findByBalanceGreaterThan(amt)
                .stream().map(BankAccount::getUser)
                .collect(Collectors.toList());

    }


    public List<User> getUsersLessThanBalV1(Double amount) {
        return bankAccountRepository.findByBalanceLessThan(amount)
                .stream().map(BankAccount::getUser)
                .collect(Collectors.toList());
    }
}
