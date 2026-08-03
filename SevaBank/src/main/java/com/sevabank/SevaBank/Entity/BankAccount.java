package com.sevabank.SevaBank.Entity;

import com.sevabank.SevaBank.Enum.AccountType;
import jakarta.persistence.*;

@Entity
@Table(name = "bankaccount", schema = "account_schema")
public abstract class BankAccount {
    private static int nextAccNo = 1000;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int accNo;
    @Column(name = "balance", nullable = false)
    protected double balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    protected AccountType accountType;
    @Column(name = "interest_rate")
    private double interest_rate;
    @Column(name = "overdraft_limit")
    private double overdraft_limit;

    public BankAccount(double balance, AccountType saving){
        this.balance = balance;
        this.accNo = nextAccNo++;
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

    @Override
    public String toString() {
        return "BankAccount{" +
                "Account Number =" + accNo +
                ", balance=" + balance +
                ", accountType=" + accountType +
                '}';
    }
}
