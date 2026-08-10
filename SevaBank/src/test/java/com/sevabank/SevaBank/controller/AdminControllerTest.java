package com.sevabank.SevaBank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevabank.SevaBank.dto.request.AgeReqDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.service.AdminServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminServices adminService;

    @Autowired
    private ObjectMapper objectMapper;


    // =========================================================
    // GET ALL USERS
    // =========================================================

    @Test
    void shouldGetAllUsers() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getAllUsers())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getAllUsers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Vishal"))
                .andExpect(jsonPath("$[0].email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // GET USERS LESS THAN BALANCE
    // =========================================================

    @Test
    void shouldGetUsersLessThanBalance() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getUsersLessThanBal(50000.0))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersLessThanBal/50000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"))
                .andExpect(jsonPath("$[0].email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // GET USERS LESS THAN BALANCE V1
    // =========================================================

    @Test
    void shouldGetUsersLessThanBalanceV1() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getUsersLessThanBalV1(50000.0))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/v1/getUsersThanBal/50000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS HAVING SAVING ACCOUNT
    // =========================================================

    @Test
    void shouldGetUsersHavingSaving() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUsersHavingSaving())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersHavingSaving")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS HAVING CURRENT ACCOUNT
    // =========================================================

    @Test
    void shouldGetUsersHavingCurrent() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUsersHavingCurrent())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersHavingCurrent")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET OLD AGE USERS
    // =========================================================

    @Test
    void shouldGetOldAgeUsers() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getOldAgeUsers())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getOldAgeUsers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET OLD AGE USERS V1
    // =========================================================

    @Test
    void shouldGetOldAgeUsersV1() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getOldAgeUsersV1())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/v1/getOldAgeUsers")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }


    // =========================================================
    // GET USER BY EMAIL
    // =========================================================

    @Test
    void shouldGetUserByEmail() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getUsersByEmail("vishal@gmail.com"))
                .thenReturn(user);

        mockMvc.perform(
                        get("/api/admin/getUsersByEmail/vishal@gmail.com")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Vishal"))
                .andExpect(jsonPath("$.email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // GET ALL USER EMAILS
    // =========================================================

    @Test
    void shouldGetAllUsersEmail() throws Exception {

        when(adminService.getAllUsersEmail())
                .thenReturn(Arrays.asList(
                        "vishal@gmail.com",
                        "rahul@gmail.com"
                ));

        mockMvc.perform(
                        get("/api/admin/getAllUsersEmail")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]")
                        .value("vishal@gmail.com"))
                .andExpect(jsonPath("$[1]")
                        .value("rahul@gmail.com"));
    }


    // =========================================================
    // GET TOTAL NUMBER OF ACCOUNTS
    // =========================================================

    @Test
    void shouldGetTotalNumberOfAccounts() throws Exception {

        when(adminService.getTotalNoAcc())
                .thenReturn(10);

        mockMvc.perform(
                        get("/api/admin/getTotalNoAcc")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }


    // =========================================================
    // GET TOTAL NUMBER OF ACCOUNTS V1
    // =========================================================

    @Test
    void shouldGetTotalNumberOfAccountsV1() throws Exception {

        when(adminService.getTotalNoAccV1())
                .thenReturn(10L);

        mockMvc.perform(
                        get("/api/admin/v1/getTotalNoAcc")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }


    // =========================================================
    // GET TOTAL MONEY
    // =========================================================

    @Test
    void shouldGetTotalMoney() throws Exception {

        when(adminService.getTotalMoneyInBank())
                .thenReturn(1500000.0);

        mockMvc.perform(
                        get("/api/admin/getTotalMoney")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("1500000.0"));
    }


    // =========================================================
    // GET USER WITH MAX BALANCE
    // =========================================================

    @Test
    void shouldGetUserWithMaxBalance() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getUserWithMaxBal())
                .thenReturn(user);

        mockMvc.perform(
                        get("/api/admin/getUserWithMaxBal")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Vishal"))
                .andExpect(jsonPath("$.email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // GET USERS OVER SPECIFIC BALANCE
    // =========================================================

    @Test
    void shouldGetUsersOverSpecificBalance() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserOverSpecificBal(100000.0))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersOverCertainBal/100000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS OVER SPECIFIC BALANCE V1
    // =========================================================

    @Test
    void shouldGetUsersOverSpecificBalanceV1()
            throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserOverSpecificBalV1(100000.0))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/v1/getUsersOverCertainBal/100000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS ABOVE AGE
    // =========================================================

    @Test
    void shouldGetUsersAboveAge() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserAboveAge(50))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersAboveSomeAge/50")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS ABOVE AGE V1
    // =========================================================

    @Test
    void shouldGetUsersAboveAgeV1() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserAboveAgeV1(50))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/v1/getUsersAboveSomeAge/50")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USER BY ACCOUNT NUMBER
    // =========================================================

    @Test
    void shouldGetUserByAccountNumber() throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");
        user.setEmail("vishal@gmail.com");

        when(adminService.getUserByAccNo(1001L))
                .thenReturn(user);

        mockMvc.perform(
                        get("/api/admin/getUserByAccNo/1001")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Vishal"))
                .andExpect(jsonPath("$.email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // GET USER BY ACCOUNT NUMBER V1
    // =========================================================

    @Test
    void shouldGetUserByAccountNumberV1()
            throws Exception {

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserByAccNoV1(1001L))
                .thenReturn(user);

        mockMvc.perform(
                        get("/api/admin/v1/getUserByAccNo/1001")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET USERS BETWEEN AGE
    // =========================================================

    @Test
    void shouldGetUsersBetweenAge() throws Exception {

        AgeReqDto request = new AgeReqDto();

        request.setAge1(20);
        request.setAge2(40);

        UserResponseDto user = new UserResponseDto();
        user.setId(1L);
        user.setName("Vishal");

        when(adminService.getUserBwAge(any(AgeReqDto.class)))
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(
                        get("/api/admin/getUsersBwAge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Vishal"));
    }


    // =========================================================
    // GET ALL BANK ACCOUNTS
    // =========================================================

    @Test
    void shouldGetAllBankAccounts() throws Exception {

        BankAccountResponseDto account =
                new BankAccountResponseDto();

        account.setAccNo(1001L);
        account.setUser_name("Vishal");
        account.setEmail("vishal@gmail.com");

        when(adminService.getAllBankAccounts())
                .thenReturn(Arrays.asList(account));

        mockMvc.perform(
                        get("/api/admin/getAllBankAccounts")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accNo")
                        .value(1001))
                .andExpect(jsonPath("$[0].user_name")
                        .value("Vishal"))
                .andExpect(jsonPath("$[0].email")
                        .value("vishal@gmail.com"));
    }


    // =========================================================
    // DELETE ACCOUNT - SUCCESS
    // =========================================================

    @Test
    void shouldDeleteAccountSuccessfully()
            throws Exception {

        when(adminService.deleteAccountById(1001L))
                .thenReturn(true);

        mockMvc.perform(
                        get("/api/admin/deleteAccount/1001")
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string("Account deleted")
                );
    }


    // =========================================================
    // DELETE ACCOUNT - FAILURE
    // =========================================================

    @Test
    void shouldFailDeleteAccount() throws Exception {

        when(adminService.deleteAccountById(1001L))
                .thenReturn(false);

        mockMvc.perform(
                        get("/api/admin/deleteAccount/1001")
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string("Account not found!")
                );
    }
}