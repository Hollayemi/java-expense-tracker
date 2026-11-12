package com.learning.dao;

import com.learning.model.User;
import com.learning.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /** Returns the user if the credentials are right, otherwise null. */
    public User login(String username, String password) {
        String sql = "SELECT id, name, username, password, profile_image, bio FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                // Only the username goes in the query. The hash is checked in Java.
                if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                    return new User(
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getInt("id"),
                            rs.getString("profile_image"),
                            rs.getString("bio"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** user.getPassword() must be the plain password here. It is hashed before saving. */
    public boolean register(User user) {
        String sql = "INSERT INTO users (name, username, password, profile_image, bio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.setString(4, user.getProfileImage());
            stmt.setString(5, user.getBio());

            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
