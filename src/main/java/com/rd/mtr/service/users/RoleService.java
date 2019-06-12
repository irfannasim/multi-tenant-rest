package com.rd.mtr.service.users;

import com.rd.mtr.dao.users.RoleDAO;
import com.rd.mtr.model.users.Role;
import com.rd.mtr.request.users.CreateRoleRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
 * @Package   : com.rd.mtr.service
 * @FileName  : RoleService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class RoleService {

    private final Logger logger = LogManager.getLogger(RoleService.class);

    @Autowired
    private RoleDAO roleDAO;

    public List<Role> findAll(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleService - findAll method called");

        return roleDAO.findAll(tenantId);
    }

    public List<Role> findAllActive(String tenantId) throws SQLException {
        logger.info("TenantId:" + tenantId + " " + "RoleService - findAllActive method called");

        return roleDAO.findAllActive(tenantId);
    }

    public Role findById(String tenantId, long rId) throws SQLException {
        return roleDAO.findById(tenantId, rId);
    }

    public Role findByName(String tenantId, String rName) throws SQLException {
        return roleDAO.findByName(tenantId, rName);
    }

    public Role findByNameExculsive(String tenantId, String rName, long rId) throws SQLException {
        return roleDAO.findByNameExclusive(tenantId, rName, rId);
    }

    public Boolean saveRolePermissions(String tenantId, CreateRoleRequest createRequest) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleService - updateDefaultAdvance method called");
        createRequest.setUpdatedOn(new Date());
        createRequest.setCreatedOn(new Date());
        return roleDAO.saveRolePermissions(tenantId, createRequest);
    }

    public Boolean updateRolePermissions(String tenantId, CreateRoleRequest updateRequest) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleService - updateDefaultAdvance method called");
        updateRequest.setUpdatedOn(new Date());
        return roleDAO.updateRolePermissions(tenantId, updateRequest);
    }


    public Boolean deleteRolePermissions(String tenantId,long roleId) throws SQLException, IOException {
        logger.info("TenantId:" + tenantId + " " + "RoleService - updateDefaultAdvance method called");
        return roleDAO.deleteRolePermissions(tenantId, roleId);
    }
}
