package com.rd.mtr.controller.users;

import com.rd.mtr.enums.ResponseEnum;
import com.rd.mtr.model.users.User;
import com.rd.mtr.request.users.CreateUserRequest;
import com.rd.mtr.request.users.UpdateUserRequest;
import com.rd.mtr.response.BKAPIResponse;
import com.rd.mtr.service.users.TenantUserService;
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
import java.security.Principal;
import java.util.List;
import java.util.Locale;

/*
 * @author    : Irfan Nasim
 * @Date      : 20-Nov-2018
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
 * @FileName  : UserAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/user")
public class UserAPI {

    private final Logger logger = LogManager.getLogger(UserAPI.class);

    @Autowired
    private TenantUserService userService;
    @Autowired
    private MessageSource messageSource;

    @ApiOperation(httpMethod = "GET", value = "User Logging In",
            notes = "This method will return logged in User",
            produces = "application/json", nickname = "Logging In",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in User fetched", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request, Principal principal) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "LoggedIn User API - getLoggedInUser API initiated.");
        String name = principal.getName();

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.USER_LOGGEDIN_FETCHED_FAILED.getValue());
        response.setResponseData(null);

        try {
            if (BKCoreUtil.isValidObject(name)) {
                logger.info("TenantId:" + tenantId + " " + "LoggedIn User API - fetching user from DB.");
                User user = userService.findByTenantIdAndUsername(tenantId, name);

                if (BKCoreUtil.isValidObject(user)) {
                    logger.info("TenantId:" + tenantId + " " + "LoggedIn User API - user successfully fetched...");
                    response.setResponseMessage(messageSource.getMessage("user.fetched.success", null, new Locale(locale)));
                    response.setResponseCode(ResponseEnum.USER_LOGGEDIN_FETCHED_SUCCESS.getValue());
                    response.setResponseData(user);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "LoggedIn User API - getLoggedInUser failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "User Logging In",
            notes = "This method will return logged in User",
            produces = "application/json", nickname = "Logging In",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in User fetched", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(HttpServletRequest request,
                                         @PathVariable("userId") long userId) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "getUserById - getLoggedInUser API initiated.");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
        response.setResponseData(null);

        try {
            if (userId > 0) {
                logger.info("TenantId:" + tenantId + " " + "getUserById API - fetching user from DB.");
                User user = userService.findUserById(userId, tenantId);

                if (BKCoreUtil.isValidObject(user)) {
                    logger.info("TenantId:" + tenantId + " " + "getUserById API - user successfully fetched...");
                    response.setResponseMessage(messageSource.getMessage("user.fetched.success", null, new Locale(locale)));
                    response.setResponseCode(ResponseEnum.FOUND.getValue());
                    response.setResponseData(user);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "getUserById API - getUserById failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "All Users",
            notes = "This method will return All Users",
            produces = "application/json", nickname = "Get All Users ",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Users fetched successfully", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "getAllUsers API..");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.not.found", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
        response.setResponseData(null);

        try {
            List<User> users = userService.findAllUsersByTenantId(tenantId);

            response.setResponseMessage(messageSource.getMessage("user.fetched.success", null, new Locale(locale)));
            response.setResponseCode(ResponseEnum.FOUND.getValue());
            response.setResponseData(users);
            logger.info("TenantId:" + tenantId + " " + "getAllUser Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "get all User failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Create User",
            notes = "This method will Create User",
            produces = "application/json", nickname = "Create User",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully created.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(HttpServletRequest request,
                                        @RequestBody CreateUserRequest createRequest) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "createUser API called..");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.create.error", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.USER_CREATE_FAILED.getValue());
        response.setResponseData(null);

        try {
            if (BKCoreUtil.isNull(tenantId) || BKCoreUtil.isNull(createRequest.getEmail())) {
                logger.info("TenantId:" + tenantId + " " + "createUser API - insufficient Params...");

                response.setResponseMessage(messageSource.getMessage("insufficient.parameter", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseData(null);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            logger.info("TenantId:" + tenantId + " " + "createUser API - Fetching user from DB...");
            User user = userService.findByTenantIdAndEmail(tenantId, createRequest.getEmail());
            if (BKCoreUtil.isValidObject(user)) {
                logger.info("TenantId:" + tenantId + " " + "createUser API - USer already exist with this email...");
                response.setResponseMessage(messageSource.getMessage("user.already.exist", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_ALREADY_EXIST.getValue());
                response.setResponseData(null);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            boolean isUserCreated = userService.createUser(tenantId, createRequest);
            if (isUserCreated) {
                logger.info("TenantId:" + tenantId + " " + "createUser API - User successfully created...");
                response.setResponseMessage(messageSource.getMessage("user.create.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_CREATE_SUCCESS.getValue());
                response.setResponseData(null);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "createUser API - create user failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update User",
            notes = "This method will Update User",
            produces = "application/json", nickname = "Update User",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated.", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody UpdateUserRequest updateRequest) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "updateUser API called..");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.update.error", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.USER_UPDATE_FAILED.getValue());
        response.setResponseData(null);

        try {
            if (BKCoreUtil.isNull(tenantId) || BKCoreUtil.isNull(updateRequest.getEmail())) {
                response.setResponseMessage(messageSource.getMessage("insufficient.parameter", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseData(null);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            logger.info("TenantId:" + tenantId + " " + "updateUser API - Fetching user from DB...");
            User user = userService.findByTenantIdAndEmailExceptCurrent(tenantId, updateRequest.getEmail(), updateRequest.getUserId());
            if (BKCoreUtil.isValidObject(user)) {
                logger.info("TenantId:" + tenantId + " " + "updateUser API - User already exist with this email...");
                response.setResponseMessage(messageSource.getMessage("user.already.exist", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_ALREADY_EXIST.getValue());
                response.setResponseData(null);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            boolean isUserUpdate = userService.updateUser(tenantId, updateRequest);
            if (isUserUpdate) {
                logger.info("TenantId:" + tenantId + " " + "updateUser API - User successfully updated...");
                response.setResponseMessage(messageSource.getMessage("user.update.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_UPDATE_SUCCESS.getValue());
                response.setResponseData(null);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "updateUser API - update user failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

