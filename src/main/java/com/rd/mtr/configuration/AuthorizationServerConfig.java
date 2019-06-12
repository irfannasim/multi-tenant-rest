package com.rd.mtr.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    static final String CLIENT_ID = "MTClient";
    static final String CLIENT_SECRET = "MTSecret";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final String AUTHORITIES = "USER";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 86400;
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 864000;
    static final String RESOURCE_ID = "RESOURCE_ID";


    @Autowired
    private HikariDataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer.jdbc(dataSource);
               /* .withClient(CLIENT_ID)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
                .authorities(AUTHORITIES)
                .scopes(SCOPE_READ, SCOPE_WRITE)
               .resourceIds(RESOURCE_ID)
                .secret(CLIENT_SECRET)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);*/

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore());
        // endpoints.tokenStore(tokenStore()).approvalStoreDisabled();
    }

}