package com.sevabank.SevaBank.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="admin", schema = "admin_schema")
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

    public Long getAdmin_id() {
        return admin_id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
