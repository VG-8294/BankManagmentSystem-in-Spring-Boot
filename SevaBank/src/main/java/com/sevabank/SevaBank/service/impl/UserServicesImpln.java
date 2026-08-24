package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.request.UpdateUserReq;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.exception.InvalidAgeException;
import com.sevabank.SevaBank.exception.InvalidCredentialsException;
import com.sevabank.SevaBank.exception.ResourceNotFoundException;
import com.sevabank.SevaBank.exception.UserAlreadyExistsException;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import com.sevabank.SevaBank.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServicesImpln implements UserServices {

    private final UserRepository userRepo;
    private final BankAccountRepository bankAccountRepository;

    public UserServicesImpln(UserRepository userRepo, BankAccountRepository bankAccountRepository) {
        this.userRepo = userRepo;
        this.bankAccountRepository = bankAccountRepository;
    }

    private UserResponseDto mapToDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }


    @Override
    public UserResponseDto createUser(RegisterReqDto dto){
        User newUser = new User(dto.getName(),dto.getEmail(), dto.getPassword(), dto.getAge());
        if(userRepo.existsByEmail(newUser.getEmail())){
            log.error("user email already exists in db");
            throw new UserAlreadyExistsException("User Already exists!");
        }
        if(newUser.getAge() < 0){
            log.error("age is negative");
            throw new InvalidAgeException("Age cannot be negative!");
        }
        userRepo.save(newUser);
        log.info("User created with id-{}", newUser.getId());
        return mapToDto(newUser);
    }

    @Override
    public UserResponseDto login(LoginReqDto loginReqDto) {
        Optional<BankAccount> account = bankAccountRepository.findById(loginReqDto.getAccNo());
        if(!account.isPresent()){
            log.error("Account number doesn't exist in db");
            throw new ResourceNotFoundException("Invalid account number!");
        }
            User user = account.get().getUser();
        if(!user.getEmail().equals(loginReqDto.getEmail()) || !user.getPassword().equals(loginReqDto.getPassword())){
            log.error("email or password is incorrect");
            throw new InvalidCredentialsException("Invalid credentials!");
        }
        log.info("User logged in successfully!");
            return mapToDto(user);
    }

    @Override
    public UserResponseDto loginV1(LoginReqDto loginReqDto) {
        System.out.println("In UserService");
        System.out.println(loginReqDto);
        BankAccount account =  bankAccountRepository.findByAccNoAndUserEmailAndUserPassword(
                    loginReqDto.getAccNo(),
                    loginReqDto.getEmail(),
                    loginReqDto.getPassword()
        );
        if(account == null){
            log.error("email or password is incorrect");
            throw new ResourceNotFoundException("Invalid credentials!");
        }
        UserResponseDto user = new UserResponseDto();
        user.setId(account.getUser().getId());
        user.setName(account.getUser().getName());
        user.setEmail(account.getUser().getEmail());
        user.setAge(account.getUser().getAge());
        log.info("User logged in successfully!");
        return user;
    }

    @Override
    public UserResponseDto getUserDetails(Long id) {
        User user = bankAccountRepository.findById(id)
                .get()
                .getUser();

        if(user == null){
            throw new ResourceNotFoundException("Account not found!");
        }

        return mapToDto(user);
    }

}
