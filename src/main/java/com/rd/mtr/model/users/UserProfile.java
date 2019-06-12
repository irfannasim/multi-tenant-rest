package com.rd.mtr.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;

import java.io.Serializable;
import java.util.Date;

/*
 * @author    : Irfan Nasim
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
 * @Package   : com.rd.mtr.model
 * @FileName  : UserProfile
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class UserProfile implements Serializable {

    @Column(name = "up_id")
    @JsonProperty("upId")
    private Long id;

    @Column(name = "up_first_name")
    @JsonProperty("upFirstName")
    private String firstName;

    @Column(name = "up_last_name")
    @JsonProperty("upLastName")
    private String lastName;

    @Column(name = "up_dob")
    @JsonProperty("upDob")
    private Date dob;

    @Column(name = "up_language")
    @JsonProperty("upLanguage")
    private String language;

    @Column(name = "up_address")
    @JsonProperty("upAddress")
    private String address;

    @Column(name = "up_mobile")
    @JsonProperty("upMobile")
    private String mobile;

    @Column(name = "up_profile_img")
    @JsonProperty("upProfileImg")
    private String profileImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
