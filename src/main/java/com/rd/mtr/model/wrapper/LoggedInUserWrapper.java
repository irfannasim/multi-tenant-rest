package com.rd.mtr.model.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;
import com.rd.mtr.configuration.Columns;
import com.rd.mtr.model.users.User;

/*
 * @author    : Irfan Nasim
 * @Date      : 01-Jan-2019
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
 * @Package   : com.rd.mtr.model
 * @FileName  : LoggedInUserWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class LoggedInUserWrapper {

    @Column(name = "u_first_name")
    @JsonProperty("uFirstName")
    private String uFirstName;

    @Column(name = "u_last_name")
    @JsonProperty("uLastName")
    private String uLastName;

    @Column(name = "u_type")
    @JsonProperty("uType")
    private String uType;

    @JsonProperty("user")
    private User user;

    public LoggedInUserWrapper() {
    }

    public String getuFirstName() {
        return uFirstName;
    }

    public void setuFirstName(String uFirstName) {
        this.uFirstName = uFirstName;
    }

    public String getuLastName() {
        return uLastName;
    }

    public void setuLastName(String uLastName) {
        this.uLastName = uLastName;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
