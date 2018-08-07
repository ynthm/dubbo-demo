package com.ynthm.springbootdemo.oauth2;

import com.ynthm.springbootdemo.infrastructure.dao.RoleDao;
import com.ynthm.springbootdemo.infrastructure.dao.UserDao;
import com.ynthm.springbootdemo.infrastructure.rbac.Role;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.oauth2.config.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Ynthm
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = user.getRoles();

        Long roleId;
        String roleName = null;
        Role tempRole;
        for (Role role : roles) {
            roleId = role.getId();
            roleName = roleDao.findById(roleId).get().getName();
            if (!StringUtils.isEmpty(roleName)) {
                authorities.add(new SimpleGrantedAuthority(roleName));
            }
        }

        return new CustomUserDetails(user);
    }
}
