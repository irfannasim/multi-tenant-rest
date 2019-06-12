package com.rd.mtr.dao.users;

import com.rd.mtr.configuration.JdbcEntityManager;
import com.rd.mtr.model.users.Permission;
import com.rd.mtr.model.users.Role;
import com.rd.mtr.request.users.CreateRolePermissionRequest;
import com.rd.mtr.request.users.CreateRoleRequest;
import com.rd.mtr.service.system.HikariDataSourceSupport;
import com.rd.mtr.util.BKCoreUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 10-Jan-2019
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
 * @Package   : com.rd.mtr.dao.users
 * @FileName  : RoleDAO
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public class RoleDAO {

    private final Logger logger = LogManager.getLogger(RoleDAO.class);

    @Autowired
    private HikariDataSourceSupport support;
    @Autowired
    private PermissionDAO permissionDAO;

    public List<Role> findAll(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - findAll method called");
        List<Role> roles;
        try (Connection conn = support.getConnection(tenantId)) {
            roles = JdbcEntityManager.getMultiple(
                    conn,
                    Role.class,
                    "SELECT r.id AS r_id, r.name AS r_name, r.description AS r_description, r.is_active AS r_is_active, " +
                            "r.created_on AS r_created_on, r.update_on AS r_updated_on " +
                            "FROM role r ");
        }
        if (!BKCoreUtil.isListEmpty(roles)) {
            for (Role role : roles) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, role.getId());
                role.setPermissions(permissions);
            }
        }
        return roles;
    }

    public List<Role> findAllActive(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - findAllActive method called");
        List<Role> roles;
        try (Connection conn = support.getConnection(tenantId)) {
            roles = JdbcEntityManager.getMultiple(
                    conn,
                    Role.class,
                    "SELECT r.id AS r_id, r.name AS r_name, r.description AS r_description, r.is_active AS r_is_active, " +
                            "r.created_on AS r_created_on, r.update_on AS r_updated_on " +
                            "FROM role r " +
                            "WHERE r.is_active = true ");
        }
        if (!BKCoreUtil.isListEmpty(roles)) {
            for (Role role : roles) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, role.getId());
                role.setPermissions(permissions);
            }
        }
        return roles;
    }

    public Role findById(String tenantId, long id) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - findById method called");
        Role role;
        try (Connection conn = support.getConnection(tenantId)) {
            role = JdbcEntityManager.getSingle(
                    conn,
                    Role.class,
                    "SELECT r.id AS r_id, r.name AS r_name, r.description AS r_description, r.is_active AS r_is_active, " +
                            "r.created_on AS r_created_on, r.update_on AS r_updated_on " +
                            "FROM role r " +
                            "WHERE r.id = ?",
                    id);
        }
        if (BKCoreUtil.isValidObject(role)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, role.getId());
            role.setPermissions(permissions);
        }
        return role;
    }

    public Role findByName(String tenantId, String name) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - findByName method called");
        Role role;
        try (Connection conn = support.getConnection(tenantId)) {
            role = JdbcEntityManager.getSingle(
                    conn,
                    Role.class,
                    "SELECT r.id AS r_id, r.name AS r_name, r.description AS r_description, r.is_active AS r_is_active, " +
                            "r.created_on AS r_created_on, r.update_on AS r_updated_on " +
                            "FROM role r " +
                            "WHERE r.name = ?",
                    name);
        }
        if (BKCoreUtil.isValidObject(role)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, role.getId());
            role.setPermissions(permissions);
        }
        return role;
    }

    public Role findByNameExclusive(String tenantId, String name, long rId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - findByNameExclusive method called");
        Role role;
        try (Connection conn = support.getConnection(tenantId)) {
            role = JdbcEntityManager.getSingle(
                    conn,
                    Role.class,
                    "SELECT r.id AS r_id, r.name AS r_name, r.description AS r_description, r.is_active AS r_is_active, " +
                            "r.created_on AS r_created_on, r.update_on AS r_updated_on " +
                            "FROM role r " +
                            "WHERE r.name = ? AND r.id != ?",
                    name,
                    rId);
        }
        if (BKCoreUtil.isValidObject(role)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, role.getId());
            role.setPermissions(permissions);
        }
        return role;
    }

    public Boolean saveRolePermissions(String tenantId, CreateRoleRequest createRequest) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - saveRolePermissions method called");

        boolean transactionSuccess = false;
        Connection connection = null;
        long roleId = 0;
        long permissionId = 0;

        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);

            logger.info("TenantId:" + tenantId + " " + "RoleDAO - insertion started in Role");

            roleId = JdbcEntityManager.executeInsert(connection,
                    "INSERT INTO role (name, description, is_active, update_on, created_on, can_delete )" +
                            "VALUES (?, ?, ?, ?, ?, ?);",
                    Long.class,
                    createRequest.getrName(),
                    createRequest.getrDescription(),
                    createRequest.getrIsActive(),
                    createRequest.getUpdatedOn(),
                    createRequest.getCreatedOn(),
                    true
            );

            logger.info("TenantId:" + tenantId + " " + "RoleDAO - insertion started in permission...");

            for (CreateRolePermissionRequest rolePermissionReq : createRequest.getPermissions()) {
                permissionId = JdbcEntityManager.executeInsert(connection,
                        "INSERT INTO permission (created_on, updated_on, is_active,  description, name, is_default ) " +
                                "VALUES (?, ?, ?, ?, ?, ?);",
                        Long.class,
                        new Date(),
                        new Date(),
                        rolePermissionReq.getpIsActive(),
                        rolePermissionReq.getpDescription(),
                        rolePermissionReq.getpName(),
                        rolePermissionReq.getpIsDefault()
                );

                JdbcEntityManager.executeInsert(connection,
                        "INSERT INTO role_permission (role_id, permission_id) " +
                                "VALUES (?, ? );",
                        Long.class,
                        roleId,
                        permissionId
                );
            }
            logger.info("TenantId:" + tenantId + " " + "RoleDAO - insertion successfully ended in permission and role permission Request...");
            transactionSuccess = true;
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "RoleDAO - create Role and Permission transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - create Role and Permission transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - create Role and Permission is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public Boolean updateRolePermissions(String tenantId, CreateRoleRequest updateRequest) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - updateRolePermissions method called");

        boolean transactionSuccess = false;
        Connection connection = null;
        long roleId = updateRequest.getrId();
        long permissionId = 0;

        boolean isRoleUpdated = false;
        boolean isRolePermissionDeleted = false;

        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);

            logger.info("TenantId:" + tenantId + " " + "RoleDAO - updation started in Role");

            List<Permission> permissions = permissionDAO.findAllByRoleId(tenantId, updateRequest.getrId());
            for (Permission per : permissions) {
                long perId = per.getId();
                isRolePermissionDeleted = JdbcEntityManager.executeUpdate(connection, "DELETE FROM role_permission WHERE role_id = ?  AND permission_id = ? ",
                        updateRequest.getrId(),
                        perId);

                if (isRolePermissionDeleted) {
                    JdbcEntityManager.executeUpdate(connection, "DELETE FROM permission WHERE id = ? ", perId);
                }
            }


            isRoleUpdated = JdbcEntityManager.executeUpdate(connection,
                    "UPDATE role SET name = ?, description = ?, is_active = ?, update_on = ? " +
                            "WHERE id = ?",
                    updateRequest.getrName(),
                    updateRequest.getrDescription(),
                    updateRequest.getrIsActive(),
                    updateRequest.getUpdatedOn(),
                    updateRequest.getrId()
            );

            if (isRoleUpdated) {
                logger.info("TenantId:" + tenantId + " " + "RoleDAO - insertion started in permission...");

                for (CreateRolePermissionRequest rolePermissionReq : updateRequest.getPermissions()) {
                    permissionId = JdbcEntityManager.executeInsert(connection,
                            "INSERT INTO permission (created_on, updated_on, is_active,  description, name, is_default ) " +
                                    "VALUES (?, ?, ?, ?, ?, ? );",
                            Long.class,
                            new Date(),
                            new Date(),
                            rolePermissionReq.getpIsActive(),
                            rolePermissionReq.getpDescription(),
                            rolePermissionReq.getpName(),
                            rolePermissionReq.getpIsDefault()
                    );

                    JdbcEntityManager.executeInsert(connection,
                            "INSERT INTO role_permission (role_id, permission_id) " +
                                    "VALUES (?, ? );",
                            Long.class,
                            roleId,
                            permissionId
                    );
                }
                logger.info("TenantId:" + tenantId + " " + "RoleDAO - updation successfully ended in permission and role permission Request...");
                transactionSuccess = true;
            }

        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "RoleDAO - update Role and Permission transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - update Role and Permission transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - update Role and Permission is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public Boolean deleteRolePermissions(String tenantId, long roleId) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleDAO - deleteRolePermissions method called");

        boolean transactionSuccess = false;
        Connection connection = null;
        boolean isRolePermissionDeleted = false;

        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);

            logger.info("TenantId:" + tenantId + " " + "RoleDAO - Delete started in Role");

            List<Permission> permissions = permissionDAO.findAllByRoleId(tenantId, roleId);
            for (Permission per : permissions) {
                isRolePermissionDeleted = false;
                long perId = per.getId();
                isRolePermissionDeleted = JdbcEntityManager.executeUpdate(connection, "DELETE FROM role_permission WHERE role_id = ?  AND permission_id = ? ",
                        roleId,
                        perId);

                if (isRolePermissionDeleted) {
                    JdbcEntityManager.executeUpdate(connection, "DELETE FROM permission WHERE id = ? ", perId);
                }
            }

            if (isRolePermissionDeleted) {
                logger.info("TenantId:" + tenantId + " " + "RoleDAO - Deletion started in Role...");
                JdbcEntityManager.executeUpdate(connection, "DELETE FROM role WHERE  id = ? ", roleId);

                logger.info("TenantId:" + tenantId + " " + "RoleDAO - Deletion successfully ended in role, permission and role permission Request...");
                transactionSuccess = true;
            }

        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "RoleDAO - delete Role and Permission transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - delete Role and Permission transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "RoleDAO - delete Role and Permission is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

}
