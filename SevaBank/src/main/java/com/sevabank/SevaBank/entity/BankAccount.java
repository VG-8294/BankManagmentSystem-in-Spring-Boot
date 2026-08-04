package com.sevabank.SevaBank.entity;

import com.sevabank.SevaBank.Enum.AccountType;
import jakarta.persistence.*;

@Entity
@Table(name = "bankaccount", schema = "account_schema")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int accNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "balance", nullable = false)
    protected double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    protected AccountType accountType;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "overdraft_limit")
    private Double overdraftLimit;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    public BankAccount() {
    }

    public BankAccount(double balance, String accountType, Double interestRate, Double overdraftLimit) {
        this.balance = balance;
        this.accountType = AccountType.valueOf(accountType);
        this.interestRate = interestRate;
        this.overdraftLimit = overdraftLimit;
        this.isDeleted = false;
    }

    public int getAccNo() {
        return accNo;
    }

    public void deposit(double amt) {
        balance += amt;
    }

    public void withdraw(double amt) {
        balance -= amt;
    }

    public double checkBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(Double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "Account Number =" + accNo +
                ", balance=" + balance +
                ", accountType=" + accountType +
                '}';
    }
}