package com.sevabank.SevaBank.entity;

import com.sevabank.SevaBank.Enum.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(
        name = "bankaccount",
        schema = "account_schema",
        indexes = {
                @Index(
                        name = "idx_bankaccount_balance",
                        columnList = "balance"
                ),
                @Index(
                        name = "idx_bankaccount_user_id",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long accNo;

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

    public BankAccount(double balance, AccountType accountType, Double interestRate, Double overdraftLimit) {
        this.balance = balance;
        this.accountType = accountType;
        this.interestRate = interestRate;
        this.overdraftLimit = overdraftLimit;
        this.isDeleted = false;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public double calculateInt(){
        return (balance*this.interestRate)/100;
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