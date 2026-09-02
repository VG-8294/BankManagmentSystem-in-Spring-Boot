package com.sevabank.SevaBank.service.impl;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.BalanceResDto;
import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.request.CreateBankAccountRequest;
import com.sevabank.SevaBank.dto.response.InterestResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.Transaction;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.exception.BalanceException;
import com.sevabank.SevaBank.exception.InvalidAccountTypeException;
import com.sevabank.SevaBank.exception.InvalidAmountException;
import com.sevabank.SevaBank.exception.ResourceNotFoundException;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.TransactionRepository;
import com.sevabank.SevaBank.repository.UserRepository;
import com.sevabank.SevaBank.service.BankServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankServicesImpln implements BankServices {

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    TransactionRepository transactionRepository;

    public BankServicesImpln(BankAccountRepository bankAccountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    private BankAccountResponseDto bankAccountToDto(BankAccount bankAccount){
        BankAccountResponseDto dto = new BankAccountResponseDto();
        dto.setAccNo(bankAccount.getAccNo());
        dto.setUser_name(bankAccount.getUser().getName());
        dto.setEmail(bankAccount.getUser().getEmail());
        dto.setAccountType(bankAccount.getAccountType());
        dto.setBalance(bankAccount.getBalance());
        log.info("normal bank class to dto converted");
        return dto;
    }

    @Override
    public void saveTransaction(BankAccount account, TransactionType transactionType, double amount, double currBal){
        Transaction tx = new Transaction();
        tx.setBankAccount(account);
        tx.setAmount(amount);
        tx.setBalanceAfterTransaction(currBal);
        tx.setTransactionType(transactionType);
        tx.setTransactionTime(LocalDateTime.now());
        log.info("transaction saved!");
        transactionRepository.save(tx);
    }


    @Override
    public BankAccountResponseDto createBankAccount(CreateBankAccountRequest bankReq) {
        log.info("Creating bank acount with user-id-{}", bankReq.getUserId());
        Optional<User> user = Optional.of(userRepository.findById(bankReq.getUserId())
                .stream()
                .filter(x -> x.getId() == bankReq.getUserId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found")));

        if(!user.isPresent()){
            log.error("user record doesn't exist with id-{}", bankReq.getUserId());
            throw new ResourceNotFoundException("User doesn't exist!");
        }

        AccountType type;
        if (bankReq.getAccountType().equals("SAVING")) {
            type = AccountType.SAVING;
        } else if(bankReq.getAccountType().equals("CURRENT")){
            type = AccountType.CURRENT;
        }
        else{
            log.error("accountType is not valid");
            throw new InvalidAccountTypeException("This type of account doesn't exist!");
        }
        BankAccount createdBankAccount = new BankAccount(bankReq.getBalance(), type);
        createdBankAccount.setUser(user.get());
        bankAccountRepository.createAccount(createdBankAccount);
        BankAccount account = bankAccountRepository.findAccountByUserId(bankReq.getUserId())
                .stream()
                .findFirst()
                        .orElseThrow(() -> new RuntimeException("Account not created due to some error"));
        log.info("Bank account created by user with user-id -{} with account number - {}", user.get().getId(), account.getAccNo());
        return bankAccountToDto(account);
    }
//
//    @Override
//    public Optional<BankAccount> getBankAccountById(Long id) {
//        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
//
//        if(!bankAccount.isPresent()){
//            log.error("BankAccount record doesn't exist with id-{}", id);
//            throw new ResourceNotFoundException("Account not found!");
//        }
//        log.info("bank account found with id-{}", id);
//        return bankAccount;
//    }
//
    @Override
    public BankAccountResponseDto depositInAccount(Long id, double balance) {
        log.info("User with user-id-{} is depositing with amount - {}", id, balance);
        if(balance < 0){
            log.error("Amount is negative for deposit");
            throw new InvalidAmountException("Amount is less then 0");
        }
        Boolean isAmountDep = bankAccountRepository.existsById(id);
        if(!isAmountDep){
            log.error("Account number doesn't exist in db for deposition");
            throw new ResourceNotFoundException("Account not found!");
        }
        Optional<BankAccount> account = bankAccountRepository.findById(id)
                .stream()
                .findFirst();
        BankAccount accountInDep = account.get();
        accountInDep.deposit(balance);
        bankAccountRepository.deposit(accountInDep, balance);
        saveTransaction(accountInDep, TransactionType.DEPOSIT, balance, accountInDep.getBalance());
        log.info("Amount deposited successfully with user-id-{} having account with account number-{} and deposited -{}", id, accountInDep.getAccNo(), balance);
        return bankAccountToDto(accountInDep);
    }
//
//
//
    @Override
    public BankAccountResponseDto withdrawInAccount(Long id, double balance) {
        log.info("User with user-id-{} is withdrawing with amount - {}", id, balance);
        if(balance < 0){
            log.error("Amount is negative for withdrawal");
            throw new InvalidAmountException("Amount is less then 0");
        }
        Boolean isAmountDep = bankAccountRepository.existsById(id);
        if(!isAmountDep){
            log.error("Account number doesn't exist in db for withdrawal");
            throw new ResourceNotFoundException("Account not found!");
        }
        Optional<BankAccount> account = bankAccountRepository.findById(id)
                .stream()
                .findFirst();
        BankAccount accountInDep = account.get();
        if(accountInDep.getBalance() < balance){
            log.error("Amount entered is greater then balance");
            throw new BalanceException("Balance less then " + balance);
        }
        if(accountInDep.getAccountType() == AccountType.CURRENT){
            if(accountInDep.getBalance() + accountInDep.getOverdraftLimit() < balance){
                log.error("Bank account is current and amount is less than balance plus overdraft limit");
                throw new BalanceException("Balance is less then the amount in your account");
            }
        }
        accountInDep.withdraw(balance);
        bankAccountRepository.withdraw(accountInDep, balance);
        saveTransaction(accountInDep, TransactionType.WITHDRAW, balance, accountInDep.getBalance());
        log.info("amount withdrawal successfully with user-id-{} from account wih acount number- {}", accountInDep.getUser().getId(), accountInDep.getAccNo());
        return bankAccountToDto(accountInDep);
    }
//
//
    @Override
    public BalanceResDto checkBalance(Long id) {
        log.info("Checking balance for account number - {}", id);
        Optional<BankAccount> accountExist = bankAccountRepository.findById(id)
                .stream()
                .findFirst();
        if(!accountExist.isPresent()){
            log.error("Account number doesn't exist in db for balance check");
            throw new ResourceNotFoundException("Account not found!");
        }
        BankAccount accountToExist = accountExist.get();
        BalanceResDto balanceDto = new BalanceResDto();
        balanceDto.setBalance(accountToExist.getBalance());
        log.info("balance checked for account number-{}", id);
        return balanceDto;
    }

    @Override
    public InterestResponseDto calculateInterest(Long id) {
        log.info("calculating interest for account having account number - {}", id);
        Optional<BankAccount> accountExist = bankAccountRepository.findById(id)
                .stream()
                .findFirst();
        if(!accountExist.isPresent()){
            log.error("Account number doesn't exist in db for interest check");
            throw new ResourceNotFoundException("Account not found!");
        }
        BankAccount accountToExist = accountExist.get();
        saveTransaction(accountToExist, TransactionType.INTEREST, accountToExist.calculateInt(), accountToExist.getBalance());
        InterestResponseDto intDto = new InterestResponseDto();
        intDto.setInterest(accountToExist.calculateInt());
        log.info("interest checked for account number - {}", id);
        return intDto;
    }
}
