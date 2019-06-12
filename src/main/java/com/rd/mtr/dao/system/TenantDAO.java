package com.rd.mtr.dao.system;

import com.rd.mtr.configuration.JdbcEntityManager;
import com.rd.mtr.model.system.Tenant;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

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
 * @FileName  : TenantDAO
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public class TenantDAO {

    private final Logger logger = LogManager.getLogger(TenantDAO.class);

    @Autowired
    private HikariDataSource tenantDataSource;

    public Tenant findOneByTenantId(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantDAO - findOneByTenantId method called");

        try (Connection conn = tenantDataSource.getConnection()) {
            return JdbcEntityManager.getSingle(
                    conn,
                    Tenant.class,
                    "SELECT t.id, t.db_name, t.username, t.password, t.db_username, t.db_password, name, t.db_server_url, t.db_server_port, t.is_ssl_enabled," +
                            "pc.id AS pc_id, pc.pool_name AS pc_pool_name, pc.minimum_idle AS pc_min_idle, pc.idle_timeout AS pc_idle_timeout, pc.connection_timeout AS pc_conn_timeout," +
                            "pc.max_life_time AS pc_life_time, pc.leak_detection_threshold AS pc_leak_detection_threshold, pc.max_pool_size AS pc_max_pool_size " +
                            "FROM tenants t JOIN tenant_hikari_pool_configuration pc ON t.pool_configuration_id = pc.id " +
                            "WHERE name = ? ",
                    tenantId);
        }
    }
}
