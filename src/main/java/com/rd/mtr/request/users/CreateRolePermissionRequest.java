package com.rd.mtr.request.users;

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
 * @FileName  : CreateRolePermissionRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class CreateRolePermissionRequest {

    private long pId;
    private String pName;
    private Boolean pIsDefault;
    private Boolean pIsActive;
    private String pDescription;

    public CreateRolePermissionRequest() {
    }


    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Boolean getpIsDefault() {
        return pIsDefault;
    }

    public void setpIsDefault(Boolean pIsDefault) {
        this.pIsDefault = pIsDefault;
    }

    public Boolean getpIsActive() {
        return pIsActive;
    }

    public void setpIsActive(Boolean pIsActive) {
        this.pIsActive = pIsActive;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
