package com.amplee.radio.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.*;

public class CustomerService {
    private static final String URL = "jdbc:mysql://localhost:3306/users?useSSL=false&serverTimezone=UTC";
    private static final String USER = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int registerCustomer(String userId, String password, String email) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD); Statement s = c.createStatement()) {
               ResultSet rs = s.executeQuery("SELECT name FROM users.users");
               while (rs.next()) {
                   if (userId.equals(rs.getString("name"))) {
                       return 0;
                   }
               }

               rs = s.executeQuery("SELECT email FROM users.users");
               while (rs.next()) {
                   if (email.equals(rs.getString("email"))) {
                       return -1;
                   }
               }

               String hashedPassword = passwordEncoder.encode(password);
               s.execute("INSERT INTO users.users (name, password, email) VALUES ('" + userId + "' , '" + hashedPassword + "' , '" + email + "')");
        }   catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public String loginCustomer(String userId, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stat = conn.createStatement()
        ) {
            ResultSet rs = stat.executeQuery("SELECT * FROM users.users");
            while (rs.next()) {
                if (rs.getString("name").equals(userId) && passwordEncoder.matches(password, rs.getString("password"))) {
                    return userId;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
