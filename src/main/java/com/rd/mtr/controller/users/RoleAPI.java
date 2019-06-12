package com.rd.mtr.controller.users;

import com.rd.mtr.enums.ResponseEnum;
import com.rd.mtr.model.users.Role;
import com.rd.mtr.request.users.CreateRoleRequest;
import com.rd.mtr.response.BKAPIResponse;
import com.rd.mtr.service.users.RoleService;
import com.rd.mtr.util.BKConstants;
import com.rd.mtr.util.BKCoreUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/*
 * @author    : Naeem Saeed
 * @Date      : 08-Jan-2019
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
 * @Package   : com.rd.mtr.controller
 * @FileName  : RoleAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/role")
public class RoleAPI {

    private final Logger logger = LogManager.getLogger(RoleAPI.class);

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RoleService roleService;

    @ApiOperation(httpMethod = "GET", value = "All Roles",
            notes = "This method will return All Roles",
            produces = "application/json", nickname = "Get All Roles ",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Roles fetched successfully", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRoles(HttpServletRequest request) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "getAllRoles API..");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
        response.setResponseData(null);

        try {
            List<Role> roles = roleService.findAll(tenantId);

            response.setResponseMessage(messageSource.getMessage("role.fetched.success", null, new Locale(locale)));
            response.setResponseCode(ResponseEnum.ROLE_ALL_FETCHED_SUCCESS.getValue());
            response.setResponseData(roles);
            logger.info("TenantId:" + tenantId + " " + "getAllRoles Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "get all Roles failed.", ex.fillInStackTrace());
            response.setResponseData(null);
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Active Roles",
            notes = "This method will return All Active Roles",
            produces = "application/json", nickname = "Get All Active Roles ",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Active Roles fetched successfully", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/all-active", method = RequestMethod.GET)
    public ResponseEntity<?> getAllActiveRoles(HttpServletRequest request) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "getAllActiveRoles API..");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
        response.setResponseData(null);

        try {
            List<Role> roles = roleService.findAllActive(tenantId);

            response.setResponseMessage(messageSource.getMessage("role.fetched.success", null, new Locale(locale)));
            response.setResponseCode(ResponseEnum.ROLE_ALL_ACTIVE_FETCHED_SUCCESS.getValue());
            response.setResponseData(roles);
            logger.info("TenantId:" + tenantId + " " + "getAllActiveRoles Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "get all active Roles failed.", ex.fillInStackTrace());
            response.setResponseData(null);
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Get Role by ID",
            notes = "This method will return  Role by ID",
            produces = "application/json", nickname = "Get  Role by ID",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Role by ID fetched successfully.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/get/{rId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoleById(HttpServletRequest request, @PathVariable("rId") long venId) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "getRoleById API - initiated.");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
        response.setResponseData(null);
        try
        {
            if (BKCoreUtil.isNull(tenantId)) {
                response.setResponseMessage(messageSource.getMessage("insufficient.parameter", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseData(null);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            logger.info("TenantId:" + tenantId + " " + "getRoleById API - fetching Role by ID from DB.");
            Role role = roleService.findById(tenantId, venId);
            if (BKCoreUtil.isValidObject(role)) {
                logger.info("TenantId:" + tenantId + " " + "getRoleById API - role by ID successfully fetched...");
                response.setResponseMessage(messageSource.getMessage("vendor.fetched.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ROLE_FETCHED_SUCCESS.getValue());
                response.setResponseData(role);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "getRoleById API - fetching failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Create Role and their Permission",
            notes = "This method will create Role and their Permission",
            produces = "application/json", nickname = "Create Role and their Permission",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Role and their Permission Method created successfully.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createRole(HttpServletRequest request,
                                                 @RequestBody @Valid CreateRoleRequest createRequest) {

        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "create Role API - initiated.");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.create.error", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.ROLE_CREATE_FAILED.getValue());
        response.setResponseData(null);
        try {
            if (BKCoreUtil.isNull(tenantId)) {
                logger.info("TenantId:" + tenantId + " " + "createRole API - Insufficient parameters.");
                response.setResponseMessage(messageSource.getMessage("insufficient.parameter", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Role role = roleService.findByName(tenantId, createRequest.getrName());
            if (BKCoreUtil.isValidObject(role)) {
                logger.info("TenantId:" + tenantId + " " + "createRole API - Role already exist with this name...");
                response.setResponseMessage(messageSource.getMessage("role.already.exist", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ALREADY_EXIST.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Boolean isSavedSuccessfully = roleService.saveRolePermissions(tenantId, createRequest);
            if(isSavedSuccessfully){
                logger.info("TenantId:" + tenantId + " " + "createRole API - Role successfully created...");
                response.setResponseMessage(messageSource.getMessage("role.create.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ROLE_CREATE_SUCCESS.getValue());
                response.setResponseData(isSavedSuccessfully);
            }

        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "createRole API - creating failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "PUT", value = "Update Role and their Permission",
            notes = "This method will update Role and their Permission",
            produces = "application/json", nickname = "Update Role and their Permission",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Role and their Permission Method updated successfully.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRole(HttpServletRequest request,
                                        @RequestBody @Valid CreateRoleRequest updateRequest) {

        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "update Role API - initiated.");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.update.error", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.ROLE_UPDATE_FAILED.getValue());
        response.setResponseData(null);
        try {
            if (BKCoreUtil.isNull(tenantId)) {
                logger.info("TenantId:" + tenantId + " " + "updateRole API - Insufficient parameters.");
                response.setResponseMessage(messageSource.getMessage("insufficient.parameter", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Role role = roleService.findById(tenantId, updateRequest.getrId());
            if (role == null) {
                logger.info("TenantId:" + tenantId + " " + "updateRole API - Role not found...");
                response.setResponseMessage(messageSource.getMessage("role.not.found", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Role role1 = roleService.findByNameExculsive(tenantId, updateRequest.getrName(), role.getId());
            if (BKCoreUtil.isValidObject(role1)) {
                logger.info("TenantId:" + tenantId + " " + "updateRole API - Role already exist with this name...");
                response.setResponseMessage(messageSource.getMessage("role.already.exist", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ALREADY_EXIST.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Boolean isSavedSuccessfully = roleService.updateRolePermissions(tenantId, updateRequest);
            if(isSavedSuccessfully){
                logger.info("TenantId:" + tenantId + " " + "updateRole API - Role successfully updated...");
                response.setResponseMessage(messageSource.getMessage("role.update.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ROLE_UPDATE_SUCCESS.getValue());
                response.setResponseData(isSavedSuccessfully);
            }

        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "updateRole API - updation failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Role and Permission",
            notes = "This method will delete Role and Permission",
            produces = "application/json", nickname = "Delete Role and Permission",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Role and Permission deleted successfully.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRole(HttpServletRequest request,
                                            @PathVariable("id") long id) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "deleteRole API - initiated.");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("role.delete.error", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.ROLE_DELETE_FAILED.getValue());
        response.setResponseData(null);
        try
        {
            Role role = roleService.findById(tenantId, id);
            if (role == null) {
                logger.info("TenantId:" + tenantId + " " + "deleteRole API - Role not found...");
                response.setResponseMessage(messageSource.getMessage("role.not.found", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            logger.info("TenantId:" + tenantId + " " + "deleteRole API - deleting Role...");
            Boolean isDeleted = roleService.deleteRolePermissions(tenantId, role.getId());
            if (isDeleted) {
                response.setResponseMessage(messageSource.getMessage("role.delete.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.ROLE_DELETE_SUCCESS.getValue());
                List<Role> roles = roleService.findAllActive(tenantId);
                response.setResponseData(roles);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "deleteRole API - deleting failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
