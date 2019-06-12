package com.rd.mtr.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;
import com.rd.mtr.configuration.Columns;

import java.io.Serializable;

/*
 * @author    : Irfan Nasim
 * @Date      : 28-Nov-2018
 * @version   : 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : multi-tenant-rest
 * @Package   : com.rd.mtr.model.system
 * @FileName  : Tenant
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class Tenant implements Serializable {

    @Column(name = "id")
    @JsonProperty("Id")
    private Long id;

    @Column(name = "db_name")
    @JsonProperty("dbName")
    private String dbName;

    @Column(name = "username")
    @JsonProperty("username")
    private String username;

    @Column(name = "username")
    @JsonProperty("username")
    private String password;

    @Column(name = "db_username")
    @JsonProperty("dbUsername")
    private String dbUsername;

    @Column(name = "db_password")
    @JsonProperty("dbPassword")
    private String dbPassword;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "db_server_url")
    @JsonProperty("dbServerUrl")
    private String dbServerUrl;

    @Column(name = "db_server_port")
    @JsonProperty("dbServerPort")
    private int dbServerPort;

    @Column(name = "is_ssl_enabled")
    @JsonProperty("isSSLEnabled")
    private boolean sslEnabled;

    @Columns
    @JsonProperty("poolConfiguration")
    private TenantHikariPoolConfiguration poolConfiguration;

    public Tenant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbServerUrl() {
        return dbServerUrl;
    }

    public void setDbServerUrl(String dbServerUrl) {
        this.dbServerUrl = dbServerUrl;
    }

    public int getDbServerPort() {
        return dbServerPort;
    }

    public void setDbServerPort(int dbServerPort) {
        this.dbServerPort = dbServerPort;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public TenantHikariPoolConfiguration getPoolConfiguration() {
        return poolConfiguration;
    }

    public void setPoolConfiguration(TenantHikariPoolConfiguration poolConfiguration) {
        this.poolConfiguration = poolConfiguration;
    }
}
