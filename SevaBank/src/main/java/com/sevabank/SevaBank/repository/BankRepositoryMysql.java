package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BankRepositoryMysql implements BankRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankRepositoryMysql(
            @Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // =========================
    // ROW MAPPERS
    // =========================

    private BankAccount rowMapper(ResultSet rs, int rowNum)
            throws SQLException {

        BankAccount account = new BankAccount();

        account.setAccNo(rs.getLong("acc_no"));
        account.setAccountType(
                AccountType.valueOf(rs.getString("type"))
        );
        account.setBalance(rs.getDouble("balance"));
        account.setIsDeleted(rs.getBoolean("is_deleted"));

        User user = new User();

        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));

        account.setUser(user);

        return account;
    }


    private BankAccount bankAccountRowMapper(
            ResultSet rs,
            int rowNum) throws SQLException {

        BankAccount account = new BankAccount();

        account.setAccNo(rs.getLong("acc_no"));

        account.setAccountType(
                AccountType.valueOf(rs.getString("type"))
        );

        account.setBalance(rs.getDouble("balance"));

        return account;
    }


    // =========================
    // CREATE ACCOUNT
    // =========================

    @Override
    public void createAccount(BankAccount createdBankAccount) {

        String sql =
                "INSERT INTO sevabank.bankaccount " +
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

        if (rows == 1) {
            System.out.println("Bank Account created!");
        } else {
            System.out.println("Account creation failed");
        }
    }


    // =========================
    // FIND BY ID
    // =========================

    @Override
    public List<BankAccount> findById(Long accNo) {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.interest_rate, b.overdraft_limit, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.password, u.age " +
                        "FROM sevabank.bankaccount b " +
                        "JOIN sevabank.users u " +
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
                    account.setIsDeleted(
                            rs.getBoolean("is_deleted")
                    );

                    User user = new User();

                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setAge(rs.getInt("age"));

                    account.setUser(user);

                    return account;

                },
                accNo
        );
    }


    // =========================
    // EXISTS BY ID
    // =========================

    @Override
    public Boolean existsById(Long id) {

        String sql =
                "SELECT EXISTS (" +
                        "SELECT 1 " +
                        "FROM sevabank.bankaccount " +
                        "WHERE acc_no = ?" +
                        ")";

        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                id
        );
    }


    // =========================
    // DEPOSIT
    // =========================

    @Override
    public void deposit(BankAccount accountInDep, double amt) {

        String sql =
                "UPDATE sevabank.bankaccount " +
                        "SET balance = balance + ? " +
                        "WHERE acc_no = ?";

        jdbcTemplate.update(
                sql,
                amt,
                accountInDep.getAccNo()
        );
    }


    // =========================
    // WITHDRAW
    // =========================

    @Override
    public void withdraw(
            BankAccount accountInDep,
            double amt) {

        String sql =
                "UPDATE sevabank.bankaccount " +
                        "SET balance = balance - ? " +
                        "WHERE acc_no = ?";

        jdbcTemplate.update(
                sql,
                amt,
                accountInDep.getAccNo()
        );
    }


    // =========================
    // DELETE
    // =========================

    @Override
    public void delete(BankAccount accountToDel) {

        String sql =
                "DELETE FROM sevabank.bankaccount " +
                        "WHERE acc_no = ?";

        jdbcTemplate.update(
                sql,
                accountToDel.getAccNo()
        );
    }


    // =========================
    // AVERAGE BALANCE
    // =========================

    @Override
    public Double getAverageOfBalance() {

        String sql =
                "SELECT AVG(balance) " +
                        "FROM sevabank.bankaccount " +
                        "WHERE is_deleted = false";

        return jdbcTemplate.queryForObject(
                sql,
                Double.class
        );
    }


    // =========================
    // SAVING ACCOUNTS
    // =========================

    @Override
    public List<BankAccount> findAccountsHavingSaving() {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.type = 'SAVING' " +
                        "AND b.is_deleted = false";

        return jdbcTemplate.query(
                sql,
                this::rowMapper
        );
    }


    // =========================
    // CURRENT ACCOUNTS
    // =========================

    @Override
    public List<BankAccount> findAccountsHavingCurrent() {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.type = 'CURRENT' " +
                        "AND b.is_deleted = false";

        return jdbcTemplate.query(
                sql,
                this::rowMapper
        );
    }


    // =========================
    // TOTAL ACCOUNTS
    // =========================

    @Override
    public Integer findTotalNoAccs() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM sevabank.bankaccount " +
                        "WHERE is_deleted = false";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }


    // =========================
    // TOTAL MONEY
    // =========================

    @Override
    public Double findTotalMoney() {

        String sql =
                "SELECT SUM(balance) " +
                        "FROM sevabank.bankaccount " +
                        "WHERE is_deleted = false";

        return jdbcTemplate.queryForObject(
                sql,
                Double.class
        );
    }


    // =========================
    // FIND BY ACCOUNT NUMBER
    // =========================

    @Override
    public List<BankAccount> findByAccNo(Long accNo) {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.acc_no = ? " +
                        "AND b.is_deleted = false";

        return jdbcTemplate.query(
                sql,
                this::rowMapper,
                accNo
        );
    }


    // =========================
    // DELETED ACCOUNTS
    // =========================

    @Override
    public List<BankAccount> findDeletedAccounts() {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.is_deleted = true";

        return jdbcTemplate.query(
                sql,
                this::rowMapper
        );
    }


    // =========================
    // BALANCE GREATER THAN
    // =========================

    @Override
    public List<BankAccount> findByBalanceMoreThan(
            Double amt) {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.balance > ? " +
                        "AND b.is_deleted = false";

        return jdbcTemplate.query(
                sql,
                this::rowMapper,
                amt
        );
    }


    // =========================
    // USER WITH MAX BALANCE
    // =========================

    @Override
    public List<BankAccount> findUserWithMaxBal() {

        String sql =
                "SELECT b.acc_no, b.type, b.balance, " +
                        "b.is_deleted, " +
                        "u.id, u.name, u.email, u.age " +
                        "FROM sevabank.users u " +
                        "JOIN sevabank.bankaccount b " +
                        "ON u.id = b.user_id " +
                        "WHERE b.is_deleted = false " +
                        "ORDER BY b.balance DESC " +
                        "LIMIT 1";

        return jdbcTemplate.query(
                sql,
                this::rowMapper
        );
    }
}