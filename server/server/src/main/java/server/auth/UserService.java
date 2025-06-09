package server.auth;

import server.db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Basic user related operations.
 */
public class UserService {

    public boolean register(String username, String passwordHash, String email) throws SQLException {
        String sql = "INSERT INTO users(username,password_hash,email) VALUES(?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, email);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean validateLogin(String username, String passwordHash) throws SQLException {
        String sql = "SELECT id FROM users WHERE username=? AND password_hash=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean verifyOtp(int userId, String otpCode) throws SQLException {
        String sql = "SELECT user_id FROM user_otp WHERE user_id=? AND otp_code=? AND expires_at>NOW()";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, otpCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
