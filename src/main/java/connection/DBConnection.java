package connection;

import exception.CredentialsInvalidException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {

        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/db_car",
                    "postgres",
                    "password");
            System.out.println("DB connection successfully !");
        } catch (SQLException e) {
            throw new CredentialsInvalidException("Database credentials is invalid");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
            System.out.println("DB connection closed !");
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        }
    }
}
