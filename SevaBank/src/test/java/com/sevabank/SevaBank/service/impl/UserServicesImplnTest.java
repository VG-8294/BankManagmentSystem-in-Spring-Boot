package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.dto.request.LoginReqDto;
import com.sevabank.SevaBank.dto.request.RegisterReqDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServicesImplnTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private UserServicesImpln userServices;

    private RegisterReqDto registerReqDto;
    private LoginReqDto loginReqDto;
    private User user;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {

        registerReqDto = new RegisterReqDto();
        registerReqDto.setName("Vishal");
        registerReqDto.setEmail("vishal@gmail.com");
        registerReqDto.setPassword("Password@123");
        registerReqDto.setAge(23);

        loginReqDto = new LoginReqDto();
        loginReqDto.setAccNo(1001L);
        loginReqDto.setEmail("vishal@gmail.com");
        loginReqDto.setPassword("Password@123");

        user = new User("Vishal",
                "vishal@gmail.com",
                "Password@123",
                23);

        bankAccount = new BankAccount();
        bankAccount.setAccNo(1001L);
        bankAccount.setUser(user);
    }

    @Test
    void shouldCreateUserSuccessfully() {

        when(userRepo.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto response = userServices.createUser(registerReqDto);

        assertNotNull(response);
        assertEquals("Vishal", response.getName());
        assertEquals("vishal@gmail.com", response.getEmail());

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void shouldLoginSuccessfully() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(bankAccount));

        Boolean result = userServices.login(loginReqDto);

        assertTrue(result);

        verify(bankAccountRepository).findById(1001L);
    }

    @Test
    void shouldReturnFalseWhenAccountDoesNotExist() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.empty());

        Boolean result = userServices.login(loginReqDto);

        assertFalse(result);

        verify(bankAccountRepository).findById(1001L);
    }

    @Test
    void shouldReturnFalseForWrongPassword() {

        loginReqDto.setPassword("WrongPassword");

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(bankAccount));

        Boolean result = userServices.login(loginReqDto);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForWrongEmail() {

        loginReqDto.setEmail("wrong@gmail.com");

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(bankAccount));

        Boolean result = userServices.login(loginReqDto);

        assertFalse(result);
    }

    @Test
    void shouldLoginSuccessfullyUsingRepositoryMethod() {

        when(bankAccountRepository.existsByAccNoAndUserEmailAndUserPassword(
                1001L,
                "vishal@gmail.com",
                "Password@123"))
                .thenReturn(true);

        Boolean result = userServices.loginV1(loginReqDto);

        assertTrue(result);

        verify(bankAccountRepository)
                .existsByAccNoAndUserEmailAndUserPassword(
                        1001L,
                        "vishal@gmail.com",
                        "Password@123");
    }

    @Test
    void shouldFailLoginV1() {

        when(bankAccountRepository.existsByAccNoAndUserEmailAndUserPassword(
                (long) anyLong(),
                anyString(),
                anyString()))
                .thenReturn(false);

        Boolean result = userServices.loginV1(loginReqDto);

        assertFalse(result);
    }
}