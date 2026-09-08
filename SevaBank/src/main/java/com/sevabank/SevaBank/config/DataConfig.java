package com.sevabank.SevaBank.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minIdle;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeOut;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idleTimeOut;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int maxLifeTime;

    @Bean
    DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setMinimumIdle(minIdle);
        dataSource.setConnectionTimeout(connectionTimeOut);
        dataSource.setIdleTimeout(idleTimeOut);
        dataSource.setMaxLifetime(maxLifeTime);
        System.out.println(dataSource.getMaximumPoolSize());
        System.out.println(dataSource.getMinimumIdle());
        System.out.println(dataSource.getConnectionTimeout());
        System.out.println(dataSource.getIdleTimeout());
        System.out.println(dataSource.getMaxLifetime());
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


}
