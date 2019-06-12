package com.rd.mtr.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "p_id")
    @JsonProperty("pId")
    private Long id;

    @Column(name = "p_name")
    @JsonProperty("pName")
    private String name;

    @Column(name = "p_description")
    @JsonProperty("pDescription")
    private String description;

    @Column(name = "p_is_active")
    @JsonProperty("pIsActive")
    private Boolean active;

    @Column(name = "p_is_default")
    @JsonProperty("pIsDefault")
    private Boolean pDefault;

    @Column(name = "p_created_on")
    @JsonProperty("pCreatedOn")
    private Date createdOn;

    @Column(name = "p_updated_on")
    @JsonProperty("pUpdatedOn")
    private Date updatedOn;

    public Permission() {
    }

    public Permission(Long id, String name, String description, Boolean active, Boolean isDefault) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.pDefault = isDefault;
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

    public Boolean getpDefault() {
        return pDefault;
    }

    public void setpDefault(Boolean pDefault) {
        this.pDefault = pDefault;
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
}
