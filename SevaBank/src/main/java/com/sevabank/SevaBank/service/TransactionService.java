package com.sevabank.SevaBank.service;

import com.sevabank.SevaBank.Enum.TransactionType;
import com.sevabank.SevaBank.dto.response.TransactionResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<TransactionResponseDto> getTransactionsByAccount(Long accNo);
    List<TransactionResponseDto> getAllTransactions();
    TransactionResponseDto getTransactionById(Long transactionId);
    List<TransactionResponseDto> getTransactionsByType( TransactionType transactionType);
    List<TransactionResponseDto> getTransactionsBetween(LocalDateTime from, LocalDateTime to);
}
