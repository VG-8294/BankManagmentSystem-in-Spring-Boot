package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.exception.CustomServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BankAccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private BankAccount rowMapper(ResultSet rs, int rowNum) throws SQLException {
        BankAccount account = new BankAccount();

        account.setAccNo(rs.getLong("acc_no"));
        account.setAccountType(
                AccountType.valueOf(rs.getString("type"))
        );
        account.setBalance(rs.getDouble("balance"));

        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));

        account.setUser(user);

        return account;
    }

    private BankAccount bankAccountRowMapper(
            ResultSet rs, int rowNum) throws SQLException {

        BankAccount account = new BankAccount();

        account.setAccNo(rs.getLong("acc_no"));
        account.setAccountType(
                AccountType.valueOf(rs.getString("type"))
        );
        account.setBalance(rs.getDouble("balance"));

        return account;
    }

    public void createAccount(BankAccount createdBankAccount) {
        try {
            String sql = "INSERT INTO account_schema.bankaccount " +
                    "(type, balance, interest_rate, overdraft_limit, " +
                    "user_id, is_deleted, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            int rows = jdbcTemplate.update(
                    sql,
                    createdBankAccount.getAccountType().name(),
                    createdBankAccount.getBalance(),
                    createdBankAccount.getInterestRate(),
                    createdBankAccount.getOverdraftLimit(),
                    createdBankAccount.getUser().getId(),
                    createdBankAccount.getIsDeleted(),
                    createdBankAccount.getCreatedAt(),
                    createdBankAccount.getUpdatedAt()
            );

            if (rows != 1) {
                throw new CustomServiceException(
                        "Bank account could not be created"
                );
            }

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while creating the bank account"
            );
        }
    }

    public List<BankAccount> findById(Long accNo) {
        try {
            String sql = "SELECT * FROM account_schema.bankaccount b " +
                    "JOIN user_schema.users u " +
                    "ON u.id = b.user_id " +
                    "WHERE b.acc_no = ?";

            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {

                        BankAccount account = new BankAccount();

                        account.setAccNo(rs.getLong("acc_no"));
                        account.setAccountType(
                                AccountType.valueOf(rs.getString("type"))
                        );
                        account.setBalance(
                                rs.getDouble("balance")
                        );
                        account.setInterestRate(
                                rs.getDouble("interest_rate")
                        );
                        account.setOverdraftLimit(
                                rs.getDouble("overdraft_limit")
                        );

                        User user = new User();

                        user.setId(rs.getLong("user_id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setAge(rs.getInt("age"));

                        account.setUser(user);

                        return account;
                    },
                    accNo
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while finding the bank account"
            );
        }
    }

    public Boolean existsById(Long id) {
        try {
            String sql = "SELECT EXISTS (" +
                    "SELECT 1 FROM account_schema.bankaccount " +
                    "WHERE acc_no = ?" +
                    ")";

            return jdbcTemplate.queryForObject(
                    sql,
                    Boolean.class,
                    id
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while checking the bank account"
            );
        }
    }

    public void deposit(BankAccount accountInDep, double amt) {
        try {
            String sql = "UPDATE account_schema.bankaccount " +
                    "SET balance = balance + ? " +
                    "WHERE acc_no = ?";

            jdbcTemplate.update(
                    sql,
                    amt,
                    accountInDep.getAccNo()
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while depositing money"
            );
        }
    }

    public void withdraw(BankAccount accountInDep, double amt) {
        try {
            String sql = "UPDATE account_schema.bankaccount " +
                    "SET balance = balance - ? " +
                    "WHERE acc_no = ?";

            jdbcTemplate.update(
                    sql,
                    amt,
                    accountInDep.getAccNo()
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while withdrawing money"
            );
        }
    }

    public List<BankAccount> findAll() {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "b.is_deleted, u.id, u.name, u.email, u.age " +
                    "FROM account_schema.bankaccount b " +
                    "JOIN user_schema.users u " +
                    "ON b.user_id = u.id " +
                    "WHERE b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {

                        BankAccount account = new BankAccount();

                        account.setAccNo(rs.getLong("acc_no"));
                        account.setAccountType(
                                AccountType.valueOf(rs.getString("type"))
                        );
                        account.setBalance(
                                rs.getDouble("balance")
                        );
                        account.setIsDeleted(
                                rs.getBoolean("is_deleted")
                        );

                        User user = new User();

                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setAge(rs.getInt("age"));

                        account.setUser(user);

                        return account;
                    }
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving bank accounts"
            );
        }
    }

    public void delete(BankAccount accountToDel) {
        try {
            String sql = "DELETE FROM account_schema.bankaccount " +
                    "WHERE acc_no = ?";

            jdbcTemplate.update(
                    sql,
                    accountToDel.getAccNo()
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while deleting the bank account"
            );
        }
    }

    public List<BankAccount> findByBalanceLessThan(Double amount) {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age, " +
                    "b.acc_no, b.type, b.balance, b.is_deleted " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.balance < ?";

            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {

                        BankAccount account = new BankAccount();

                        account.setAccNo(rs.getLong("acc_no"));
                        account.setAccountType(
                                AccountType.valueOf(rs.getString("type"))
                        );
                        account.setBalance(
                                rs.getDouble("balance")
                        );
                        account.setIsDeleted(
                                rs.getBoolean("is_deleted")
                        );

                        User user = new User();

                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setAge(rs.getInt("age"));

                        account.setUser(user);

                        return account;
                    },
                    amount
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving accounts with low balance"
            );
        }
    }

    public Double getAverageOfBalance() {
        try {
            String sql = "SELECT AVG(balance) " +
                    "FROM account_schema.bankaccount " +
                    "WHERE is_deleted = false";

            return jdbcTemplate.queryForObject(
                    sql,
                    Double.class
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while calculating average balance"
            );
        }
    }

    public List<BankAccount> findAccountsLessThanAmt(Double balance) {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.balance < ? " +
                    "AND b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper,
                    balance
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving accounts below the specified balance"
            );
        }
    }

    public List<BankAccount> findAccountsHavingSaving() {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.type = 'SAVING' " +
                    "AND b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving savings accounts"
            );
        }
    }

    public List<BankAccount> findAccountsHavingCurrent() {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.type = 'CURRENT' " +
                    "AND b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving current accounts"
            );
        }
    }

    public Integer findTotalNoAccs() {
        try {
            String sql = "SELECT COUNT(*) " +
                    "FROM account_schema.bankaccount " +
                    "WHERE is_deleted = false";

            return jdbcTemplate.queryForObject(
                    sql,
                    Integer.class
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while calculating total accounts"
            );
        }
    }

    public Double findTotalMoney() {
        try {
            String sql = "SELECT SUM(balance) " +
                    "FROM account_schema.bankaccount " +
                    "WHERE is_deleted = false";

            return jdbcTemplate.queryForObject(
                    sql,
                    Double.class
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while calculating total money"
            );
        }
    }

    public List<BankAccount> findByAccNo(Long accNo) {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.acc_no = ? " +
                    "AND b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper,
                    accNo
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while finding the bank account"
            );
        }
    }

    public List<BankAccount> findDeletedAccounts() {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.is_deleted = true";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving deleted accounts"
            );
        }
    }

    public List<BankAccount> findByBalanceMoreThan(Double amt) {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.balance > ? " +
                    "AND b.is_deleted = false";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper,
                    amt
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while retrieving accounts above the specified balance"
            );
        }
    }

    public List<BankAccount> findUserWithMaxBal() {
        try {
            String sql = "SELECT b.acc_no, b.type, b.balance, " +
                    "u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "WHERE b.is_deleted = false " +
                    "ORDER BY b.balance DESC " +
                    "LIMIT 1";

            return jdbcTemplate.query(
                    sql,
                    this::rowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Something went wrong while finding the account with maximum balance"
            );
        }
    }
}