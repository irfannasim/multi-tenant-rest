package com.rd.mtr.dao.users;

import com.rd.mtr.configuration.JdbcEntityManager;
import com.rd.mtr.model.users.Permission;
import com.rd.mtr.model.users.Role;
import com.rd.mtr.model.users.User;
import com.rd.mtr.request.users.CreateUserRequest;
import com.rd.mtr.request.users.UpdateUserProfileRequest;
import com.rd.mtr.request.users.UpdateUserRequest;
import com.rd.mtr.service.system.HikariDataSourceSupport;
import com.rd.mtr.util.BKCoreUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
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
public class UserDAO {

    private final Logger logger = LogManager.getLogger(UserDAO.class);

    @Autowired
    private HikariDataSourceSupport support;
    @Autowired
    private PermissionDAO permissionDAO;

    public List<User> findAll(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findAll method called");

        List<User> users;
        try (Connection conn = support.getConnection(tenantId)) {
            users = JdbcEntityManager.getMultiple(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id ");
        }
        for (User user : users) {
            if (BKCoreUtil.isValidObject(user)) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
                user.getRole().setPermissions(permissions);
            }
        }
        return users;
    }

    public User findByTenantIdAndEmail(String tenantId, String email) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndEmail method called");

        User user;
        try (Connection conn = support.getConnection(tenantId)) {
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.email = ? ",
                    email);
        }
        if (BKCoreUtil.isValidObject(user)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
            user.getRole().setPermissions(permissions);
        }
        return user;
    }

    public User findByTenantIdAndEmailExceptCurrent(String tenantId, String email, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndEmailExceptCurrent method called");

        User user;
        try (Connection conn = support.getConnection(tenantId)) {
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.email = ? AND u.id != ? ",
                    email,
                    userId);
        }
        if (BKCoreUtil.isValidObject(user)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
            user.getRole().setPermissions(permissions);
        }
        return user;
    }


    public User findByTenantIdAndUsername(String tenantId, String username) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndUsername method called");

        User user;
        try (Connection conn = support.getConnection(tenantId)) {
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.username = ? ",
                    username);
        }
        if (BKCoreUtil.isValidObject(user)) {
            List<Permission> permissions;
            permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
            user.getRole().setPermissions(permissions);
        }
        return user;
    }

    public User findByTenantIdAndUsernameOrEmail(String tenantId, String username, String email) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndUsernameOrEmail method called");

        User user;
        Connection conn = null;
        try {
            conn = support.getConnection(tenantId);
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.username = ? OR email = ? ",
                    username,
                    email);

            if (BKCoreUtil.isValidObject(user)) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
                user.getRole().setPermissions(permissions);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public User findByTenantIdAndUsernameOrEmailExceptCurrent(String tenantId, String username, String email, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndUsernameOrEmailExceptCurrent method called");

        User user;
        try (Connection conn = support.getConnection(tenantId)) {
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.username = ? OR email = ? " +
                            "AND u.id != ?",
                    username,
                    email,
                    userId);

            if (BKCoreUtil.isValidObject(user)) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
                user.getRole().setPermissions(permissions);
            }
        }
        return user;
    }

    public User findByTenantIdAndUserId(String tenantId, Long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - findByTenantIdAndUserId method called");

        User user;
        try (Connection conn = support.getConnection(tenantId)) {
            user = JdbcEntityManager.getSingle(
                    conn,
                    User.class,
                    "SELECT u.id AS u_id, u.username AS u_username, u.email AS u_email, u.user_type AS u_user_type, u.password AS u_password, u.is_active AS u_is_active, " +
                            "u.created_on AS u_created_on, u.updated_on AS u_updated_on, up.id AS up_id, up.first_name AS up_first_name, up.last_name AS up_last_name, " +
                            "up.dob AS up_dob, up.language AS up_language, up.address AS up_address, up.mobile AS up_mobile, r.id AS r_id, r.name AS r_name, " +
                            " up.profile_img AS up_profile_img, r.description AS r_description, r.is_active AS r_is_active, r.created_on AS r_created_on, " +
                            "r.update_on AS r_updated_on, u.role_id AS u_role_id " +
                            "FROM user u LEFT JOIN user_profile up ON u.profile_id = up.id " +
                            "JOIN role r ON u.role_id = r.id " +
                            "WHERE u.id = ? ",
                    userId);

            if (BKCoreUtil.isValidObject(user)) {
                List<Permission> permissions;
                permissions = permissionDAO.findAllByRoleId(tenantId, user.getRoleId());
                user.getRole().setPermissions(permissions);
            }
        }
        return user;
    }

    public Boolean updateUserPassword(String tenantId, String password, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - updateUserPassword method called");

        boolean transactionSuccess = false, isUserUpdated;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password update started in user...");

            isUserUpdated = JdbcEntityManager.executeUpdate(connection,
                    "UPDATE user SET password = ? WHERE id = ? ",
                    password,
                    userId
            );
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password update ended in user...");
            if (isUserUpdated) {
                transactionSuccess = true;
            }
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password update transaction is successful...");
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - password update transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - password update transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - password update is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public Boolean createUserPassword(String tenantId, String password, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - createUserPassword method called");

        boolean transactionSuccess = false, isUserUpdated;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password creation started in user...");

            isUserUpdated = JdbcEntityManager.executeUpdate(connection,
                    "UPDATE user SET password = ?, is_active = true WHERE id = ? ",
                    password,
                    userId
            );
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password creation ended in user...");
            if (isUserUpdated) {
                transactionSuccess = true;
            }
            logger.info("TenantId:" + tenantId + " " + "UserDAO - password creation transaction is successful...");
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - password creation transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - password creation transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - password creation is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public boolean createUser(String tenantId, CreateUserRequest createUserRequest, Role role) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - createUser method called");

        boolean transactionSuccess = false;
        long profileId, userId;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);
            logger.info("TenantId:" + tenantId + " " + "UserDAO - Insertion started in profile...");

            profileId = JdbcEntityManager.executeInsert(connection,
                    "INSERT INTO user_profile (first_name, language) VALUES (?, ?);",
                    Long.class,
                    createUserRequest.getName(),
                    createUserRequest.getLanguage()
            );
            logger.info("TenantId:" + tenantId + " " + "UserDAO - insertion ended in profile...");
            if (profileId > 0) {
                logger.info("TenantId:" + tenantId + " " + "UserDAO - insertion started in user...");
                userId = JdbcEntityManager.executeInsert(connection,
                        "INSERT INTO user (username, email, user_type, profile_id, role_id, is_active, created_on) " +
                                "VALUES (?, ?, ?, ?, ?, ?, NOW());",
                        Long.class,
                        createUserRequest.getUsername(),
                        createUserRequest.getEmail(),
                        role.getName(),
                        profileId,
                        createUserRequest.getRoleId(),
                        false);
                logger.info("TenantId:" + tenantId + " " + "UserDAO - insertion ended in user...");

                if (userId > 0) {
                    transactionSuccess = true;
                }
            }
            logger.info("TenantId:" + tenantId + " " + "UserDAO - create user transaction is successful...");
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - create user transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - create user transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - create user is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public boolean updateUser(String tenantId, UpdateUserRequest updateUserRequest) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - updateUser method called");

        boolean transactionSuccess = false, isProfileUpdate, isUserUpdated;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);
            logger.info("TenantId:" + tenantId + " " + "UserDAO - updation started in profile...");

            isProfileUpdate = JdbcEntityManager.executeUpdate(connection,
                    "UPDATE user_profile SET first_name = ?, profile_img = ? WHERE id = ?",
                    updateUserRequest.getName(),
                    updateUserRequest.getProfileImg(),
                    updateUserRequest.getProfileId());

            logger.info("TenantId:" + tenantId + " " + "UserDAO - insertion ended in profile...");
            if (isProfileUpdate) {
                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation started in user...");
                isUserUpdated = JdbcEntityManager.executeUpdate(connection,
                        "UPDATE user SET username = ?, email = ?, role_id = ?, updated_on = NOW() " +
                                "WHERE id = ? ",
                        updateUserRequest.getUsername(),
                        updateUserRequest.getEmail(),
                        updateUserRequest.getRoleId(),
                        updateUserRequest.getUserId());
                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation ended in user...");

                if (isUserUpdated) {
                    transactionSuccess = true;
                }
            }
            logger.info("TenantId:" + tenantId + " " + "UserDAO - update user transaction is successful...");
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - update user transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - update user transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - update user is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public boolean updateUserActivateDeactivate(String tenantId, boolean isActivate, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - updateUserActivateDeactivate method called");

        boolean transactionSuccess = false, isVenUpdated;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);
            logger.info("TenantId:" + tenantId + " " + "UserDAO - activation / de-activation started in user...");

            isVenUpdated = JdbcEntityManager.executeUpdate(connection,
                    "UPDATE user SET is_active = ? WHERE id = ? ",
                    isActivate,
                    userId
            );
            logger.info("TenantId:" + tenantId + " " + "UserDAO - updation ended in user...");
            if (isVenUpdated) {
                transactionSuccess = true;
            }
            logger.info("TenantId:" + tenantId + " " + "UserDAO - activation / de-activation user transaction is successful...");
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - activation / de-activation user transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - activation / de-activation user transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - activation / de-activation user transaction is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }

    public boolean updateUserProfile(String tenantId, UpdateUserProfileRequest updateRequest) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "UserDAO - updateUserProfile method called");

        boolean transactionSuccess = false;
        Connection connection = null;
        try {
            connection = support.getConnection(tenantId);
            connection.setAutoCommit(false);

            if (updateRequest.getInfoType().equalsIgnoreCase("user-info")) {
                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation started in user...");

                JdbcEntityManager.executeUpdate(connection,
                        "UPDATE user SET username = ?, email = ? WHERE id = ?",
                        updateRequest.getUsername(),
                        updateRequest.getEmail(),
                        updateRequest.getUserId());

                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation ended in user...");
                transactionSuccess = true;
            } else {
                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation started in user_profile...");

                JdbcEntityManager.executeUpdate(connection,
                        "UPDATE user_profile SET first_name = ?, last_name = ?, dob = ?, language = ?, address = ?, mobile = ? " +
                                "WHERE id = ?",
                        updateRequest.getFirstName(),
                        updateRequest.getLastName(),
                        updateRequest.getDob(),
                        updateRequest.getLanguage(),
                        updateRequest.getAddress(),
                        updateRequest.getMobile(),
                        updateRequest.getProfileId());

                logger.info("TenantId:" + tenantId + " " + "UserDAO - updation ended in user_profile...");
                transactionSuccess = true;
            }
        } catch (Exception ex) {
            transactionSuccess = false;
            logger.error("TenantId:" + tenantId + " " + "UserDAO - update user profile transaction is failed...", ex.fillInStackTrace());
        } finally {
            if (connection != null) {
                if (transactionSuccess) {
                    connection.commit();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - update profile transaction committed successfully...");
                } else {
                    connection.rollback();
                    logger.info("TenantId:" + tenantId + " " + "UserDAO - update profile is being rolled back successfully...");
                }
                connection.close();
            }
        }
        return transactionSuccess;
    }
}
