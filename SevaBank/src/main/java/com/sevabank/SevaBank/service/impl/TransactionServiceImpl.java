package com.sevabank.SevaBank.service.impl;
import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.TransactionResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.Transaction;
import com.sevabank.SevaBank.exception.ResourceNotFoundException;
import com.sevabank.SevaBank.repository.BankAccountRepository;
import com.sevabank.SevaBank.repository.TransactionRepository;
import com.sevabank.SevaBank.service.TransactionService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            BankAccountRepository bankAccountRepository) {

        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }


    // ============================================================
    // GET TRANSACTIONS BY ACCOUNT
    // ============================================================

    @Override
    public List<TransactionResponseDto> getTransactionsByAccount(Long accNo) {

        // First check whether account exists
        BankAccount account = bankAccountRepository.findById(accNo)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bank account not found"));

        List<Transaction> transactions =
                transactionRepository.findByBankAccount_AccNo(accNo);

        return transactions.stream()
                .map(this::transactionToDto)
                .collect(Collectors.toList());
    }


    // ============================================================
    // GET ALL TRANSACTIONS
    // ============================================================

    @Override
    public List<TransactionResponseDto> getAllTransactions() {

        List<Transaction> transactions =
                transactionRepository.findAll();

        return transactions.stream()
                .map(this::transactionToDto)
                .collect(Collectors.toList());
    }


    // ============================================================
    // GET TRANSACTION BY ID
    // ============================================================

    @Override
    public TransactionResponseDto getTransactionById(Long transactionId) {

        Transaction transaction =
                transactionRepository.findById(transactionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Transaction not found"));

        return transactionToDto(transaction);
    }


    // ============================================================
    // GET TRANSACTIONS BY TYPE
    // ============================================================

    @Override
    public List<TransactionResponseDto> getTransactionsByType(
            TransactionType transactionType) {

        List<Transaction> transactions =
                transactionRepository.findByTransactionType(transactionType);

        return transactions.stream()
                .map(this::transactionToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponseDto> getTransactionsBetween(
            LocalDateTime from,
            LocalDateTime to) {

        List<Transaction> transactions =
                transactionRepository.findByTransactionTimeBetween(
                        from, to);

        return transactions.stream()
                .map(this::transactionToDto)
                .collect(Collectors.toList());
    }



    // ============================================================
    // ENTITY -> DTO
    // ============================================================

    private TransactionResponseDto transactionToDto(
            Transaction transaction) {

        TransactionResponseDto dto =
                new TransactionResponseDto();

        dto.setTransactionId(
                transaction.getTransactionId());

        dto.setTransactionType(
                transaction.getTransactionType());

        dto.setAmount(
                transaction.getAmount());

        dto.setBalanceAfterTransaction(
                transaction.getBalanceAfterTransaction());

        dto.setTransactionTime(
                transaction.getTransactionTime());

        return dto;
    }
}
