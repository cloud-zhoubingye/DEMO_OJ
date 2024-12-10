//package com.example.admin_demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()  // 禁用 CSRF 保护
//                .authorizeRequests()
//                .antMatchers("/user/register", "/user/login").permitAll() // 开放注册和登录接口
//                .anyRequest().authenticated();
//        return http.build();
//    }
//}
