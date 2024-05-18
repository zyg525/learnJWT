package com.zyg.security;

import com.zyg.model.User;
import com.zyg.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 负责调用MyUserService获取用户详细信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    MyUserService myUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = myUserService.getUserByName(username);
        return new UserDetailsImpl(user);
    }
}
