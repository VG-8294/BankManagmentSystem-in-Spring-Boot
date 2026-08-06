package com.sevabank.SevaBank.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name ="admin", schema = "admin_schema")
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;
    @Column(name = "email", nullable = false, unique = true)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;

    public Admin() {
        this.mail = "Seva";
        this.password = "Bank";
    }

    @Override
    public String toString() {
        return "Admin{" +
                "admin_id=" + admin_id +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
