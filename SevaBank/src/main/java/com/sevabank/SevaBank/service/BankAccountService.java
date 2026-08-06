package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.CreateBankAccountRequest;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.Transaction;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.TransactionRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    TransactionRepository transactionRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    private void saveTransaction(BankAccount account, TransactionType transactionType, double amount, double currBal){
        Transaction tx = new Transaction();
        tx.setBankAccount(account);
        tx.setAmount(amount);
        tx.setBalanceAfterTransaction(currBal);
        tx.setTransactionType(transactionType);
        tx.setTransactionTime(LocalDateTime.now());

        transactionRepository.save(tx);
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
        
        if(!bankAccount.isPresent()){
            return null;
        }
        return bankAccount;
    }

    public List<BankAccount> getAllBankAccounts(){
        return bankAccountRepository.findAll();
    }

    public Boolean deleteAccountById(Long id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if(!account.isPresent()){
            return null;
        }
        BankAccount accountToDel = account.get();
        accountToDel.setDeleted(true);
        bankAccountRepository.save(accountToDel);
        return accountToDel.getDeleted();
    }

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


    public Double checkBalance(Long id) {
        Optional<BankAccount> accountExist = bankAccountRepository.findById(id);
        if(!accountExist.isPresent()){
            return null;
        }
        BankAccount accountToExist = accountExist.get();
        return accountToExist.getBalance();
    }

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
