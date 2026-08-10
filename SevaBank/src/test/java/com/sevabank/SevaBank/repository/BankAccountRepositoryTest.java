package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;


    private User createUser() {

        User user = new User(
                "Vishal",
                "vishal@gmail.com",
                "Password@123",
                22
        );

        return userRepository.save(user);
    }


    private BankAccount createAccount(
            User user,
            double balance,
            String accountType
    ) {

        BankAccount account = new BankAccount(
                balance,
                accountType,
                5.0,
                1000.0
        );

        account.setUser(user);

        return account;
    }


    // =========================================================
    // existsByAccNoAndUserEmailAndUserPassword
    // =========================================================

    @Test
    void shouldReturnTrueForCorrectCredentials() {

        User user = createUser();

        BankAccount account =
                createAccount(user, 1000.0, "SAVING");

        BankAccount savedAccount =
                bankAccountRepository.save(account);

        Boolean result =
                bankAccountRepository
                        .existsByAccNoAndUserEmailAndUserPassword(
                                savedAccount.getAccNo(),
                                "vishal@gmail.com",
                                "Password@123"
                        );

        assertTrue(result);
    }


    @Test
    void shouldReturnFalseForWrongEmail() {

        User user = createUser();

        BankAccount account =
                createAccount(user, 1000.0, "SAVING");

        BankAccount savedAccount =
                bankAccountRepository.save(account);

        Boolean result =
                bankAccountRepository
                        .existsByAccNoAndUserEmailAndUserPassword(
                                savedAccount.getAccNo(),
                                "wrong@gmail.com",
                                "Password@123"
                        );

        assertFalse(result);
    }


    @Test
    void shouldReturnFalseForWrongPassword() {

        User user = createUser();

        BankAccount account =
                createAccount(user, 1000.0, "SAVING");

        BankAccount savedAccount =
                bankAccountRepository.save(account);

        Boolean result =
                bankAccountRepository
                        .existsByAccNoAndUserEmailAndUserPassword(
                                savedAccount.getAccNo(),
                                "vishal@gmail.com",
                                "WrongPassword"
                        );

        assertFalse(result);
    }


    // =========================================================
    // findByAccNo
    // =========================================================

    @Test
    void shouldFindAccountByAccountNumber() {

        User user = createUser();

        BankAccount account =
                createAccount(user, 1000.0, "SAVING");

        BankAccount savedAccount =
                bankAccountRepository.save(account);

        BankAccount result =
                bankAccountRepository
                        .findByAccNo(savedAccount.getAccNo())
                        .orElse(null);

        assertNotNull(result);

        assertEquals(
                savedAccount.getAccNo(),
                result.getAccNo()
        );

        assertEquals(
                1000.0,
                result.getBalance()
        );
    }


    @Test
    void shouldReturnEmptyWhenAccountDoesNotExist() {

        assertFalse(
                bankAccountRepository
                        .findByAccNo(999999L)
                        .isPresent()
        );
    }


    // =========================================================
    // findByBalanceGreaterThan
    // =========================================================

    @Test
    void shouldFindAccountsWithBalanceGreaterThanAmount() {

        User user = createUser();

        BankAccount account1 =
                createAccount(user, 1000.0, "SAVING");

        BankAccount account2 =
                createAccount(user, 5000.0, "SAVING");

        BankAccount account3 =
                createAccount(user, 10000.0, "CURRENT");

        bankAccountRepository.save(account1);
        bankAccountRepository.save(account2);
        bankAccountRepository.save(account3);

        List<BankAccount> result =
                bankAccountRepository.findByBalanceGreaterThan(4000.0);

        assertNotNull(result);

        assertTrue(
                result.stream()
                        .anyMatch(account -> account.getBalance() == 5000.0)
        );

        assertTrue(
                result.stream()
                        .anyMatch(account -> account.getBalance() == 10000.0)
        );

        for (BankAccount account : result) {
            assertTrue(account.getBalance() > 4000.0);
        }
    }


    @Test
    void shouldReturnEmptyWhenNoBalanceIsGreaterThanAmount() {

        User user = createUser();

        bankAccountRepository.save(
                createAccount(user, 1000.0, "SAVING")
        );

        bankAccountRepository.save(
                createAccount(user, 2000.0, "SAVING")
        );

        List<BankAccount> result =
                bankAccountRepository
                        .findByBalanceGreaterThan(999999999.0);

        assertTrue(result.isEmpty());
    }


    // =========================================================
    // findByBalanceLessThan
    // =========================================================

    @Test
    void shouldFindAccountsWithBalanceLessThanAmount() {

        User user = createUser();

        bankAccountRepository.save(
                createAccount(user, 1000.0, "SAVING")
        );

        bankAccountRepository.save(
                createAccount(user, 5000.0, "SAVING")
        );

        bankAccountRepository.save(
                createAccount(user, 10000.0, "CURRENT")
        );

        List<BankAccount> result =
                bankAccountRepository
                        .findByBalanceLessThan(6000.0);

        assertEquals(2, result.size());

        for (BankAccount account : result) {
            assertTrue(account.getBalance() < 6000.0);
        }
    }


    @Test
    void shouldReturnEmptyWhenNoBalanceIsLessThanAmount() {

        User user = createUser();

        bankAccountRepository.save(
                createAccount(user, 5000.0, "SAVING")
        );

        bankAccountRepository.save(
                createAccount(user, 7000.0, "CURRENT")
        );

        List<BankAccount> result =
                bankAccountRepository
                        .findByBalanceLessThan(1000.0);

        assertTrue(result.isEmpty());
    }
}