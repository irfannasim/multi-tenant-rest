package com.rd.mtr.enums;

/*
 * @author    : Irfan Nasim
 * @Date      : 04-Dec-2018
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
 * @Project   : multi-tenant-rest
 * @Package   : com.rd.mtr.enums
 * @FileName  : UserTypeEnum
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */

public enum UserTypeEnum {

    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    private String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
