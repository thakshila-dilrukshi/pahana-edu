package com.icbt.dao;

import com.icbt.model.User;
import com.icbt.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUser(String username, String password) {
        String sql = "select * from users where username=? and password=?";

        try (Connection con = DBConnection.getConnection();) {
            PreparedStatement ps = con.prepareStatement(sql);
            {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}