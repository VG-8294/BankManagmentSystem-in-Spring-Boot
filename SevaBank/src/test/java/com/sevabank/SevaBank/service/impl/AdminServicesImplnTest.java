//package com.sevabank.SevaBank.service.impl;
//
//import com.sevabank.SevaBank.Enum.AccountType;
//import com.sevabank.SevaBank.dto.response.UserResponseDto;
//import com.sevabank.SevaBank.entity.BankAccount;
//import com.sevabank.SevaBank.entity.User;
//import com.sevabank.SevaBank.repository.BankAccountRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AdminServicesImplnTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BankAccountRepository bankAccountRepository;
//
//    @InjectMocks
//    private AdminServicesImpln adminServices;
//
//    private User user1;
//    private User user2;
//
//    private BankAccount account1;
//    private BankAccount account2;
//
//    @BeforeEach
//    void setUp() {
//
//        user1 = new User();
//        user1.setId(1L);
//        user1.setName("Vishal");
//        user1.setEmail("vishal@gmail.com");
//        user1.setAge(25);
//
//        user2 = new User();   // <-- This is missing
//        user2.setId(2L);
//        user2.setName("Rahul");
//        user2.setEmail("rahul@gmail.com");
//        user2.setAge(65);
//    }
//
//    @Test
//    void shouldReturnAllUsers() {
//
//        when(userRepository.findAll())
//                .thenReturn(Arrays.asList(user1, user2));
//
//        List<UserResponseDto> result =
//                adminServices.getAllUsers();
//
//        assertEquals(2, result.size());
//        assertEquals("Vishal", result.get(0).getName());
//        assertEquals("Rahul", result.get(1).getName());
//
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnUserByEmail() {
//
//        when(userRepository.findByEmail("vishal@gmail.com"))
//                .thenReturn(Optional.of(user1));
//
//        UserResponseDto dto = adminServices.getUsersByEmail("vishal@gmail.com");
//
//        assertNotNull(dto);
//        assertEquals("Vishal", dto.getName());
//        assertEquals("vishal@gmail.com", dto.getEmail());
//
//        verify(userRepository).findByEmail("vishal@gmail.com");
//    }
//
//    @Test
//    void shouldReturnNullIfEmailNotFound() {
//
//        when(userRepository.findByEmail("abc@gmail.com"))
//                .thenReturn(Optional.empty());
//
//        UserResponseDto dto =
//                adminServices.getUsersByEmail("abc@gmail.com");
//
//        assertNull(dto);
//
//        verify(userRepository).findByEmail("abc@gmail.com");
//    }
//
//    @Test
//    void shouldReturnAllEmails() {
//
//        when(userRepository.findAll())
//                .thenReturn(Arrays.asList(user1, user2));
//
//        List<String> emails =
//                adminServices.getAllUsersEmail();
//
//        assertEquals(2, emails.size());
//
//        assertTrue(emails.contains("vishal@gmail.com"));
//        assertTrue(emails.contains("rahul@gmail.com"));
//
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnOldAgeUsers() {
//
//        user2.setAge(65);
//
//        when(userRepository.findByAgeGreaterThanEqual(60))
//                .thenReturn(Arrays.asList(user2));
//
//        List<UserResponseDto> result = adminServices.getOldAgeUsersV1();
//
//        assertEquals(1, result.size());
//        assertEquals("Rahul", result.get(0).getName());
//
//        verify(userRepository).findByAgeGreaterThanEqual(60);
//    }
//
//    @Test
//    void shouldReturnUserWithMaximumBalance() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        UserResponseDto result =
//                adminServices.getUserWithMaxBal();
//
//        assertNotNull(result);
//        assertEquals("Rahul", result.getName());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnUsersAboveSpecificBalance() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUserOverSpecificBal(60000.0);
//
//        assertEquals(1, result.size());
//        assertEquals("Rahul", result.get(0).getName());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnEmptyWhenNoUserAboveSpecificBalance() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUserOverSpecificBal(100000.0);
//
//        assertTrue(result.isEmpty());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnUsersBelowSpecificBalance() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUsersLessThanBal(60000.0);
//
//        assertEquals(1, result.size());
//        assertEquals("Vishal", result.get(0).getName());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnSavingAccountUsers() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUsersHavingSaving();
//
//        assertEquals(1, result.size());
//        assertEquals("Vishal", result.get(0).getName());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnCurrentAccountUsers() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUsersHavingCurrent();
//
//        assertEquals(1, result.size());
//        assertEquals("Rahul", result.get(0).getName());
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnTotalMoneyInBank() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        Double total =
//                adminServices.getTotalMoneyInBank();
//
//        assertEquals(140000, total);
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnTotalNumberOfAccounts() {
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        Integer total =
//                adminServices.getTotalNoAcc();
//
//        assertEquals(2, total);
//
//        verify(bankAccountRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnEmptyListWhenNoUsersExist() {
//
//        when(userRepository.findAll())
//                .thenReturn(Collections.emptyList());
//
//        List<UserResponseDto> result =
//                adminServices.getAllUsers();
//
//        assertTrue(result.isEmpty());
//
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void shouldReturnEmptySavingAccountList() {
//
//        account1.setAccountType(AccountType.CURRENT);
//
//        when(bankAccountRepository.findAll())
//                .thenReturn(Arrays.asList(account1, account2));
//
//        List<UserResponseDto> result =
//                adminServices.getUsersHavingSaving();
//
//        assertTrue(result.isEmpty());
//
//        verify(bankAccountRepository).findAll();
//    }
//}