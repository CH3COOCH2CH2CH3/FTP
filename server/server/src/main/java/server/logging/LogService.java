package server.logging;

import server.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Persist server events to the logs table.
 */
public class LogService {
    public void log(int userId, String action, String detail, String ip, String device) throws SQLException {
        String sql = "INSERT INTO logs(user_id, action, detail, ip_address, device_info) VALUES(?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setString(3, detail);
            ps.setString(4, ip);
            ps.setString(5, device);
            ps.executeUpdate();
        }
    }
}
