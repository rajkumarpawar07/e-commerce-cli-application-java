package com.company.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
        private static final String USER = "root";
        private static final String PASSWORD = "Rajkumar@0707";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
