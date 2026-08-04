package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.dto.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

        System.out.println(bankReq.getInterestRate());
        System.out.println(bankReq.getOverdraftLimit());

        BankAccount createdBankAccount = new BankAccount(bankReq.getBalance(), type, bankReq.getInterestRate(), bankReq.getOverdraftLimit());
        createdBankAccount.setUser(user);
        bankAccountRepository.save(createdBankAccount);
        return createdBankAccount;
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        
        if(bankAccount.isEmpty()){
            return null;
        }
        return bankAccount;
    }

    public List<BankAccount> getAllBankAccounts(){
        return bankAccountRepository.findAll();
    }

    public Boolean deleteAccountById(Long id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if(account.isEmpty()){
            return null;
        }
        BankAccount accountToDel = account.get();
        accountToDel.setDeleted(true);
        bankAccountRepository.save(accountToDel);
        return accountToDel.getDeleted();
    }
}
