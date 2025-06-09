package server.files;

import server.db.Database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handle file storage and metadata management.
 */
public class FileService {
    private final Path storageRoot;

    public FileService(Path storageRoot) throws IOException {
        this.storageRoot = storageRoot;
        Files.createDirectories(storageRoot);
    }

    /**
     * Save a file for the given user.
     */
    public void saveFile(int userId, String filename, InputStream data) throws IOException, SQLException {
        Path userDir = storageRoot.resolve(String.valueOf(userId));
        Files.createDirectories(userDir);
        Path target = userDir.resolve(filename);
        Files.copy(data, target, StandardCopyOption.REPLACE_EXISTING);

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO files(user_id,filename,filepath,size) VALUES(?,?,?,?)")) {
            ps.setInt(1, userId);
            ps.setString(2, filename);
            ps.setString(3, target.toString());
            ps.setLong(4, Files.size(target));
            ps.executeUpdate();
        }
    }
}
