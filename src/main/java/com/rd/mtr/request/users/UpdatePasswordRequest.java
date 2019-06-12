package com.rd.mtr.request.users;

import javax.validation.Valid;

/*
 * @author    : irfan
 * @Date      : 17-Dec-2018
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
 * @FileName  : UpdatePasswordRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class UpdatePasswordRequest {

    @Valid
    private String currentPassword;
    @Valid
    private String newPassword;
    @Valid
    private String confirmPassword;

    public UpdatePasswordRequest() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
