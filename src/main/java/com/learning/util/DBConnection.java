package com.learning.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String URL = "jdbc:postgresql://localhost:5432/expense-tracker";
    private static String USER = "admin";
    private static String PASS = "12345";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("PostgreSQL Driver not found!");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }
}
