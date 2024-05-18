package com.zyg.service.impl;

import com.zyg.model.Role;
import com.zyg.model.User;
import com.zyg.model.enumerate.RoleType;
import com.zyg.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 负责从数据库中查询用户详细信息
 */
@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUserByName(String username) {
        if(!"zyg".equals(username)) {
            throw new RuntimeException("用户不存在！");
        }

        List<Role> roles = Arrays.asList(new Role(RoleType.USER));
        return new User(username, passwordEncoder.encode("123"), roles);
    }
}
