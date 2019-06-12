package com.rd.mtr.dao.users;

import com.rd.mtr.configuration.JdbcEntityManager;
import com.rd.mtr.model.users.Permission;
import com.rd.mtr.service.system.HikariDataSourceSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
 * @author    : irfan
 * @Date      : 01-Jan-2019
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
 * @Package   : com.rd.mtr.dao
 * @FileName  : PermissionDAO
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public class PermissionDAO {

    private final Logger logger = LogManager.getLogger(PermissionDAO.class);

    @Autowired
    private HikariDataSourceSupport support;


    public List<Permission> findAll(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "PermissionDAO - findAll method called");

        try (Connection conn = support.getConnection(tenantId)) {
            return JdbcEntityManager.getMultiple(
                    conn,
                    Permission.class,
                    "SELECT p.id AS p_id, p.name AS p_name, p.description AS p_description, p.is_active AS p_is_active, p.is_default AS p_is_default, " +
                            "p.created_on AS p_created_on, p.updated_on AS p_updated_on " +
                            "FROM permission p ");
        }
    }

    public List<Permission> findAllDefault(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "PermissionDAO - findAllDefault method called");

        try (Connection conn = support.getConnection(tenantId)) {
            return JdbcEntityManager.getMultiple(
                    conn,
                    Permission.class,
                    "SELECT p.id AS p_id, p.name AS p_name, p.description AS p_description, p.is_active AS p_is_active, p.is_default AS p_is_default, " +
                            "p.created_on AS p_created_on, p.updated_on AS p_updated_on " +
                            "FROM permission p " +
                            "WHERE p.p_is_default = true ");
        }
    }

    public List<Permission> findAllByRoleId(String tenantId, long roleId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "PermissionDAO - findAllDefault method called");

        try (Connection conn = support.getConnection(tenantId)) {
            return JdbcEntityManager.getMultiple(
                    conn,
                    Permission.class,
                    "SELECT p.id AS p_id, p.name AS p_name, p.description AS p_description, p.is_active AS p_is_active, p.is_default AS p_is_default, " +
                            "p.created_on AS p_created_on, p.updated_on AS p_updated_on " +
                            "FROM permission p JOIN role_permission rp ON p.id = rp.permission_id " +
                            "JOIN role r ON rp.role_id = r.id " +
                            "WHERE r.id = ?",
                    roleId);
        }
    }
}
