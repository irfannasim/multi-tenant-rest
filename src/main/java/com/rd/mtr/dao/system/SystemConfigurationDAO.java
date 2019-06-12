package com.rd.mtr.dao.system;

import com.rd.mtr.model.system.SystemConfiguration;
import com.rd.mtr.service.system.HikariDataSourceSupport;
import com.rd.mtr.configuration.JdbcEntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class SystemConfigurationDAO {

    private final Logger logger = LogManager.getLogger(SystemConfigurationDAO.class);

    @Autowired
    private HikariDataSourceSupport support;

    public SystemConfiguration findOne(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "SystemConfigurationDAO - findAll method called");

        try (Connection conn = support.getConnection(tenantId)) {
            return JdbcEntityManager.getSingle(
                    conn,
                    SystemConfiguration.class,
                    "SELECT id, client_id, client_secret, auth_server_scheme FROM properties ");
        }
    }
}
