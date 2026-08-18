package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.BankAccountResponseDto;
import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Boolean existsByAccNoAndUserEmailAndUserPassword(Long accNo, String email, String password);

    Optional<BankAccount> findByAccNo(Long accNo);

    List<BankAccount> findByBalanceGreaterThan(Double amt);

    List<BankAccount> findByBalanceLessThan(Double amount);

    BankAccount findByAccNoAndUserEmailAndUserPassword(Long accNo, String email, String password);

    @Query(value = "SELECT b\n" +
            "    FROM BankAccount b\n" +
            "    WHERE b.balance = (\n" +
            "        SELECT MAX(b2.balance)\n" +
            "        FROM BankAccount b2\n" +
            "    )" )
    List<BankAccount> findAccountsWithHighestBalance();

    @Query(value="SELECT AVG(b.balance) from BankAccount b")
    Double getAverageOfBalance();

    @Query("SELECT b\n" +
            "FROM BankAccount b \n" +
            "WHERE b.balance > \n" +
            "(SELECT AVG(b1.balance) from BankAccount b1)\n" +
            " ")
    List<BankAccount> findAccountsHavingBalanceGreaterThanAvgBal();
}
