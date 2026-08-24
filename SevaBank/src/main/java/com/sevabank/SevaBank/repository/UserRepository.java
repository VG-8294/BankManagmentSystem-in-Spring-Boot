package com.sevabank.SevaBank.repository;

import com.sevabank.SevaBank.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


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

    public boolean existsByEmail(String email) {
        String sql = "SELECT EXISTS (" +
                "SELECT 1 FROM user_schema.users " +
                "WHERE email = ?" +
                ")";

        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

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

    public boolean updateUser(User user) {
        String sql = "UPDATE user_schema.users " +
                     "SET name = ?, email = ?, password = ?, age = ?, updated_at = ? " +
                     "WHERE id = ?";

        int rows = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getAge(), LocalDateTime.now(), user.getId());

        return rows == 1;
    }

    public  List<User> findAll() {
        String sql = "SELECT u.id, u.name, u.email, u.age " +
                     "FROM user_schema.users u ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setAge(rs.getInt("age"));
                    return user;
        });
    }

    public List<User> findByEmail(String email) {
        String sql = "SELECT u.id, u.name, u.email, u.age "+
                     "FROM user_schema.users u " +
                     "WHERE email = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
                            User user = new User();
                            user.setId(rs.getLong("id"));
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setAge(rs.getInt("age"));
                            return user;
        }, email);
    }

    public List<User> findUsersWithMultipleAccounts() {
        String sql = "SELECT u.id, u.name, u.email, u.age " +
                     "FROM user_schema.users u " +
                     "JOIN account_schema.bankaccount b on u.id = b.user_id " +
                     "GROUP BY u.id " +
                     "HAVING COUNT(*) > 1";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setAge(rs.getInt("age"));
                        return user;
        });
    }

    public List<User> findByAgeGreaterThanEqual(int i) {
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
}
