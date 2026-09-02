package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.BalanceResDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.InterestResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;

import java.util.Optional;

public interface BankServices {
    void saveTransaction(BankAccount account, TransactionType transactionType, double amount, double currBal);

    BankAccountResponseDto createBankAccount(CreateBankAccountRequest bankReq);

//    Optional<BankAccount> getBankAccountById(Long id);
//
    BankAccountResponseDto depositInAccount(Long id, double balance);
//
    BankAccountResponseDto withdrawInAccount(Long id, double balance);
//
    BalanceResDto checkBalance(Long id);
//
    InterestResponseDto calculateInterest(Long id);
}
