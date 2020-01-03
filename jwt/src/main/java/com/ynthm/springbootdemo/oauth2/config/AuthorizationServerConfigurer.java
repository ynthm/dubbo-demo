//package com.ynthm.springbootdemo.oauth2.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//
//import javax.sql.DataSource;
//
///**
// * Author : Ynthm
// * 认证服务器
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Bean
//    public TokenStore memoryTokenStore() {
//       // InMemoryTokenStore是OAuth2默认实现
//        return new InMemoryTokenStore();
//    }
//
//    @Qualifier("dataSource")
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public ClientDetailsService clientDetailsService() {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//
//    /**
//     * We here defines the security constraints on the token endpoint.
//     * We set it up to isAuthenticated, which returns true if the user is not anonymous
//     * @param security the AuthorizationServerSecurityConfigurer.
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        // 配置令牌端点(Token Endpoint)的安全约束
//        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//    }
//
//    /**
//     * Setting up the clients with a clientId, a clientSecret, a scope, the grant types and the authorities.
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // 配置客户端详情服务
//        clients.inMemory().withClient("my-trusted-client")
//                .authorizedGrantTypes("client_credentials", "password")
//                .authorities("ROLE_CLIENT","ROLE_TRUSTED_CLIENT").scopes("read","write","trust")
//                .resourceIds("oauth2-resource").accessTokenValiditySeconds(5000).secret("secret");
//    }
//
//    /**
//     * Setting up the endpointsconfigurer authentication manager.
//     * The AuthorizationServerEndpointsConfigurer defines the authorization and token endpoints and the token services.
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        // 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
//        endpoints.authenticationManager(authenticationManager).tokenStore(memoryTokenStore());
//    }
//}
