package br.com.jdbc.studies;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");

    public Connection savedConnection() {
        try {
            return createDataSource().getConnection();
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/cash_bank");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
