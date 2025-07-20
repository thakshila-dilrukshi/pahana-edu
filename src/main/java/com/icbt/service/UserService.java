package com.icbt.service;

import com.icbt.dao.UserDAO;
import com.icbt.model.User;
import com.icbt.util.DBConnection;

import java.sql.Connection;

public class UserService {
    private final UserDAO userDAO;
    public UserService() {
        this.userDAO = new UserDAO();
    }
    public User login(String uname, String pass) {
        return userDAO.getUser(uname, pass);
    }
}