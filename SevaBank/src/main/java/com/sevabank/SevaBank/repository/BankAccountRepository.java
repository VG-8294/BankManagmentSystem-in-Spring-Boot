package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.Enum.AccountType;
import com.sevabank.SevaBank.entity.BankAccount;
import com.sevabank.SevaBank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BankAccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
