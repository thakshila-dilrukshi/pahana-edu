package com.icbt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/pahana-edu";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found",e.getMessage());
            }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        }

        return connection;
    }
}