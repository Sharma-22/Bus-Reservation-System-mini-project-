package com.aip.bus.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bus_reservation?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    // Change this to your own MySQL password before running the project.
    private static final String PASSWORD = "root123";

    private DBConnection() { }

    public static Connection getConnection() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
}
