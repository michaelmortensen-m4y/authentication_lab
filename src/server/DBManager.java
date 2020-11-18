package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    static Connection connection = null;

    public static void main(String[] args) {

    }

    public static Connection connect() {

        if (connection != null) {
            return connection;
        }

        try {
            String url = "jdbc:sqlite:database.sqlite";

            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connection;
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
