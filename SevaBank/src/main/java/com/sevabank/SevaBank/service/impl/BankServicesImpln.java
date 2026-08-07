package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.Transaction;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.TransactionRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import com.sevabank.SevaBank.service.BankServices;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BankServicesImpln implements BankServices {

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    TransactionRepository transactionRepository;

    public BankServicesImpln(BankAccountRepository bankAccountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(BankAccount account, TransactionType transactionType, double amount, double currBal){
        Transaction tx = new Transaction();
        tx.setBankAccount(account);
        tx.setAmount(amount);
        tx.setBalanceAfterTransaction(currBal);
        tx.setTransactionType(transactionType);
        tx.setTransactionTime(LocalDateTime.now());

        transactionRepository.save(tx);
    }


    @Override
    public BankAccountResponseDto createBankAccount(CreateBankAccountRequest bankReq) {

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
        BankAccountResponseDto dto = new BankAccountResponseDto();
        dto.setAccNo(createdBankAccount.getAccNo());
        dto.setUser_name(createdBankAccount.getUser().getName());
        dto.setEmail(createdBankAccount.getUser().getEmail());
        dto.setAccountType(createdBankAccount.getAccountType());
        return dto;
    }

    @Override
    public Optional<BankAccount> getBankAccountById(Long id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        
        if(!bankAccount.isPresent()){
            return null;
        }
        return bankAccount;
    }

    @Override
    public Boolean depositInAccount(Long id, double balance) {
        Boolean isAmountDep = bankAccountRepository.existsById(id);
        if(!isAmountDep){
            return false;
        }
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        BankAccount accountInDep = account.get();
        accountInDep.deposit(balance);
        bankAccountRepository.save(accountInDep);
        saveTransaction(accountInDep, TransactionType.DEPOSIT, balance, accountInDep.getBalance());
        return true;
    }



    @Override
    public Boolean withdrawInAccount(Long id, double balance) {
        Boolean isAmountDep = bankAccountRepository.existsById(id);
        if(!isAmountDep){
            return false;
        }
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        BankAccount accountInDep = account.get();
        accountInDep.withdraw(balance);
        bankAccountRepository.save(accountInDep);
        saveTransaction(accountInDep, TransactionType.WITHDRAW, balance, accountInDep.getBalance());
        return true;
    }


    @Override
    public Double checkBalance(Long id) {
        Optional<BankAccount> accountExist = bankAccountRepository.findById(id);
        if(!accountExist.isPresent()){
            return null;
        }
        BankAccount accountToExist = accountExist.get();
        return accountToExist.getBalance();
    }

    @Override
    public Double calculateInterest(Long id) {
        Optional<BankAccount> accountExist = bankAccountRepository.findById(id);
        if(!accountExist.isPresent()){
            return null;
        }
        BankAccount accountToExist = accountExist.get();
        saveTransaction(accountToExist, TransactionType.INTEREST, accountToExist.calculateInt(), accountToExist.getBalance());
        return accountToExist.calculateInt();
    }
}
