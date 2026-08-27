package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.EmailResDto;
import com.sevabank.SevaBank.entity.User;
import com.sevabank.SevaBank.exception.CustomServiceException;
import com.sevabank.SevaBank.exception.UserCreationException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private User userRowMapper(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAge(rs.getInt("age"));
        return user;
    }

    public void createUser(User user) {
        try {
            String sql = "INSERT INTO user_schema.users " +
                    "(name, email, password, age, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            int rows = jdbcTemplate.update(
                    sql,
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAge(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );

            if (rows != 1) {
                throw new UserCreationException(
                        "There is some problem in creation of user"
                );
            }

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while creating user"
            );
        }
    }

    public boolean existsByEmail(String email) {
        try {
            String sql = "SELECT EXISTS (" +
                    "SELECT 1 FROM user_schema.users " +
                    "WHERE email = ?" +
                    ")";

            return jdbcTemplate.queryForObject(
                    sql,
                    Boolean.class,
                    email
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while checking user email"
            );
        }
    }

    public List<User> findById(Long userId) {
        try {
            String sql = "SELECT * FROM user_schema.users " +
                    "WHERE id = ?";

            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setAge(rs.getInt("age"));
                        return user;
                    },
                    userId
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding user by ID"
            );
        }
    }

    public boolean updateUser(User user) {
        try {
            String sql = "UPDATE user_schema.users " +
                    "SET name = ?, email = ?, password = ?, age = ?, updated_at = ? " +
                    "WHERE id = ?";

            int rows = jdbcTemplate.update(
                    sql,
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAge(),
                    LocalDateTime.now(),
                    user.getId()
            );

            return rows == 1;

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while updating user"
            );
        }
    }

    public List<User> findAll() {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while retrieving all users"
            );
        }
    }

    public List<User> findByEmail(String email) {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "WHERE u.email = ?";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper,
                    email
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding user by email"
            );
        }
    }

    public List<User> findUsersWithMultipleAccounts() {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "JOIN account_schema.bankaccount b " +
                    "ON u.id = b.user_id " +
                    "GROUP BY u.id, u.name, u.email, u.age " +
                    "HAVING COUNT(*) > 1";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding users with multiple accounts"
            );
        }
    }

    public List<User> findByAgeGreaterThanEqual(int age) {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "WHERE u.age >= ?";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper,
                    age
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding users by age"
            );
        }
    }

    public List<User> findOldAgeUsers() {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "WHERE u.age >= 60";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding senior users"
            );
        }
    }

    public List<EmailResDto> findEmails() {
        try {
            String sql = "SELECT email " +
                    "FROM user_schema.users";

            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {
                        EmailResDto emailResDto = new EmailResDto();
                        emailResDto.setEmail(
                                rs.getString("email")
                        );
                        return emailResDto;
                    }
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while retrieving user emails"
            );
        }
    }

    public List<User> findUsersBwAge(int age1, int age2) {
        try {
            String sql = "SELECT u.id, u.name, u.email, u.age " +
                    "FROM user_schema.users u " +
                    "WHERE u.age > ? AND u.age < ?";

            return jdbcTemplate.query(
                    sql,
                    this::userRowMapper,
                    age1,
                    age2
            );

        } catch (DataAccessException e) {
            throw new CustomServiceException(
                    "Database error while finding users between ages"
            );
        }
    }
}