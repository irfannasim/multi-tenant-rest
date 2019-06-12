package com.rd.mtr.service.users;

import com.rd.mtr.dao.users.RoleDAO;
import com.rd.mtr.dao.users.UserDAO;
import com.rd.mtr.model.users.Role;
import com.rd.mtr.model.users.User;
import com.rd.mtr.request.users.CreateUserRequest;
import com.rd.mtr.request.users.UpdateUserRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class TenantUserService {

    private final Logger logger = LogManager.getLogger(TenantUserService.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    public User findUserById(Long userId, String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findUserById method called");
        User user = userDAO.findByTenantIdAndUserId(tenantId, userId);
        return user;
    }

    public User findByTenantIdAndUsernameOrEmail(String tenantId, String username, String email) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findByTenantIdAndUsernameOrEmail method called");

        return userDAO.findByTenantIdAndUsernameOrEmail(tenantId, username, email);
    }

    public User findByTenantIdAndUsername(String tenantId, String username) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findByTenantIdAndUsername method called");

        return userDAO.findByTenantIdAndUsername(tenantId, username);
    }

    public User findByTenantIdAndEmail(String tenantId, String email) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findByTenantIdAndEmail method called");
        User user = userDAO.findByTenantIdAndEmail(tenantId, email);
        return user;
    }

    public User findByTenantIdAndEmailExceptCurrent(String tenantId, String email, long userId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findByTenantIdAndEmailExceptCurrent method called");

        return userDAO.findByTenantIdAndEmailExceptCurrent(tenantId, email, userId);
    }

    public List<User> findAllUsersByTenantId(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - findAllUsersByTenantId method called");

        return userDAO.findAll(tenantId);
    }

    public boolean createUser(String tenantId, CreateUserRequest createUserRequest) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - createUser method called");
        String username = createUserRequest.getEmail().split("@")[0];
        createUserRequest.setUsername(username);
        createUserRequest.setLanguage("en");
        Role role = roleDAO.findById(tenantId, createUserRequest.getRoleId());

        boolean isUserCreated = userDAO.createUser(tenantId, createUserRequest, role);
        return isUserCreated;
    }

    public boolean updateUser(String tenantId, UpdateUserRequest updateUserRequest) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "TenantUserService - UpdateUserRequest method called");

        String username = updateUserRequest.getEmail().split("@")[0];
        updateUserRequest.setUsername(username);
        return userDAO.updateUser(tenantId, updateUserRequest);
    }

}
