package com.rd.mtr.response;

/*
 * @author    : Irfan Nasim
 * @Date      : 16-Apr-18
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
 * @Package   : com.rd.mtr.response
 * @FileName  : BKAPIResponse
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */

public class BKAPIResponse<T> {

    private String responseCode;
    private T responseData;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
