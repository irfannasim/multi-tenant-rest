package com.rd.mtr.configuration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
@Component
public class AppConfigProperties {
    private final Logger logger = LogManager.getLogger(AppConfigProperties.class);

    private String clientId;
    private String clientSecret;
    private String authServerScheme;

    public AppConfigProperties() {
    }

    @PostConstruct
    public void init() {
        /*try {
            SystemConfiguration properties = configurationDAO.findOne(null);
            this.setClientId(getClientId());
            this.setClientSecret(properties.getClientSecret());
            this.setAuthServerScheme(properties.getAuthServerScheme());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }*/
    }

    public AppConfigProperties(String client_id, String client_secret, String auth_server_scheme) {
        this.clientId = client_id;
        this.clientSecret = client_secret;
        this.authServerScheme = auth_server_scheme;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthServerScheme() {
        return authServerScheme;
    }

    public void setAuthServerScheme(String authServerScheme) {
        this.authServerScheme = authServerScheme;
    }

    public Logger getLogger() {
        return logger;
    }
}
