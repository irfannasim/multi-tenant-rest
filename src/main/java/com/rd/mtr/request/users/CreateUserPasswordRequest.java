package com.rd.mtr.request.users;

import javax.validation.constraints.NotNull;

/*
 * @author    : Irfan Nasim
 * @Date      : 10-Jan-2019
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
 * @FileName  : CreateUserPasswordRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class CreateUserPasswordRequest {

    @NotNull
    private long userId;
    private String password;
    private String confirmPassword;
    private String appContextPath;

    public CreateUserPasswordRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAppContextPath() {
        return appContextPath;
    }

    public void setAppContextPath(String appContextPath) {
        this.appContextPath = appContextPath;
    }
}
