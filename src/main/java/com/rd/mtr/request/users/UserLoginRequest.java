package com.rd.mtr.request.users;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/*
 * @author    : Irfan Nasim
 * @Date      : 22-Nov-2018
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
 * @FileName  : UserLoginRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class UserLoginRequest implements Serializable {

    @NotNull
    private String userName;
    @NotNull
    private String password;

    public UserLoginRequest() {
    }

    public UserLoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


