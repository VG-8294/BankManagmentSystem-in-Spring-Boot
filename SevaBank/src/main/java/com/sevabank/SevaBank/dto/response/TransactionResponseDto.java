package com.sevabank.SevaBank.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sevabank.SevaBank.Enum.TransactionType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class TransactionResponseDto {
    private Long transactionId;
    private TransactionType transactionType;
    private Double amount;
    private Double balanceAfterTransaction;
    private LocalDateTime transactionTime;
}
