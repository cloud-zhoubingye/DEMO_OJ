package com.example.admin_demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_demo.entity.User;
import com.example.admin_demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 用户注册
    @RequestMapping("/register")
    public ResponseEntity<JSONObject> registerUser(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        String username = param.getString("username");
        String password = param.getString("password");
        String email = param.getString("email");
        try {
            if (userService.registerUser(username,password,email)) {
                json.put("result","success");
                json.put("result_code", 0);
                json.put("result_msg", "用户注册成功");
            } else {
                json.put("result","failure");
                json.put("result_code", 1);
                json.put("result_msg", "用户注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result","failure");
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
        System.out.println("Response JSON: " + json.toString());
        return ResponseEntity.ok(json); // 确保返回 HTTP 200 状态码
    }

    // 普通用户登录
    @RequestMapping("/user_login")
    public JSONObject loginUser(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        String username = param.getString("username");
        String password = param.getString("password");

        try {
            if (userService.loginUser(username, password)) {
                json.put("result","success");
                json.put("result_code", 0);
                json.put("result_msg", "普通用户登录成功");
            } else {
                json.put("result","failure");
                json.put("result_code", 1);
                json.put("result_msg", "用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result","failure");
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
        return json;
    }

    // 管理员登录
    @RequestMapping("/admin_login")
    public JSONObject loginAdmin(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        String username = param.getString("username");
        String password = param.getString("password");
        System.out.println(username + " " + password);
        try {
            if (userService.loginAdmin(username, password)) {
                json.put("result","success");
                json.put("result_code", 0);
                json.put("result_msg", "管理员登录成功");
            } else {
                json.put("result_code", 1);
                json.put("result_msg", "用户名或密码错误，或者不是管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "系统异常：" + e.getMessage());
        }
        System.out.println("/admin_login: " + json);
        return json;
    }

    // 发送验证码
    @PostMapping("/email_captcha")
    public JSONObject sendResetCode(@RequestBody JSONObject param) {
        System.out.println(param);
        String email = param.getString("email");
        JSONObject response = new JSONObject();
        if (userService.sendResetCode(email)) {
            response.put("result","success");
            response.put("result_code", 0);
            response.put("result_msg", "验证码已发送，请检查您的邮箱！");
        } else {
            response.put("result_code", 1);
            response.put("result_msg", "邮箱不存在或发送失败！");
        }
        return response;
    }

    // 重置密码
    @PostMapping("/reset_password")
    public JSONObject resetPassword(@RequestBody JSONObject param) {
        String email = param.getString("email");
        String code = param.getString("email_captcha");
        String newPassword = param.getString("password");
        JSONObject response = new JSONObject();
        if (userService.resetPassword(email, code, newPassword)) {
            response.put("result","success");
            response.put("result_code", 0);
            response.put("result_msg", "密码重置成功！");
        } else {
            response.put("result_code", 1);
            response.put("result_msg", "验证码错误或已过期！");
        }
        return response;
    }

    /**
     * 检查用户登录状态并跳转
     *
     * @param param 包含用户名参数
     * @return 返回跳转结果
     */
    @RequestMapping("/isUserLogin")
    public JSONObject checkUserLoginStatus(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        String username = param.getString("username");

        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                json.put("status", "error");
                json.put("message", "用户不存在");
                return json;
            }

            LocalDateTime lastLoginTime = user.getLastLoginTime();
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Last Login Time: " + lastLoginTime);
            System.out.println("Current Time: " + now);
            System.out.println("Minutes difference: " + Duration.between(lastLoginTime, now).toMinutes());

            if (lastLoginTime != null && Duration.between(lastLoginTime, now).toMinutes() <= 30 && user.getRole().equals("user")) {
                // 距离上次登录时间不超过半小时，允许直接跳转
                json.put("status", "ok");
                json.put("message", "允许直接跳转");
            } else {
                // 否则要求重新登录
                json.put("status", "error");
                json.put("message", "需要重新登录");
                json.put("redirectUrl", "/login"); // 示例：跳转到登录页面
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误：" + e.getMessage());
        }

        return json;
    }

    /**
     * 检查用户登录状态并跳转
     *
     * @param param 包含用户名参数
     * @return 返回跳转结果
     */
    @RequestMapping("/isAdminLogin")
    public JSONObject checkAdminLoginStatus(@RequestBody JSONObject param) {
        JSONObject json = new JSONObject();
        String username = param.getString("username");

        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                json.put("status", "error");
                json.put("message", "用户不存在");
                return json;
            }

            LocalDateTime lastLoginTime = user.getLastLoginTime();
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Last Login Time: " + lastLoginTime);
            System.out.println("Current Time: " + now);
            System.out.println("Minutes difference: " + Duration.between(lastLoginTime, now).toMinutes());
            if (lastLoginTime != null && Duration.between(lastLoginTime, now).toMinutes() <= 30 && user.getRole().equals("admin")) {
                System.out.println("1111111");
                // 距离上次登录时间不超过半小时，允许直接跳转
                json.put("status", "ok");
                json.put("message", "允许直接跳转");
            } else {
                // 否则要求重新登录
                json.put("status", "error");
                json.put("message", "需要重新登录");
                json.put("redirectUrl", "/login"); // 示例：跳转到登录页面
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "系统错误：" + e.getMessage());
        }

        return json;
    }

    @RequestMapping("/userManagement")
    public JSONObject getUserList() {
        JSONObject json = new JSONObject();
        try {
            List<User> userList = userService.getAllUsers(); // 调用服务层获取所有用户
            JSONArray userArray = new JSONArray();
            for (User user : userList) {
                JSONObject userJson = new JSONObject();
                userJson.put("username", user.getUsername());
                userJson.put("email", user.getEmail());
                userJson.put("password", user.getPasswordHash());
                userJson.put("role", user.getRole());
                userArray.add(userJson);
        }
            json.put("result", "success");
            json.put("users", userArray);
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "failure");
            json.put("message", "系统异常：" + e.getMessage());
        }
        System.out.println("/userManagement:" + json);
        return json;
    }

}
