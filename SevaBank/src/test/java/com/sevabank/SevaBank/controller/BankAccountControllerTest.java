package com.sevabank.SevaBank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.request.BalanceReq;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.service.BankServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankServices bankAccountService;

    @Autowired
    private ObjectMapper objectMapper;


    // =========================================================
    // CREATE BANK ACCOUNT
    // =========================================================

    @Test
    void shouldCreateBankAccountSuccessfully() throws Exception {

        CreateBankAccountRequest request =
                new CreateBankAccountRequest();

        request.setUserId(1L);
        request.setBalance(50000);
        request.setAccountType(AccountType.SAVING);
        request.setInterestRate(4.5);
        request.setOverdraftLimit(null);

        BankAccountResponseDto response =
                new BankAccountResponseDto();

        response.setAccNo(1001L);
        response.setUser_name("Vishal");
        response.setEmail("vishal@gmail.com");
        response.setAccountType(AccountType.SAVING);

        when(bankAccountService.createBankAccount(
                any(CreateBankAccountRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/bankAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accNo").value(1001))
                .andExpect(jsonPath("$.user_name").value("Vishal"))
                .andExpect(jsonPath("$.email")
                        .value("vishal@gmail.com"))
                .andExpect(jsonPath("$.accountType")
                        .value("SAVING"));
    }


    // =========================================================
    // DEPOSIT - SUCCESS
    // =========================================================

    @Test
    void shouldDepositSuccessfully() throws Exception {

        BalanceReq request = new BalanceReq();
        request.setBalance(5000);

        when(bankAccountService.depositInAccount(1001L, 5000))
                .thenReturn(true);

        mockMvc.perform(
                        post("/api/bankAccount/deposit/1001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string("Amount deposited!")
                );
    }


    // =========================================================
    // DEPOSIT - FAILURE
    // =========================================================

    @Test
    void shouldFailDeposit() throws Exception {

        BalanceReq request = new BalanceReq();
        request.setBalance(5000);

        when(bankAccountService.depositInAccount(1001L, 5000))
                .thenReturn(false);

        mockMvc.perform(
                        post("/api/bankAccount/deposit/1001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                "Not able to deposit your amount"
                        )
                );
    }


    // =========================================================
    // WITHDRAW - SUCCESS
    // =========================================================

    @Test
    void shouldWithdrawSuccessfully() throws Exception {

        BalanceReq request = new BalanceReq();
        request.setBalance(5000);

        when(bankAccountService.withdrawInAccount(1001L, 5000))
                .thenReturn(true);

        mockMvc.perform(
                        post("/api/bankAccount/withdraw/1001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                "Amount withdrawal successful!"
                        )
                );
    }


    // =========================================================
    // WITHDRAW - FAILURE
    // =========================================================

    @Test
    void shouldFailWithdraw() throws Exception {

        BalanceReq request = new BalanceReq();
        request.setBalance(5000);

        when(bankAccountService.withdrawInAccount(1001L, 5000))
                .thenReturn(false);

        mockMvc.perform(
                        post("/api/bankAccount/withdraw/1001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                "Not able to withdraw your amount"
                        )
                );
    }


    // =========================================================
    // CHECK BALANCE - SUCCESS
    // =========================================================

    @Test
    void shouldCheckBalanceSuccessfully() throws Exception {

        when(bankAccountService.checkBalance(1001L))
                .thenReturn(50000.0);

        mockMvc.perform(
                        get("/api/bankAccount/balance/1001")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("50000.0"));
    }


    // =========================================================
    // CHECK BALANCE - NULL
    // =========================================================

    @Test
    void shouldReturnNullWhenAccountDoesNotExistForBalance()
            throws Exception {

        when(bankAccountService.checkBalance(1001L))
                .thenReturn(null);

        mockMvc.perform(
                        get("/api/bankAccount/balance/1001")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    // =========================================================
    // CALCULATE INTEREST - SUCCESS
    // =========================================================

    @Test
    void shouldCalculateInterestSuccessfully()
            throws Exception {

        when(bankAccountService.calculateInterest(1001L))
                .thenReturn(2250.0);

        mockMvc.perform(
                        get("/api/bankAccount/interest/1001")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("2250.0"));
    }


    // =========================================================
    // CALCULATE INTEREST - NULL
    // =========================================================

    @Test
    void shouldReturnNullWhenAccountDoesNotExistForInterest()
            throws Exception {

        when(bankAccountService.calculateInterest(1001L))
                .thenReturn(null);

        mockMvc.perform(
                        get("/api/bankAccount/interest/1001")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}