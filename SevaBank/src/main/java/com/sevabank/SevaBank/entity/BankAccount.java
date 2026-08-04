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
    private double interest_rate;
    @Column(name = "overdraft_limit")
    private double overdraft_limit;

    public BankAccount() {
    }

    public BankAccount(double balance, String accountType){
        this.balance = balance;
        this.accountType = AccountType.valueOf(accountType);
    }

    public int getAccNo() {
        return accNo;
    }

    public void deposit(double amt){
        balance += amt;
    }

    public void withdraw(double amt){
        balance -= amt;
    }

    public double checkBalance(){
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

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getOverdraft_limit() {
        return overdraft_limit;
    }

    public void setOverdraft_limit(double overdraft_limit) {
        this.overdraft_limit = overdraft_limit;
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
