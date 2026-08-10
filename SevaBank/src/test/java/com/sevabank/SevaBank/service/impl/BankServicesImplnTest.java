package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.TransactionRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServicesImplnTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankServicesImpln bankServices;

    private User user;
    private BankAccount account;
    private CreateBankAccountRequest request;

    @BeforeEach
    void setUp() {

        user = new User("Vishal",
                "vishal@gmail.com",
                "Password@123",
                23);

        user.setId(1L);

        account = new BankAccount(10000, "SAVING", 4.5, null);
        account.setAccNo(1001L);
        account.setUser(user);

        request = new CreateBankAccountRequest();
        request.setUserId(1L);
        request.setBalance(10000);
        request.setAccountType(AccountType.SAVING);
        request.setInterestRate(4.5);
        request.setOverdraftLimit(null);
    }

    @Test
    void shouldCreateBankAccountSuccessfully() {

        // Arrange
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> {
                    BankAccount account = invocation.getArgument(0);
                    account.setAccNo(1001L);      // Simulate DB-generated ID
                    return account;
                });

        // Act
        BankAccountResponseDto response = bankServices.createBankAccount(request);

        // Assert
        assertNotNull(response);
        assertEquals(1001L, response.getAccNo());
        assertEquals("Vishal", response.getUser_name());
        assertEquals("vishal@gmail.com", response.getEmail());
        assertEquals(AccountType.SAVING, response.getAccountType());

        verify(userRepository, times(1)).findById(1L);
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bankServices.createBankAccount(request));

        assertEquals("User not found", ex.getMessage());

        verify(bankAccountRepository, never()).save(any());
    }

    @Test
    void shouldReturnBankAccountWhenFound() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(account));

        Optional<BankAccount> result = bankServices.getBankAccountById(1001L);

        assertTrue(result.isPresent());
        assertEquals(1001, result.get().getAccNo());
    }

    @Test
    void shouldDepositMoneySuccessfully() {

        when(bankAccountRepository.existsById(1001L))
                .thenReturn(true);

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(account));

        boolean result = bankServices.depositInAccount(1001L, 5000);

        assertTrue(result);
        assertEquals(15000, account.getBalance());

        verify(bankAccountRepository).save(account);
        verify(transactionRepository).save(any());
    }

    @Test
    void shouldReturnFalseWhenDepositingIntoInvalidAccount() {

        when(bankAccountRepository.existsById(1001L))
                .thenReturn(false);

        boolean result = bankServices.depositInAccount(1001L, 5000);

        assertFalse(result);

        verify(bankAccountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldWithdrawMoneySuccessfully() {

        when(bankAccountRepository.existsById(1001L))
                .thenReturn(true);

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(account));

        boolean result = bankServices.withdrawInAccount(1001L, 2000);

        assertTrue(result);
        assertEquals(8000, account.getBalance());

        verify(bankAccountRepository).save(account);
        verify(transactionRepository).save(any());
    }

    @Test
    void shouldReturnFalseWhenWithdrawingFromInvalidAccount() {

        when(bankAccountRepository.existsById(1001L))
                .thenReturn(false);

        boolean result = bankServices.withdrawInAccount(1001L, 2000);

        assertFalse(result);

        verify(bankAccountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldReturnBalance() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(account));

        Double balance = bankServices.checkBalance(1001L);

        assertEquals(10000, balance);
    }

    @Test
    void shouldReturnNullWhenBalanceAccountNotFound() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.empty());

        Double balance = bankServices.checkBalance(1001L);

        assertNull(balance);
    }

    @Test
    void shouldCalculateInterest() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.of(account));

        Double interest = bankServices.calculateInterest(1001L);

        assertNotNull(interest);

        verify(transactionRepository).save(any());
    }

    @Test
    void shouldReturnNullWhenCalculatingInterestForInvalidAccount() {

        when(bankAccountRepository.findById(1001L))
                .thenReturn(Optional.empty());

        Double interest = bankServices.calculateInterest(1001L);

        assertNull(interest);

        verify(transactionRepository, never()).save(any());
    }
}