package com.rd.mtr.request.users;

import com.rd.mtr.model.users.User;

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
 * @FileName  : UpdateUserRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class UpdateUserRequest {

    @NotNull
    private long userId;
    @NotNull
    private String email;
    private String username;
    private String name;
    private long profileId;
    private long roleId;
    private String profileImg;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.name = user.getProfile().getFirstName();
        this.profileId = user.getProfile().getId();
        this.roleId = user.getRoleId();
        this.profileImg = user.getProfile().getProfileImg();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public void setName(String name) {
        this.name = name;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
