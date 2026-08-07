package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import com.sevabank.SevaBank.service.UserServices;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpln implements UserServices {

    private final UserRepository userRepo;
    private final BankAccountRepository bankAccountRepository;

    public UserServicesImpln(UserRepository userRepo, BankAccountRepository bankAccountRepository) {
        this.userRepo = userRepo;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public UserResponseDto createUser(RegisterReqDto dto){
        User newUser = new User(dto.getName(),dto.getEmail(), dto.getPassword(), dto.getAge());
        userRepo.save(newUser);
        UserResponseDto createdDto = new UserResponseDto();
        createdDto.setId(newUser.getId());
        createdDto.setName(newUser.getName());
        createdDto.setEmail(newUser.getEmail());
        return createdDto;
    }

    @Override
    public Boolean login(LoginReqDto loginReqDto) {
        Optional<BankAccount> account = bankAccountRepository.findById(loginReqDto.getAccNo());
        if(!account.isPresent()){
            return false;
        }
        User user = account.get().getUser();
        return user.getEmail().equals(loginReqDto.getEmail()) && user.getPassword().equals(loginReqDto.getPassword());
    }

    @Override
    public Boolean loginV1(LoginReqDto loginReqDto) {
        return bankAccountRepository.existsByAccNoAndUserEmailAndUserPassword(
                loginReqDto.getAccNo(),
                loginReqDto.getEmail(),
                loginReqDto.getPassword()
        );
    }
}
