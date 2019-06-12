package com.rd.mtr.controller.auth;

import com.rd.mtr.enums.ResponseEnum;
import com.rd.mtr.model.users.User;
import com.rd.mtr.request.users.UserLoginRequest;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/*
 * @author    : Irfan Nasim
 * @Date      : 22-Nov-2018
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
 * @FileName  : AuthenticationAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationAPI {

    private final Logger logger = LogManager.getLogger(AuthenticationAPI.class);
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private TenantUserService userService;
    @Autowired
    private MessageSource messageSource;

    @ApiOperation(httpMethod = "POST", value = "Admin Logged In",
            notes = "This method will Log in the User",
            produces = "application/json", nickname = "Logging In ",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Logged in successfully", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getLoggedIn(HttpServletRequest request,
                                         @RequestBody @Valid UserLoginRequest loginRequest) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);
        logger.info("TenantId:" + tenantId + " " + "Login User API initiated...");

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.logged.in.failed", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.USER_LOGGEDIN_FAILED.getValue());
        response.setResponseData(null);

        try {
            User user = userService.findByTenantIdAndUsernameOrEmail(tenantId,
                    loginRequest.getUserName(), loginRequest.getUserName());

            if (BKCoreUtil.isNull(user.getUsername()) || BKCoreUtil.isNull(user.getEmail())) {
                response.setResponseMessage(messageSource.getMessage("user.not.found", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseData(null);
                logger.info("TenantId:" + tenantId + " " + "The User not found...", null, new Locale(locale));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (!user.getActive()) {
                response.setResponseMessage(messageSource.getMessage("user.not.active", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_NOT_ACTIVE.getValue());
                response.setResponseData(null);
                logger.info("TenantId:" + tenantId + " " + "The User not active...", null, new Locale(locale));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                response.setResponseData(user);
                response.setResponseMessage(messageSource.getMessage("user.logged.in.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_ACCESS_GRANTED.getValue());
                logger.info("TenantId:" + tenantId + " " + "User Logged in successfully...", null, new Locale(locale));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "User Login failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Admin Logged out ",
            notes = "This method will Log out the User",
            produces = "application/json", nickname = "Logging Out ",
            response = BKAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Logout success", response = BKAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = BKAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = BKAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = BKAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = BKAPIResponse.class)})
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logOutUser(HttpServletRequest request) {
        String tenantId = request.getHeader(BKConstants.TENANT_ID);
        String locale = request.getHeader(BKConstants.ACCEPT_LANGUAGE);

        logger.info("TenantId:" + tenantId + " " + "LogoutUser API initiated...");

        String authHeader = request.getHeader("Authorization");
        logger.info("TenantId:" + tenantId + " " + "Checking Request Header...:" + authHeader);

        BKAPIResponse response = new BKAPIResponse();
        response.setResponseMessage(messageSource.getMessage("user.logged.out.failed", null, new Locale(locale)));
        response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_FAILED.getValue());
        response.setResponseData(authHeader);

        try {
            if (!BKCoreUtil.isNull(authHeader)) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                response.setResponseMessage(messageSource.getMessage("user.logged.out.success", null, new Locale(locale)));
                response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_SUCCESS.getValue());
                response.setResponseData(null);

                logger.info("TenantId:" + tenantId + " " + "User logging out ended ...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("TenantId:" + tenantId + " " + "logOutUser failed.", ex.fillInStackTrace());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageSource.getMessage("exception.occurs", null, new Locale(locale)));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
