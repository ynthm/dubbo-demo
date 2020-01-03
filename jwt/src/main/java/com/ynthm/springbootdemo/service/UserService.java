package com.ynthm.springbootdemo.service;

import com.ynthm.springbootdemo.infrastructure.dao.RoleDao;
import com.ynthm.springbootdemo.infrastructure.dao.UserDao;
import com.ynthm.springbootdemo.infrastructure.rbac.Role;
import com.ynthm.springbootdemo.infrastructure.rbac.RoleName;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.infrastructure.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Ynthm
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public String register(User user) {
        String username = user.getName();
        if (userDao.findByName(username) != null) {
            return "用户已存在";
        }

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        List<Role> roles = new ArrayList<>();
        Role defaultRole = roleDao.findByName("ROLE_USER");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setName(RoleName.ROLE_ADMIN);
            defaultRole = roleDao.save(defaultRole);
        }

        roles.add(defaultRole);
        user.setRoles(roles);
        userDao.save(user);
        return "success";
    }


    public String refreshToken(String oldToken) {
        String token = oldToken.substring(7);
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }

}
