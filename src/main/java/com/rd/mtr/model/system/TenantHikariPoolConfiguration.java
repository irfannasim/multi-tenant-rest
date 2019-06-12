package com.rd.mtr.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rd.mtr.configuration.Column;

import java.io.Serializable;

/*
 * @author    : Irfan Nasim
 * @Date      : 29-Jan-2019
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
 * @Package   : com.rd.mtr.model.system
 * @FileName  : TenantHikariPoolConfiguration
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class TenantHikariPoolConfiguration implements Serializable {

    @Column(name = "pc_id")
    @JsonProperty("pcId")
    private Long id;

    @Column(name = "pc_pool_name")
    @JsonProperty("pcPoolName")
    private String poolName;

    @Column(name = "pc_min_idle")
    @JsonProperty("pcMinIdle")
    private int minIdle;

    @Column(name = "pc_idle_timeout")
    @JsonProperty("pcIdleTimeout")
    private int idleTimeOut;

    @Column(name = "pc_conn_timeout")
    @JsonProperty("pcConnTimeout")
    private int connTimeout;

    @Column(name = "pc_life_time")
    @JsonProperty("pcLifeTime")
    private int lifeTime;

    @Column(name = "pc_leak_detection_threshold")
    @JsonProperty("pcLeakDetectionThreshold")
    private int leakDetectionThreshold;

    @Column(name = "pc_max_pool_size")
    @JsonProperty("pcMaxPoolSize")
    private int maxPoolSize;

    public TenantHikariPoolConfiguration() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getIdleTimeOut() {
        return idleTimeOut;
    }

    public void setIdleTimeOut(int idleTimeOut) {
        this.idleTimeOut = idleTimeOut;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(int leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
}
