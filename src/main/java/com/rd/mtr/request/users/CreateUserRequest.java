package com.rd.mtr.request.users;

import javax.validation.constraints.NotNull;

/*
 * @author    : Irfan Nasim
 * @Date      : 09-Jan-2019
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
 * @Package   : com.rd.mtr.request
 * @FileName  : CreateUserRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class CreateUserRequest {

    @NotNull
    private String email;
    private String username;
    private String name;
    private String language;
    private long roleId;
    private String appContextPath;

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getAppContextPath() {
        return appContextPath;
    }

    public void setAppContextPath(String appContextPath) {
        this.appContextPath = appContextPath;
    }
}
