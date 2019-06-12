package com.rd.mtr.service.users;

import com.rd.mtr.dao.system.TenantDAO;
import com.rd.mtr.dao.users.UserDAO;
import com.rd.mtr.model.system.Tenant;
import com.rd.mtr.model.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userService")
public class AppUserService implements UserDetailsService {

    private final Logger logger = LogManager.getLogger(AppUserService.class);

    @Autowired
    private TenantDAO tenantDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String userNameTenantId) throws UsernameNotFoundException {
        logger.info("AppUserService - loadUserByUsername method called");

        String[] credInfo = userNameTenantId.split("__"); //double underscore
        User user = null;
        try {
            Tenant tenant = tenantDAO.findOneByTenantId(credInfo[1]);
            user = userDAO.findByTenantIdAndUsernameOrEmail(tenant.getName(), credInfo[0], credInfo[0]);
            if (user == null) {
                throw new UsernameNotFoundException("Invalid username or password.");
            }
        } catch (Exception ex) {
            logger.error("Unable to find tenant with tenant id.", ex.fillInStackTrace());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getActive(), true, true, true, getAuthorities(user));
    }

  /*  private List<SimpleGrantedAuthority> getRoles(List<Role> authList) {
        return authList.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }*/

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {

        List<String> perm = getPrivileges(user);
        return getGrantedAuthorities(perm);
    }

    private List<String> getPrivileges(User user) {
        List<String> privileges = new ArrayList<>();
        /*List<Permission> rolePermissions = permissionRepo.findUserPermissionByUserId(user.getId());

        for (Permission per : rolePermissions) {
            privileges.add(per.getName());
        }*/
        return privileges.stream().collect(Collectors.toList());
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
