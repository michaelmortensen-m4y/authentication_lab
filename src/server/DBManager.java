package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    static Connection connection = null;

    public static Connection connect() {

        if (connection != null) {
            return connection;
        }

        try {
            String url = "jdbc:sqlite:database.sqlite";

            connection = DriverManager.getConnection(url);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return connection;
    }
}
