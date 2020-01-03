package com.ynthm.springbootdemo.oauth2;

import com.ynthm.springbootdemo.infrastructure.dao.RoleDao;
import com.ynthm.springbootdemo.infrastructure.dao.UserDao;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.oauth2.config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        return new CustomUserDetails(user);
    }
}
