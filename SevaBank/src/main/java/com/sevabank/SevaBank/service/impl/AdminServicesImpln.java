//package com.sevabank.SevaBank.service.impl;
//
//import com.sevabank.SevaBank.Enum.AccountType;
//import com.sevabank.SevaBank.dto.request.AgeReqDto;
//import com.sevabank.SevaBank.dto.response.BalanceResDto;
//import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
//import com.sevabank.SevaBank.dto.response.UserResponseDto;
//import com.sevabank.SevaBank.entity.BankAccount;
//import com.sevabank.SevaBank.entity.User;
//import com.sevabank.SevaBank.exception.ResourceNotFoundException;
//import com.sevabank.SevaBank.repository.BankAccountRepository;
//import com.sevabank.SevaBank.repository.UserRepository;
//import com.sevabank.SevaBank.service.AdminServices;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class AdminServicesImpln implements AdminServices {
//
//    private final UserRepository userRepository;
//    private final BankAccountRepository bankAccountRepository;
//
//    public AdminServicesImpln(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
//        this.userRepository = userRepository;
//        this.bankAccountRepository = bankAccountRepository;
//    }
//
//    private UserResponseDto mapToDto(User user){
//        UserResponseDto dto = new UserResponseDto();
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setAge(user.getAge());
//        log.info("user mapped to userDto");
//        return dto;
//    }
//
//    private BankAccountResponseDto mapToBankDto(BankAccount bankAccount){
//        BankAccountResponseDto dto = new BankAccountResponseDto();
//        dto.setAccNo(bankAccount.getAccNo());
//        dto.setUser_name(bankAccount.getUser().getName());
//        dto.setEmail(bankAccount.getUser().getEmail());
//        dto.setAccountType(bankAccount.getAccountType());
//        dto.setBalance(bankAccount.getBalance());
//        return dto;
//    }
//
//    @Override
//    public List<UserResponseDto> getAllUsers() {
//        List<User> users = new ArrayList<>(userRepository.findAll());
//        if(users == null){
//            log.error("Users not present in db");
//            throw new ResourceNotFoundException("Users not found!");
//        }
//        log.error("users list returned!");
//        return users
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//
//    }
//
//    @Override
//    public List<UserResponseDto> getUsersLessThanBal(Double balance){
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("Nothing present in bank db having balance less than {}", balance);
//            throw new ResourceNotFoundException("Users not found!");
//        }
//        log.error("All bank accounts having balance less than {}", balance);
//        return accounts.stream()
//                .filter(x -> x.getBalance() < balance && x.getIsDeleted() == false)
//                .map(BankAccount::getUser)
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getUsersHavingSaving(){
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("Nothing present in bank db having savings account");
//            throw new ResourceNotFoundException("Users not found!");
//        }
//        log.info("returned users having savings account");
//        return accounts
//                .stream()
//                .filter(x -> x.getAccountType() == AccountType.SAVING)
//                .map(BankAccount::getUser)
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getUsersHavingCurrent() {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("Nothing present in bank db having current account");
//            throw new ResourceNotFoundException("Users not found!");
//        }
//        log.info("returned users with current account!");
//        return accounts
//                .stream()
//                .filter(x -> x.getAccountType() == AccountType.CURRENT)
//                .map(BankAccount::getUser)
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getOldAgeUsers() {
//        List<User> users = userRepository.findAll();
//        if(users.isEmpty()){
//            log.error("Nothing present in bank db of old age users");
//            throw new ResourceNotFoundException("Users not found!");
//        }
//        log.info("returned users of old age");
//        return users
//                .stream()
//                .filter(x -> x.getAge() >= 60)
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getOldAgeUsersV1() {
//        log.info("returned users of old age by v1");
//        return userRepository.findByAgeGreaterThanEqual(60)
//                .stream()
//                .map(user -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(user.getId());
//                    dto.setName(user.getName());
//                    dto.setEmail(user.getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserResponseDto getUsersByEmail(String email) {
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("user not found with this email!"));
//        log.info("User found by email!");
//        return mapToDto(user);
//    }
//
//
//    @Override
//    public List<String> getAllUsersEmail() {
//        return userRepository.findAll()
//                .stream()
//                .map(User::getEmail)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Integer getTotalNoAcc() {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("accounts not present in bank db");
//            throw new ResourceNotFoundException("accounts not found!");
//        }
//        log.info("returned the total number of accounts");
//        return Math.toIntExact(accounts
//                .stream()
//                .count());
//    }
//
//
//    @Override
//    public Double getTotalMoneyInBank() {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("accounts not present in bank db for total money");
//            throw new ResourceNotFoundException("accounts not found!");
//        }
//        log.info("returned the total money");
//        return accounts
//                .stream()
//                .filter(x -> x.getIsDeleted() == false)
//                .mapToDouble(BankAccount::getBalance)
//                .sum();
//    }
//
//    @Override
//    public UserResponseDto getUserWithMaxBal() {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("accounts not present in bank db for maximum balance");
//            throw new ResourceNotFoundException("account not found!");
//        }
//        log.info("User returned with maximum balance");
//        return accounts
//                .stream()
//                .max(Comparator.comparing(BankAccount::getBalance))
//                .map(BankAccount::getUser)
//                .map(x -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(x.getId());
//                    dto.setName(x.getName());
//                    dto.setEmail(x.getEmail());
//                    return dto;
//                })
//                .get();
//    }
//
//    @Override
//    public List<UserResponseDto> getUserOverSpecificBal(Double amt) {
//        List<BankAccount> accounts = bankAccountRepository.findAll();
//        if(accounts.isEmpty()){
//            log.error("accounts not present in bank db above this balance");
//            throw new ResourceNotFoundException("account not found!");
//        }
//        log.info("retuned users having balance more than {}", amt);
//        return accounts
//                .stream()
//                .filter(x -> x.getBalance() > amt)
//                .map(BankAccount::getUser)
//                .map(user -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(user.getId());
//                    dto.setName(user.getName());
//                    dto.setEmail(user.getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getUserAboveAge(Integer age) {
//        log.info("returned users b/w particular ages");
//        return userRepository.findAll()
//                .stream()
//                .filter(x -> x.getAge() > age)
//                .map(user -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(user.getId());
//                    dto.setName(user.getName());
//                    dto.setEmail(user.getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//
//
//    @Override
//    public UserResponseDto getUserByAccNo(Long accNo) {
//        User user =  bankAccountRepository.findAll()
//                .stream()
//                .filter(x -> x.getAccNo() == accNo)
//                .map(BankAccount::getUser)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found with account number " + accNo));
//        log.info("returned user having account number {}", accNo);
//        return mapToDto(user);
//    }
//
//    @Override
//    public List<UserResponseDto> getUserBwAge(AgeReqDto ageReqDto) {
//        log.info("returned users b/w two ages");
//        return userRepository.findAll()
//                .stream()
//                .filter(x -> x.getAge() > ageReqDto.getAge1() && x.getAge() <ageReqDto.getAge2())
//                .map(user -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(user.getId());
//                    dto.setName(user.getName());
//                    dto.setEmail(user.getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Long getTotalNoAccV1() {
//        log.info("returned the total number of accounts by v1");
//        return bankAccountRepository.count();
//    }
//
//    @Override
//    public List<UserResponseDto> getUserAboveAgeV1(Integer age) {
//        log.info("returned users b/w two ages by v1");
//        List<User> users = userRepository.findByAgeGreaterThan(age);
//        log.info("");
//        return users.stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserResponseDto getUserByAccNoV1(Long accNo) {
//
//        User user =  bankAccountRepository.findByAccNo(accNo)
//                .map(BankAccount::getUser)
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found with account number " + accNo));
//        log.info("returned user having account number {} by v1", accNo);
//        return mapToDto(user);
//    }
//
//    @Override
//    public List<UserResponseDto> getUserOverSpecificBalV1(Double amt) {
//        log.error("accounts not present in bank db above this balance by v1");
//        return bankAccountRepository.findByBalanceGreaterThan(amt)
//                .stream().map(BankAccount::getUser)
//                .map(user -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(user.getId());
//                    dto.setName(user.getName());
//                    dto.setEmail(user.getEmail());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//
//    }
//
//
//    @Override
//    public List<UserResponseDto> getUsersLessThanBalV1(Double amount) {
//        log.error("All bank accounts having balance less than {} by v1", amount);
//        return bankAccountRepository.findByBalanceLessThan(amount)
//                .stream().map(BankAccount::getUser)
//                .map(x -> {
//                    UserResponseDto dto = new UserResponseDto();
//                    dto.setId(x.getId());
//                    dto.setName(x.getName());
//                    dto.setEmail(x.getEmail());
//                    return dto;
//                })
//
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BankAccountResponseDto> getAllBankAccounts(){
//        log.info("returned all the bank accounts");
//        return bankAccountRepository.findAll()
//                .stream()
//                .filter(x -> x.getIsDeleted() == false)
//                .map(x -> {
//                    BankAccountResponseDto dto = new BankAccountResponseDto();
//                    dto.setAccNo(x.getAccNo());
//                    dto.setUser_name(x.getUser().getName());
//                    dto.setEmail(x.getUser().getEmail());
//                    dto.setAccountType(x.getAccountType());
//                    dto.setBalance(x.getBalance());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Boolean deleteAccountById(Long id) {
//        Optional<BankAccount> account = bankAccountRepository.findById(id);
//        if(!account.isPresent()){
//            log.error("Account not found with id-{} for deletion", id);
//            throw new ResourceNotFoundException("Account doesn't exist!");
//        }
//        BankAccount accountToDel = account.get();
//        accountToDel.setDeleted(true);
//        bankAccountRepository.save(accountToDel);
//        log.info("bank account deleted with id-{}", id);
//        return accountToDel.getDeleted();
//    }
//
//    @Override
//    public List<UserResponseDto> getUsersWithMulAcc(){
//        return userRepository.findUsersWithMultipleAccounts()
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserResponseDto> getUsersHavingTotalBalGreaterThan100000(){
//        return userRepository.findUsersWithTotalBalanceGreaterThan100000()
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public BalanceResDto getAvgBalOfAcc(){
//        Double avgBal = bankAccountRepository.getAverageOfBalance();
//        BalanceResDto dto = new BalanceResDto();
//        dto.setBalance(avgBal);
//        return dto;
//    }
//
//    @Override
//    public List<BankAccountResponseDto> getUsersWithBalGreaterThanAvgBal(){
//        List<BankAccount> bk = bankAccountRepository.findAccountsHavingBalanceGreaterThanAvgBal();
//        return bk
//                .stream()
//                .map(this::mapToBankDto)
//                .collect(Collectors.toList());
//    }
//
//}
