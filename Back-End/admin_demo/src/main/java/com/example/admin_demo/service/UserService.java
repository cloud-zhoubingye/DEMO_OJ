//package com.example.admin_demo.service;
//
//import com.example.admin_demo.dao.UserDao;
//import com.example.admin_demo.entity.User;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//
//import java.sql.SQLException;
//
//public class UserService {
//
//    private final UserDao userDao = new UserDao();
//
//    // 注册用户
//    public boolean registerUser(User user) throws SQLException {
//        // 加密密码
//        String hashedPassword = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt());
//        user.setPasswordHash(hashedPassword);
//        return userDao.registerUser(user);
//    }
//
//    // 用户登录
//    public boolean loginUser(String username, String password) throws SQLException {
//        User user = userDao.loginUser(username);
//        if (user == null) {
//            return false; // 用户名不存在
//        }
//        // 验证密码
//        return BCrypt.checkpw(password, user.getPasswordHash());
//    }
//}
package com.example.admin_demo.service;

import com.example.admin_demo.dao.UserDao;
import com.example.admin_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    // 存储邮箱与验证码的映射
    private final Map<String, String> emailVerificationCodes = new HashMap<>();


    // 注册用户
    public boolean registerUser(String username,String password,String email) throws SQLException {
        return userDao.registerUser(username,password,email);
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;

    // 发送验证码
    public boolean sendResetCode(String email) {
        try {
            // 检查邮箱是否存在
            User user = userDao.findUserByEmail(email);
            if (user == null) return false;

            // 生成 6 位验证码
            String code = String.format("%06d", new Random().nextInt(999999));
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // 验证码有效期 10 分钟

            // 保存验证码到数据库
            userDao.updateResetCode(email, code, expirationTime);

            // 发送验证码邮件
            String subject = "密码重置验证码";
            String content = "您的验证码是：" + code + ",请在10分钟内完成验证。";
            emailService.sendEmail(email, subject, content);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 重置密码
    public boolean resetPassword(String email, String code, String newPassword) {
        try {
            // 验证验证码
            if (!userDao.validateResetCode(email, code)) {
                return false; // 验证码无效或过期
            }

            // 更新密码
            userDao.updatePasswordByEmail(email, newPassword);

            // 清除验证码
            userDao.clearResetCode(email);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 普通用户登录
    public boolean loginUser(String username, String password) throws SQLException {
        // 查找用户
        User user = userDao.findUserByUsername(username);
        if (user == null || !"user".equals(user.getRole())) {
            return false; // 用户名不存在或不是普通用户
        }

        // 验证密码
        if (password.equals(user.getPasswordHash())) {
            // 更新用户最后登录时间
            userDao.updateLastLoginTime(username);
            return true;
        }
        return false;
    }

    // 管理员登录
    public boolean loginAdmin(String username, String password) throws SQLException {
        // 查找用户
        User user = userDao.findUserByUsername(username);
        if (user == null || !"admin".equals(user.getRole())) {
            return false; // 用户名不存在或不是管理员
        }

        // 验证密码
        if (password.equals(user.getPasswordHash())) {
            // 更新管理员最后登录时间
            userDao.updateLastLoginTime(username);
            return true;
        }
        return false;
    }


    /**
     * 生成并发送验证码
     *
     * @param email 收件人邮箱
     * @return 生成的验证码
     */
    public String generateAndSendVerificationCode(String email) throws SQLException {
        // 检查邮箱是否存在
        User user = userDao.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("邮箱不存在！");
        }

        // 生成随机验证码
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        emailVerificationCodes.put(email, verificationCode);

        // 发送邮件
        String subject = "重置密码验证码";
        String content = "您的验证码是：" + verificationCode + "，有效期为 5 分钟。";
        emailService.sendEmail(email, subject, content);

        System.out.println("验证码已发送到邮箱：" + email + "，验证码是：" + verificationCode);
        return verificationCode;
    }

    /**
     * 校验验证码并重置密码
     *
     * @param email 收件人邮箱
     * @param newPassword 新密码
     * @param verificationCode 验证码
     * @return 是否重置成功
     */
    public boolean resetPasswordWithVerificationCode(String email, String newPassword, String verificationCode) throws SQLException {
        // 校验验证码
        String correctCode = emailVerificationCodes.get(email);
        if (correctCode == null || !correctCode.equals(verificationCode)) {
            throw new RuntimeException("验证码错误或已过期！");
        }

        // 更新密码
        return userDao.updatePasswordByEmail(email, newPassword);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户对象
     * @throws SQLException 数据库操作异常
     */
    public User getUserByUsername(String username) throws SQLException {
        return userDao.findUserByUsername(username);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.findAllUsers(); // 调用 DAO 层获取用户列表
    }

}

