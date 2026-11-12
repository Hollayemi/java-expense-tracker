package com.learning.dao;
import com.learning.model.User;
import com.learning.util.DBConnection;

import java.sql.*;

public class UserDAO {
    public User login (String username, String password) {
        String sql = "Select * FROM user WHERE username = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getInt("id"),
                        resultSet.getString("profileImage"),
                        resultSet.getString("bio"),
                        resultSet.getString("password")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    public boolean register (User user) {
        String sql = "INSERT INTO user (name, username, bio, profileImage, password) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getName());
            stmt.setString(1, user.getUsername());
            stmt.setString(1, user.getBio());
            stmt.setString(1, user.getProfileImage());
            stmt.setString(1, user.getPassword());

             stmt.executeUpdate();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}


