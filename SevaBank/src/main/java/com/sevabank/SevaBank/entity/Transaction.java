package com.sevabank.SevaBank.entity;

import com.sevabank.SevaBank.Enum.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", schema = "transaction_schema")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    private BankAccount bankAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "balance_after_transaction", nullable = false)
    private double balanceAfterTransaction;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    public Transaction() {
    }

    public Transaction(BankAccount bankAccount,
                       TransactionType transactionType,
                       double amount,
                       double balanceAfterTransaction) {
        this.bankAccount = bankAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.transactionTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                ", transactionTime=" + transactionTime +
                '}';
    }
}