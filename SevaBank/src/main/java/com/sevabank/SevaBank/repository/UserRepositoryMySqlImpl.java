package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.EmailResDto;
import com.sevabank.SevaBank.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryMySqlImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryMySqlImpl (@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create User
    private static final String CREATE_USER =
            "INSERT INTO user_schema.users " +
                    "(name, email, password, age) " +
                    "VALUES (?, ?, ?, ?)";

    // Check whether email exists
    private static final String EXISTS_BY_EMAIL =
            "SELECT COUNT(*) " +
                    "FROM user_schema.users " +
                    "WHERE email = ?";

    // Find user by ID
    private static final String FIND_BY_ID =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users " +
                    "WHERE id = ?";

    // Update user
    private static final String UPDATE_USER =
            "UPDATE user_schema.users " +
                    "SET name = ?, email = ?, password = ?, age = ? " +
                    "WHERE id = ?";

    // Find all users
    private static final String FIND_ALL =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users";

    // Find user by email
    private static final String FIND_BY_EMAIL =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users " +
                    "WHERE email = ?";

    // Find users having multiple bank accounts
    private static final String FIND_USERS_WITH_MULTIPLE_ACCOUNTS =
            "SELECT u.id, u.name, u.email, u.password, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "GROUP BY u.id, u.name, u.email, u.password, u.age " +
                    "HAVING COUNT(b.acc_no) > 1";

    // Find users whose age >= given age
    private static final String FIND_BY_AGE_GREATER_THAN_EQUAL =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users " +
                    "WHERE age >= ?";

    // Find old age users
    private static final String FIND_OLD_AGE_USERS =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users " +
                    "WHERE age >= 40";

    // Find all emails
    private static final String FIND_EMAILS =
            "SELECT email " +
                    "FROM user_schema.users";

    // Find users between two ages
    private static final String FIND_USERS_BW_AGE =
            "SELECT id, name, email, password, age " +
                    "FROM user_schema.users " +
                    "WHERE age BETWEEN ? AND ?";


    // RowMapper
    private User mapUser(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAge(rs.getInt("age"));

        return user;
    }


    @Override
    public void createUser(User user) {

        jdbcTemplate.update(
                CREATE_USER,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAge()
        );
    }


    @Override
    public boolean existsByEmail(String email) {

        Integer count = jdbcTemplate.queryForObject(
                EXISTS_BY_EMAIL,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }


    @Override
    public List<User> findById(Long userId) {

        return jdbcTemplate.query(
                FIND_BY_ID,
                this::mapUser,
                userId
        );
    }


    @Override
    public boolean updateUser(User user) {

        int rows = jdbcTemplate.update(
                UPDATE_USER,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAge(),
                user.getId()
        );

        return rows > 0;
    }


    @Override
    public List<User> findAll() {

        return jdbcTemplate.query(
                FIND_ALL,
                this::mapUser
        );
    }


    @Override
    public List<User> findByEmail(String email) {

        return jdbcTemplate.query(
                FIND_BY_EMAIL,
                this::mapUser,
                email
        );
    }


    @Override
    public List<User> findUsersWithMultipleAccounts() {

        return jdbcTemplate.query(
                FIND_USERS_WITH_MULTIPLE_ACCOUNTS,
                this::mapUser
        );
    }


    @Override
    public List<User> findByAgeGreaterThanEqual(int age) {

        return jdbcTemplate.query(
                FIND_BY_AGE_GREATER_THAN_EQUAL,
                this::mapUser,
                age
        );
    }


    @Override
    public List<User> findOldAgeUsers() {

        return jdbcTemplate.query(
                FIND_OLD_AGE_USERS,
                this::mapUser
        );
    }


    @Override
    public List<EmailResDto> findEmails() {

        return jdbcTemplate.query(
                FIND_EMAILS,
                (rs, rowNum) -> {

                    EmailResDto dto = new EmailResDto();

                    dto.setEmail(rs.getString("email"));

                    return dto;
                }
        );
    }


    @Override
    public List<User> findUsersBwAge(int age1, int age2) {

        return jdbcTemplate.query(
                FIND_USERS_BW_AGE,
                this::mapUser,
                age1,
                age2
        );
    }
}