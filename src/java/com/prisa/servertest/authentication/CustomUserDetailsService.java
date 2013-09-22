/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.authentication;

import com.prisa.servertest.entities.User;
import com.prisa.servertest.services.Database;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author DUBIC
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Inject
    private Database db;
    private User u;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            u = db.findUserByUsername(username);
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                    grantedAuths.add(new SimpleGrantedAuthority(u.getRole().getName()));
                    return grantedAuths;
                }

                @Override
                public String getPassword() {
                    return u.getPassword();
                }

                @Override
                public String getUsername() {
                    return u.getEmail();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return false;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return false;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return false;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
//            com.dubic.usermgt.models.dto.UserDetails ud = new com.dubic.usermgt.models.dto.UserDetails(u);
//            return ud;
        } catch (Exception ex) {
            Logger.getLogger(CustomUserDetailsService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UsernameNotFoundException(username + " NOT FOUND");
        }
    }
}
