package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Boolean existsByAccNoAndUserEmailAndUserPassword(Long accNo, String email, String password);
}
