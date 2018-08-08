package com.ynthm.springbootdemo.oauth2.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ynthm.springbootdemo.infrastructure.rbac.Role;
import com.ynthm.springbootdemo.infrastructure.rbac.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Ynthm
 */

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String password;
    private String username;


    private Collection<? extends GrantedAuthority> authorities;

    private boolean enabled;

    private Date lastPasswordResetDate;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.password = user.getPassword();
        this.authorities = translate(user.getRoles());
        this.enabled = user.isEnabled();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
    }

    /**
     * Translates the List<Role> to a List<GrantedAuthority>
     *
     * @param roles the input list of roles.
     * @return a list of granted authorities
     */
    private Collection<? extends GrantedAuthority> translate(List<Role> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            String name = role.getName().toUpperCase();
//            //Make sure that all roles start with "ROLE_"
//            if (!name.startsWith("ROLE_"))
//                name = "ROLE_" + name;
//            authorities.add(new SimpleGrantedAuthority(name));
//        }
//        return authorities;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
