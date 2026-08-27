package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Transaction tx) throws Exception {
        try{
            String sql = "INSERT INTO transaction_schema.transactions" +
                    "(amount, balance_after_transaction, transaction_time, transaction_type, account_number) " +
                    "VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    tx.getAmount(),
                    tx.getBalanceAfterTransaction(),
                    LocalDateTime.now(),
                    tx.getTransactionType().name(),
                    tx.getBankAccount().getAccNo()
            );
        }
        catch (DataAccessException e){
            throw new Exception("Some error in creating transaction");
        }
    }
}
