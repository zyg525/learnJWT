package com.zyg.controller;

import cn.hutool.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String login(String username, String password) {
        // 验证用户名和密码
        System.out.println("username="+username+"  password="+password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);

        // 生成token
        String token = JWT.create()
                 .setPayload("username", username)
                 .setKey("myKey".getBytes(StandardCharsets.UTF_8))
                 .sign();

        return token;
    }

    /**
     * 角色是user时才能访问这个路径
     * @param username
     * @param password
     * @return
     */
    @PreAuthorize("hasAuthority('user')")
    @RequestMapping("/select")
    public String select(String username, String password) {
        return "username="+username+" password="+password;
    }
}
