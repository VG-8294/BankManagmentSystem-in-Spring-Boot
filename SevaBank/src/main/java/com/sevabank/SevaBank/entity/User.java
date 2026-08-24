package com.sevabank.SevaBank.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users", schema = "user_schema")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "age", nullable = false)
    private int age;
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    @Column(name="is_deleted")
    private Boolean isDeleted;

    public User() {
    }

    public User(String name, String email, String password, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
