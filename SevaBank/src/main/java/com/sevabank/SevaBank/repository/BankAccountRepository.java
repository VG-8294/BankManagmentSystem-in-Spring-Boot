package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
