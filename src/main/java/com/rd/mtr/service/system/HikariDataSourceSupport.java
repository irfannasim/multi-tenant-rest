package com.rd.mtr.service.system;

import com.rd.mtr.model.system.Tenant;
import com.rd.mtr.configuration.JdbcEntityManager;
import com.rd.mtr.util.BKCoreUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
 * @author    : Irfan Nasim
 * @Date      : 28-Nov-2018
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
 * @Package   : com.rd.mtr.service.system
 * @FileName  : HikariDataSourceSupport
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class HikariDataSourceSupport {

    private final Logger logger = LogManager.getLogger(HikariDataSourceSupport.class);

    @Autowired
    private HikariDataSource tenantDataSource;
    private final String TENANTS_DATASOURCE = "tenantsDataSource";
    private Map<String, HikariDataSource> dataSources = new HashMap<>();

    public HikariDataSourceSupport() {
    }

    public HikariDataSource getDataSource(String tenantId) {

        if (BKCoreUtil.isNull(tenantId)) {
            dataSources.put(TENANTS_DATASOURCE, tenantDataSource);
        }
        String key = getKey(tenantId);
        try {
            if (!dataSources.containsKey(key)) {
                synchronized (getMonitor()) {
                    if (!dataSources.containsKey(key)) {
                        logger.info("creating new dataSource with name:" + key);
                        HikariConfig config = getDBConfig(tenantId);
                        dataSources.put(key, new HikariDataSource(config));
                    }
                }
            }
        } catch (SQLException sqle) {
            logger.error("TenantId:" + tenantId + " " + "HikariDataSourceSupport - getting datasource failed...", sqle.fillInStackTrace());
        }
        return dataSources.get(key);
    }

    public Connection getConnection(String tenantId) throws SQLException {
        return getDataSource(tenantId).getConnection();
    }

    private String getKey(String tenantId) {
        if (tenantId != null) {
            return tenantId + "DataSource";
        }
        return TENANTS_DATASOURCE;
    }

    private Object getMonitor() {
        return this.getClass();
    }

    private HikariConfig getDBConfig(String tenantId) throws SQLException {
        logger.error("TenantId:" + tenantId + " " + "HikariDataSourceSupport - getDBConfig method called...");

        HikariConfig config = new HikariConfig();
        Connection conn = null;
        try {
            conn = tenantDataSource.getConnection();
            Tenant tenant = JdbcEntityManager.getSingle(
                    conn,
                    Tenant.class,
                    "SELECT t.id, t.db_name, t.username, t.password, t.db_username, t.db_password, name, t.db_server_url, t.db_server_port, t.is_ssl_enabled," +
                            "pc.id AS pc_id, pc.pool_name AS pc_pool_name, pc.minimum_idle AS pc_min_idle, pc.idle_timeout AS pc_idle_timeout, pc.connection_timeout AS pc_conn_timeout," +
                            "pc.max_life_time AS pc_life_time, pc.leak_detection_threshold AS pc_leak_detection_threshold, pc.max_pool_size AS pc_max_pool_size " +
                            "FROM tenants t JOIN tenant_hikari_pool_configuration pc ON t.pool_configuration_id = pc.id " +
                            "WHERE name = ? ",
                    tenantId);

            String url = "jdbc:mysql://" + tenant.getDbServerUrl() + ":" + tenant.getDbServerPort() + "/" + tenant.getDbName() + "?useSSL=" + tenant.isSslEnabled();
            config.setJdbcUrl(url);
            config.setUsername(tenant.getDbUsername());
            config.setPassword(tenant.getDbPassword());
            config.setPoolName(tenant.getPoolConfiguration().getPoolName());
            config.setMaximumPoolSize(tenant.getPoolConfiguration().getMaxPoolSize());
            config.setMinimumIdle(tenant.getPoolConfiguration().getMinIdle());
            config.setIdleTimeout(tenant.getPoolConfiguration().getIdleTimeOut());
            config.setConnectionTimeout(tenant.getPoolConfiguration().getConnTimeout());
            config.setMaxLifetime(tenant.getPoolConfiguration().getLifeTime());
            config.setLeakDetectionThreshold(tenant.getPoolConfiguration().getLeakDetectionThreshold());

        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "HikariDataSourceSupport - getting DB Config failed...", ex.fillInStackTrace());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return config;
    }
}
