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
        userRepo.save(newUser);
        return mapToDto(newUser);
    }

    @Override
    public UserResponseDto login(LoginReqDto loginReqDto) {
        Optional<BankAccount> account = bankAccountRepository.findById(loginReqDto.getAccNo());
        if(!account.isPresent()){
            return null;
        }
            User user = account.get().getUser();
            return mapToDto(user);
    }

    @Override
    public UserResponseDto loginV1(LoginReqDto loginReqDto) {
        User user =  bankAccountRepository.findByAccNoAndUserEmailAndUserPassword(
                    loginReqDto.getAccNo(),
                    loginReqDto.getEmail(),
                    loginReqDto.getPassword()
        );
        return mapToDto(user);
    }
}
