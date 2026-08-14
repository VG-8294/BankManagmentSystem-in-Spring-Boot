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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
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
            throw new UserAlreadyExistsException("User Already exists!");
        }
        if(newUser.getAge() < 0){
            throw new InvalidAgeException("Age cannot be negative!");
        }
        userRepo.save(newUser);
        return mapToDto(newUser);
    }

    @Override
    public UserResponseDto login(LoginReqDto loginReqDto) {
        Optional<BankAccount> account = bankAccountRepository.findById(loginReqDto.getAccNo());
        if(!account.isPresent()){
            throw new ResourceNotFoundException("Invalid account number!");
        }
            User user = account.get().getUser();
        if(!user.getEmail().equals(loginReqDto.getEmail()) || !user.getPassword().equals(loginReqDto.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials!");
        }
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
            throw new ResourceNotFoundException("Invalid credentials!");
        }
        UserResponseDto user = new UserResponseDto();
        user.setId(account.getUser().getId());
        user.setName(account.getUser().getName());
        user.setEmail(account.getUser().getEmail());
        user.setAge(account.getUser().getAge());
        return user;
    }

    @Override
    public UserResponseDto updateUser(Long id, UpdateUserReq updateReqUser) {
        Optional<User> user = userRepo.findById(id);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User not found!");
        }
        user.get().setName(updateReqUser.getName());
        user.get().setEmail(updateReqUser.getEmail());
        user.get().setPassword(updateReqUser.getPassword());
        user.get().setAge(updateReqUser.getAge());
        user.get().setUpdatedAt(LocalDateTime.now());
        userRepo.save(user.get());
        return mapToDto(user.get());
    }

    @Override
    public UserResponseDto updateDetailsUser(Long id, UpdateUserReq updateReqUser) {
        Optional<User> user = userRepo.findById(id);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User not found!");
        }
        if(updateReqUser.getAge() < 0){
            throw new InvalidAgeException("Age cannot be negative!");
        }
        if(updateReqUser.getName()!= null){
            user.get().setName(updateReqUser.getName());
        }
        if(updateReqUser.getEmail() != null){
            user.get().setEmail(updateReqUser.getEmail());
        }
        if(updateReqUser.getPassword()!= null){
            user.get().setPassword(updateReqUser.getPassword());
        }
        if(updateReqUser.getAge() != 0){
            user.get().setAge(updateReqUser.getAge());
        }
        user.get().setUpdatedAt(LocalDateTime.now());
        userRepo.save(user.get());
        return mapToDto(user.get());
    }
}
