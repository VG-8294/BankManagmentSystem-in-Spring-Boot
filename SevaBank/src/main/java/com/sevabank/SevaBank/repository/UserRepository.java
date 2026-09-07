package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.EmailResDto;
import com.sevabank.SevaBank.entity.User;

import java.util.List;

public interface UserRepository {
    void createUser(User user);

    boolean existsByEmail(String email);

    List<User> findById(Long userId);

    boolean updateUser(User user);

    List<User> findAll();

    List<User> findByEmail(String email);

    List<User> findUsersWithMultipleAccounts();

    List<User> findByAgeGreaterThanEqual(int i);

    List<User> findOldAgeUsers();

    List<EmailResDto> findEmails();

    List<User> findUsersBwAge(int age1, int age2);
}
