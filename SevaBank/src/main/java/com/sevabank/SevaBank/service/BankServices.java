package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;

import java.util.Optional;

public interface BankServices {
    void saveTransaction(BankAccount account, TransactionType transactionType, double amount, double currBal);

    BankAccountResponseDto createBankAccount(CreateBankAccountRequest bankReq);

    Optional<BankAccount> getBankAccountById(Long id);

    Boolean depositInAccount(Long id, double balance);

    Boolean withdrawInAccount(Long id, double balance);

    Double checkBalance(Long id);

    Double calculateInterest(Long id);
}
