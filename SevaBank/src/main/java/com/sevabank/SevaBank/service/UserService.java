package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.dto.LoginReqDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final BankAccountRepository bankAccountRepository;

    public UserService(UserRepository userRepo, BankAccountRepository bankAccountRepository) {
        this.userRepo = userRepo;
        this.bankAccountRepository = bankAccountRepository;
    }

    public User createUser(User user){
        User newUser = new User(user.getName(), user.getEmail(), user.getPassword(), user.getAge());
        userRepo.save(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }


    public Boolean deleteUserById(Long id) {
        boolean isUser = userRepo.existsById(id);
        if(!isUser){
            return false;
        }
        userRepo.deleteById(id);
        return true;
    }
    public User updateUserById(Long id, User user) {
        Optional<User> existingUser = userRepo.findById(id);
        if(!existingUser.isPresent()){
            return null;
        }
        User userToSave = existingUser.get();
        userToSave.setName(user.getName());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(user.getPassword());
        userToSave.setAge(user.getAge());
        userRepo.save(userToSave);
        return userToSave;
    }

    public Boolean login(LoginReqDto loginReqDto) {
        Optional<BankAccount> account = bankAccountRepository.findById(loginReqDto.getAccNo());
        if(!account.isPresent()){
            return false;
        }
        User user = account.get().getUser();
        return user.getEmail().equals(loginReqDto.getEmail()) && user.getPassword().equals(loginReqDto.getPassword());
    }

    public Boolean loginV1(LoginReqDto loginReqDto) {
        return bankAccountRepository.existsByAccNoAndUserEmailAndUserPassword(
                loginReqDto.getAccNo(),
                loginReqDto.getEmail(),
                loginReqDto.getPassword()
        );
    }
}
