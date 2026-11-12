package com.learning.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Defaults work locally; override with environment variables when deploying.
    private static final String URL  = env("DB_URL",  "jdbc:postgresql://localhost:5432/expense_tracker");
    private static final String USER = env("DB_USER", "admin");
    private static final String PASS = env("DB_PASS", "12345");

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
