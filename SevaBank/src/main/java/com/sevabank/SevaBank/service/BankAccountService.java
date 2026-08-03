package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createBankAccount(BankAccount bankAccount){
        BankAccount createdBankAccount = new BankAccount(bankAccount.checkBalance(), bankAccount.getAccountType());
        bankAccountRepository.save(createdBankAccount);
        return createdBankAccount;
    }
}
