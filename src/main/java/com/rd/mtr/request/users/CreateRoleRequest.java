package com.rd.mtr.request.users;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * @author    : Naeem Saeed
 * @Date      : 11-Jan-2019
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
 * @FileName  : CreateRoleRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class CreateRoleRequest implements Serializable {

    private long rId;
    private String rName;
    private String rDescription;
    private Date createdOn;
    private Date updatedOn;
    private Boolean rIsActive;
    private List<CreateRolePermissionRequest> permissions;

    public CreateRoleRequest() {
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrDescription() {
        return rDescription;
    }

    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
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

    public Boolean getrIsActive() {
        return rIsActive;
    }

    public void setrIsActive(Boolean rIsActive) {
        this.rIsActive = rIsActive;
    }

    public List<CreateRolePermissionRequest> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<CreateRolePermissionRequest> permissions) {
        this.permissions = permissions;
    }
}
