package com.rd.mtr.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;
import com.rd.mtr.configuration.Columns;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/*
 * @author    : Irfan Nasim
 * @Date      : 19-Nov-2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : MultiTenant
 * @Package   : com.rd.mtr.model
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class User implements Serializable {

    @Column(name = "u_id")
    @JsonProperty("uId")
    private Long id;

    @Column(name = "u_username")
    @JsonProperty("uUsername")
    private String username;

    @Column(name = "u_email")
    @JsonProperty("uEmail")
    private String email;

    @Column(name = "u_user_type")
    @JsonProperty("uUserType")
    private String userType;

    @Column(name = "u_password")
    @JsonProperty("uPassword")
    @JsonIgnore
    private String password;

    @Column(name = "u_is_active")
    @JsonProperty("uIsActive")
    private Boolean active;

    @Column(name = "u_created_on")
    @JsonProperty("uCreatedOn")
    private Date createdOn;

    @Column(name = "u_updated_on")
    @JsonProperty("uUpdatedOn")
    private Date updatedOn;

    @Column(name = "u_role_id")
    @JsonProperty("rRoleId")
    @JsonIgnore
    private Long roleId;

    @Columns
    @JsonProperty("uProfile")
    private UserProfile profile;

    @Columns
    @JsonProperty("uRole")
    private Role role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
