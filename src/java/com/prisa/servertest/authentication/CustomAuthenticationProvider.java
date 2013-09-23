/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.authentication;

import com.prisa.servertest.entities.Role;
import com.prisa.servertest.entities.User;
import com.prisa.servertest.enums.UserState;
import com.prisa.servertest.services.Database;
import com.prisa.servertest.services.UserService;
import com.prisa.servertest.utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author DUBIC
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger log = Logger.getLogger(CustomAuthenticationProvider.class);
    @Inject
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        log.debug("username = " + a.getName());
        log.debug("password = " + a.getCredentials().toString());
        String enc_password = TestUtils.hash(a.getName() + a.getCredentials().toString());
        log.debug("enc password = " + enc_password);
        User user;

        user = userService.findUserByUsernamePassword(a.getName(), enc_password);

        if (user != null) {
            //check if password has expired
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole().getName()));
            Authentication auth = new UsernamePasswordAuthenticationToken(a.getName(), a.getCredentials().toString(), grantedAuths);
            SecurityContextHolder.getContext().setAuthentication(auth);
            user.setUserState(UserState.ONLINE);
            try {
                userService.updateUser(user);
            } catch (Exception ex) {
                log.debug("could not set user to online");
            }
            return auth;
        } else {

            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
//    public void setAuthentication(User user) {
//        if (user != null) {
//            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
//            for (Role role : user.getRole()) {
//                grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
//            }
//
//            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword().toString(), grantedAuths);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//        }
//    }
}
