package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.BankAccount;

import java.util.List;

public interface BankRepository {
    void createAccount(BankAccount createdBankAccount);

    List<BankAccount> findById(Long accNo);

    Boolean existsById(Long id);

    void deposit(BankAccount accountInDep, double amt);

    void withdraw(BankAccount accountInDep, double amt);

    List<BankAccount> findAll();

    void delete(BankAccount accountToDel);

    List<BankAccount> findByBalanceLessThan(Double amount);

    Double getAverageOfBalance();

    List<BankAccount> findAccountsLessThanAmt(Double balance);

    List<BankAccount> findAccountsHavingSaving();

    List<BankAccount> findAccountsHavingCurrent();

    Integer findTotalNoAccs();

    Double findTotalMoney();

    List<BankAccount> findByAccNo(Long accNo);

    List<BankAccount> findDeletedAccounts();

    List<BankAccount> findByBalanceMoreThan(Double amt);

    List<BankAccount> findUserWithMaxBal();
}
