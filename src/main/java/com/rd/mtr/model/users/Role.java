package com.rd.mtr.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 07-Jan-2018
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
 * @FileName  : Role
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class Role implements Serializable {

    @Column(name = "r_id")
    @JsonProperty("rId")
    private Long id;

    @Column(name = "r_name")
    @JsonProperty("rName")
    private String name;

    @Column(name = "r_description")
    @JsonProperty("rDescription")
    private String description;

    @Column(name = "r_is_active")
    @JsonProperty("rIsActive")
    private Boolean active;

    @Column(name = "r_created_on")
    @JsonProperty("rCreatedOn")
    private Date createdOn;

    @Column(name = "r_updated_on")
    @JsonProperty("rUpdatedOn")
    private Date updatedOn;

    @JsonProperty("r_permissions")
    private List<Permission> permissions;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
