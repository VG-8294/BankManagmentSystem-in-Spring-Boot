package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public BankAccount createBankAccount(CreateBankAccountRequest bankReq) {

        User user = userRepository.findById(bankReq.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String type;
        if (bankReq.getAccountType() == AccountType.SAVING) {
            type = "SAVING";
        } else {
            type = "CURRENT";
        }

        BankAccount createdBankAccount = new BankAccount(bankReq.getBalance(), type);
        createdBankAccount.setUser(user);
        bankAccountRepository.save(createdBankAccount);
        return createdBankAccount;
    }
}
