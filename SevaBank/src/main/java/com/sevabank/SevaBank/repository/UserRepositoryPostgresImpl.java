package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.dto.response.EmailResDto;
import com.sevabank.SevaBank.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepositoryPostgresImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryPostgresImpl(
            @Qualifier("postgresJdbcTemplate") JdbcTemplate jdbcTemplate) {
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


    @Override
    public void createUser(User user) {
        System.out.println("In user Repo");
        String sql = "INSERT INTO user_schema.users"+
                     "(name, email, password, age, created_at, updated_at)" +
                      "VALUES(?, ?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sql,
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAge(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
                    );



        if(rows == 1){
            System.out.println("User created!");
        }
        else{
            System.out.println("User creation failed");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (" +
                "SELECT 1 FROM user_schema.users " +
                "WHERE email = ?" +
                ")";

        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    @Override
    public List<User> findById(Long userId) {
        String sql = "SELECT * FROM user_schema.users " +
                      "WHERE id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setAge(rs.getInt("age"));
            return user;
        }, userId);
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE user_schema.users " +
                     "SET name = ?, email = ?, password = ?, age = ?, updated_at = ? " +
                     "WHERE id = ?";

        int rows = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getAge(), LocalDateTime.now(), user.getId());

        return rows == 1;
    }

    @Override
    public List<User> findAll() {
        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {

            System.out.println(
                    "START | " +
                            Thread.currentThread().getName() +
                            " | " + connection
            );

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(
                    "END   | " +
                            Thread.currentThread().getName() +
                            " | " + connection
            );

            return null;
        });
        String sql = "SELECT u.id, u.name, u.email, u.age " +
                     "FROM user_schema.users u ";

        return jdbcTemplate.query(sql, this::userRowMapper);
    }

    @Override
    public List<User> findByEmail(String email) {
        String sql = "SELECT u.id, u.name, u.email, u.age "+
                     "FROM user_schema.users u " +
                     "WHERE email = ?";

        return jdbcTemplate.query(sql, this::userRowMapper, email);
    }

    @Override
    public List<User> findUsersWithMultipleAccounts() {
        String sql = "SELECT u.id, u.name, u.email, u.age " +
                     "FROM user_schema.users u " +
                     "JOIN account_schema.bankaccount b on u.id = b.user_id " +
                     "GROUP BY u.id " +
                     "HAVING COUNT(*) > 1";

        return jdbcTemplate.query(sql, this::userRowMapper);
    }

    @Override
    public List<User> findByAgeGreaterThanEqual(int i) {
        String sql = "SELECT u.id, u.name, u.email, u.age FROM user-schema.users u" +
                     "WHERE u.age >= ?";
        return jdbcTemplate.query(sql, this::userRowMapper, i);
    }

    @Override
    public List<User> findOldAgeUsers() {
        String sql = "SELECT u.id, u.name, u.email, u.age FROM user-schema.users u" +
                "WHERE u.age >= 60";
        return jdbcTemplate.query(sql, (rs, rowNum) ->{
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));
            return user;
        });
    }

    @Override
    public List<EmailResDto> findEmails() {
        String sql = "SELECT email FROM user_schema.users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EmailResDto emailResDto = new EmailResDto();
            emailResDto.setEmail(rs.getString("email"));
            return emailResDto;
        });
    }

    @Override
    public List<User> findUsersBwAge(int age1, int age2) {
        String sql = "SELECT u.id, u.name, u.email, u.age FROM user-schema.users u" +
                "WHERE u.age > ? AND u.age < ?";

        return jdbcTemplate.query(sql, this::userRowMapper, age1, age2);
    }
}
