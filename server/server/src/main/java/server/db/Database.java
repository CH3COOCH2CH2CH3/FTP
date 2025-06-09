package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple helper for obtaining JDBC connections.
 */
public class Database {
    private static String url;
    private static String user;
    private static String password;

    /**
     * Initialize the connection information and test the connection.
     */
    public static void init(String url, String user, String password) throws SQLException {
        Database.url = url;
        Database.user = user;
        Database.password = password;
        try (Connection conn = getConnection()) {
            // test connection
        }
    }

    /**
     * Get a new database connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
