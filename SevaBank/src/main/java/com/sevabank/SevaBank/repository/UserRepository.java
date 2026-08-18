package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.UserResponseDto;
import com.sevabank.SevaBank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAgeGreaterThan(Integer age);

    Optional<User> findByEmail(String email);

    List<User> findByAgeGreaterThanEqual(int i);

    boolean existsByEmail(String email);

    @Query(value = "SELECT u\n" +
            "    FROM User u\n" +
            "    WHERE (\n" +
            "        SELECT COUNT(b)\n" +
            "        FROM BankAccount b\n" +
            "        WHERE b.user = u\n" +
            "    ) > 1")
    List<User> findUsersWithMultipleAccounts();

    @Query("SELECT u\n" +
            "    FROM User u\n" +
            "    JOIN BankAccount b ON b.user = u\n" +
            "    GROUP BY u\n" +
            "    HAVING SUM(b.balance) > 100000")
    List<User> findUsersWithTotalBalanceGreaterThan100000();
}
