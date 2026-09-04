package com.sevabank.SevaBank.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class HikariConfig {

    private final DataSource dataSource;

    public HikariConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void getDataSource() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @PostConstruct
    public void checkHikari() {
        HikariDataSource hikari = (HikariDataSource) dataSource;

        System.out.println("Maximum Pool Size: " + hikari.getMaximumPoolSize());
        System.out.println("Minimum Idle: " + hikari.getMinimumIdle());
        System.out.println("Connection Timeout: " + hikari.getConnectionTimeout());
        System.out.println("Idle Timeout: " + hikari.getIdleTimeout());
        System.out.println("Max Lifetime: " + hikari.getMaxLifetime());
        System.out.println("Active: " + hikari.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Idle: " + hikari.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Total: " + hikari.getHikariPoolMXBean().getTotalConnections());
        System.out.println("Waiting: " + hikari.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
}
