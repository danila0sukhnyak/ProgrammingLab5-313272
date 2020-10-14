package org.example.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static Connection connection;
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(DatabaseUtil.class.getResourceAsStream("/jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://pg:5432/studs",
                    "s284187",
                    "umr350");
            connection.setAutoCommit(false);
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
