package com.ynthm.springbootdemo.oauth2.config;

import com.ynthm.springbootdemo.oauth2.EntryPointUnauthorizedHandler;
import com.ynthm.springbootdemo.oauth2.JwtAuthenticationTokenFilter;
import com.ynthm.springbootdemo.oauth2.RestAccessDeniedHandler;
import com.ynthm.springbootdemo.oauth2.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Author : Ynthm
 * 暴露 AuthenticationManager
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 重载 oauth2 可以使用
     *
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailService;
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // we don't need CSRF because our token is invulnerable
        httpSecurity.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
                .accessDeniedHandler(restAccessDeniedHandler);

        httpSecurity.authorizeRequests().antMatchers("/", "/index", "/upload").permitAll()
                .antMatchers("/css/**", "/js/**", "/image/**", "/webjars/**").permitAll()
                //.antMatchers("/templates/**", "/static/**").permitAll()
                .antMatchers("/views/**", "/res/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .antMatchers("/users/login").permitAll()
                // Un-secure H2 Database
                .antMatchers("/h2-console/**/**").permitAll()
                //除了上面申明的其余的都要权限访问
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/users/login").permitAll().and()
                .logout().permitAll();

        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 默认退出请求是/logout
        httpSecurity.logout().logoutSuccessUrl("/");
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath
                )

                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                )

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
}
