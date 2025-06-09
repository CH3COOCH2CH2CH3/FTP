package server;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import server.db.Database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Application entry point.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream("server.properties")) {
            if (in != null) {
                props.load(in);
            }
        }

        int port = Integer.parseInt(props.getProperty("server.port", "2121"));
        String dbUrl = props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPassword = props.getProperty("db.password");
        Path storage = Path.of(props.getProperty("storage.path", "storage"));

        if (dbUrl != null) {
            try {
                Database.init(dbUrl, dbUser, dbPassword);
            } catch (SQLException e) {
                System.err.println("Database init failed: " + e.getMessage());
                return;
            }
        }

        try {
            Files.createDirectories(storage);
        } catch (IOException e) {
            System.err.println("Could not create storage directories: " + e.getMessage());
            return;
        }

        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);
        serverFactory.addListener("default", listenerFactory.createListener());
        FtpServer server = serverFactory.createServer();

        server.start();
    }
}
