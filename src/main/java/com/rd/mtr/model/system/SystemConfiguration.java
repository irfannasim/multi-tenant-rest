package com.rd.mtr.model.system;

import com.rd.mtr.configuration.Column;

import java.io.Serializable;

public class SystemConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "auth_server_scheme")
    private String authServerScheme;

    public String getClientId() {
        return clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
