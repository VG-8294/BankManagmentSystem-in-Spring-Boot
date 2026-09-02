package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
        account.setAccountType(AccountType.valueOf(rs.getString("type")));
        account.setBalance(rs.getDouble("balance"));

        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));

        account.setUser(user);
        return account;
    }

    private BankAccount bankAccountRowMapper(ResultSet rs, int rowNum) throws SQLException {
        BankAccount account = new BankAccount();
        account.setAccNo(rs.getLong("acc_no"));
        account.setAccountType(AccountType.valueOf(rs.getString("type")));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }

    public void createAccount(BankAccount createdBankAccount) {
        String sql = "INSERT INTO account_schema.bankaccount " +
                "(type, balance, interest_rate, overdraft_limit, user_id, is_deleted, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sql,
                                createdBankAccount.getAccountType().name(),
                                createdBankAccount.getBalance(),
                                createdBankAccount.getInterestRate(),
                                createdBankAccount.getOverdraftLimit(),
                                createdBankAccount.getUser().getId(),
                                createdBankAccount.getIsDeleted(),
                                createdBankAccount.getCreatedAt(),
                                createdBankAccount.getUpdatedAt()
                                );

        if(rows == 1){
            System.out.println("Bank Account created!");
        }
        else{
            System.out.println("Account creation failed");
        }
    }

    public List<BankAccount> findById(Long accNo) {
        String sql = "SELECT * FROM account_schema.bankaccount b "+
                     "JOIN user_schema.users u "+
                     "on u.id = b.user_id " +
                      "WHERE b.acc_no = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
                        BankAccount account = new BankAccount();
                        account.setAccNo(rs.getLong("acc_no"));;
                        account.setAccountType(AccountType.valueOf(rs.getString("type")));
                        account.setBalance(rs.getDouble("balance"));
                        account.setInterestRate(rs.getDouble("interest_rate"));
                        account.setOverdraftLimit(rs.getDouble("overdraft_limit"));

                        User user = new User();
                        user.setId(rs.getLong("user_id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setAge(rs.getInt("age"));

                        account.setUser(user);
                        return account;
        }, accNo);
    }

    public Boolean existsById(Long id) {
        String sql = "SELECT EXISTS( " +
                     "SELECT 1 FROM account_schema.bankaccount " +
                     "WHERE acc_no = ?" +
                     ")";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public void deposit(BankAccount accountInDep, double amt) {
        String sql = "UPDATE account_schema.bankaccount " +
                     "SET balance = balance + ? " +
                     "WHERE acc_no = ?";

        jdbcTemplate.update(sql, amt, accountInDep.getAccNo());
    }

    public void withdraw(BankAccount accountInDep, double amt) {
        String sql = "UPDATE account_schema.bankaccount " +
                     "SET balance = balance - ? " +
                     "WHERE acc_no = ?";
        jdbcTemplate.update(sql, amt, accountInDep.getAccNo());
    }

    public List<BankAccount> findAll() {
        String sql = "SELECT b.acc_no, b.type, b.balance, b.is_deleted, u.id, u.name, u.email, u.age " +
                     "FROM account_schema.bankaccount b JOIN user_schema.users u " +
                     "on b.user_id = u.id " +
                     "WHERE b.is_deleted = false";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
                        BankAccount account = new BankAccount();
                        account.setAccNo(rs.getLong("acc_no"));
                        account.setAccountType(AccountType.valueOf(rs.getString("type")));
                        account.setBalance(rs.getDouble("balance"));
                        account.setIsDeleted(rs.getBoolean("is_deleted"));

                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setAge(rs.getInt("age"));

                        account.setUser(user);
                        return account;
        });
    }

    public void delete(BankAccount accountToDel) {
        String sql = "DELETE FROM account_schema.bankaccount b " +
                     "WHERE b.acc_no = ? ";

        jdbcTemplate.update(sql, accountToDel.getAccNo());
    }

    public List<BankAccount> findByBalanceLessThan(Double amount) {
        String sql = "SELECT u.id, u.name, u.email, u.age, b.acc_no, b.type, b.balance, b.is_deleted FROM user_schema.users u " +
                     "JOIN account_schema.bankaccount b on u.id = b.user_id " +
                     "WHERE b.balance < ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{

            BankAccount account = new BankAccount();
            account.setAccNo(rs.getLong("acc_no"));
            account.setAccountType(AccountType.valueOf(rs.getString("type")));
            account.setBalance(rs.getDouble("balance"));
            account.setIsDeleted(rs.getBoolean("is_deleted"));

            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));

            account.setUser(user);

            return account;
        }, amount);
    }

    public Double getAverageOfBalance() {
        String sql = "SELECT AVG(balance) FROM account_schema.bankaccount " +
                     "WHERE is_deleted = false";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    public List<BankAccount> findAccountsLessThanAmt(Double balance) {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                     "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                     "ON u.id = b.user_id " +
                     "WHERE b.balance < ? AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::rowMapper, balance);

    }

    public List<BankAccount> findAccountsHavingSaving() {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.type = 'SAVING' AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public List<BankAccount> findAccountsHavingCurrent() {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.type = 'CURRENT' AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::rowMapper);
    }


    public Integer findTotalNoAccs() {
        String sql = "SELECT COUNT(*) FROM account_schema.bankaccount WHERE is_deleted = false";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Double findTotalMoney() {
        String sql = "SELECT SUM(balance) FROM account_schema.bankaccount WHERE is_deleted = false";

        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    public List<BankAccount> findByAccNo(Long accNo) {

        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.acc_no = ? AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::rowMapper, accNo);
    }

    public List<BankAccount> findDeletedAccounts() {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.is_deleted = true";

        return jdbcTemplate.query(sql, this::rowMapper);
    }

    public List<BankAccount> findByBalanceMoreThan(Double amt) {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.balance > ? AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::rowMapper, amt);
    }

    public List<BankAccount> findUserWithMaxBal() {
        String sql = "SELECT b.acc_no, b.type, b.balance, u.id, u.name, u.email, u.age " +
                "FROM user_schema.users u JOIN account_schema.bankaccount b " +
                "ON u.id = b.user_id " +
                "WHERE b.is_deleted = false " +
                "ORDER BY b.balance DESC " +
                "LIMIT 1" ;
        return jdbcTemplate.query(sql, this::rowMapper);

    }

    public  List<BankAccount> findAccountByUserId(Long userId) {
        String sql = "SELECT b.acc_no, b.type, b.balance " +
                "FROM account_schema.bankaccount b " +
                "WHERE b.user_id = ? AND b.is_deleted = false";

        return jdbcTemplate.query(sql, this::bankAccountRowMapper, userId);
    }
}
