package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBankAccount_AccNo(Long accNo);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByTransactionTimeBetween(LocalDateTime from, LocalDateTime to);
}
