package com.example.admin_demo.dao;

import org.springframework.stereotype.Repository;

import com.example.admin_demo.entity.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库访问层，用于操作用户表
 */
@Repository
public class UserDao {

    // 数据库连接配置
    private final String dbUrl = "jdbc:mysql://localhost:3306/oj_database"; // 替换为你的数据库 URL
    private final String dbUsername = "root"; // 替换为你的数据库用户名
    private final String dbPassword = "flt020913"; // 替换为你的数据库密码

    public boolean registerUser(String username, String password, String email) throws SQLException {
        // SQL 插入语句
        String sql = "INSERT INTO users (username, password_hash, email, role) VALUES (?, ?, ?, 'user')";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置参数
            stmt.setString(1, username);
            stmt.setString(2, password); // 密码需要加密后存储
            stmt.setString(3, email);

            // 执行插入
            int rows = stmt.executeUpdate();
            return rows > 0; // 返回是否成功插入
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 将异常抛出，供 Service 层处理
        }
    }


    // 查找用户（登录用）
    public User findUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username); // 应用用户的用户名
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                // 确保提取 last_login_time
                Timestamp lastLoginTimestamp = rs.getTimestamp("last_login_time");
                if (lastLoginTimestamp != null) {
                    user.setLastLoginTime(lastLoginTimestamp.toLocalDateTime());
                }
                return user;
            }
            return null;
        }
    }

    // 更新最后登录时间
    public void updateLastLoginTime(String username) throws SQLException {
        String sql = "UPDATE users SET last_login_time = NOW() WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username); // 应用用户的用户名
            pstmt.executeUpdate();
        }
    }


    // 查找用户
    public User findUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }

    // 更新验证码
    public void updateResetCode(String email, String code, LocalDateTime expirationTime) throws SQLException {
        String sql = "UPDATE users SET reset_code = ?, reset_code_expiration = ? WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setTimestamp(2, Timestamp.valueOf(expirationTime));
            stmt.setString(3, email);
            stmt.executeUpdate();
        }
    }

    // 验证验证码
    public boolean validateResetCode(String email, String code) throws SQLException {
        String sql = "SELECT reset_code_expiration FROM users WHERE email = ? AND reset_code = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDateTime expiration = rs.getTimestamp("reset_code_expiration").toLocalDateTime();
                return expiration.isAfter(LocalDateTime.now());
            }
        }
        return false;
    }

    // 清除验证码
    public void clearResetCode(String email) throws SQLException {
        String sql = "UPDATE users SET reset_code = NULL, reset_code_expiration = NULL WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    // 更新密码
    public boolean updatePasswordByEmail(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.executeUpdate();
            return true;
        }
    }

    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT username, email, password_hash, role FROM users";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }

}