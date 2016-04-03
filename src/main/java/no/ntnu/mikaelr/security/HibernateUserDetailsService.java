package no.ntnu.mikaelr.security;

import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserRole;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("userDetailsService")
public class HibernateUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    /*
     * Retrieves the specified user from entities object and creates a UserDetails object(needed for http authentication)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if(user != null) {
            return buildUser(user);
        }
        else {
            throw new UsernameNotFoundException("Username not found.");

        }
    }

    /*
     * Converts a com.andreasogeirik.model.entities.UserIncoming to a org.springframework.security.core.userdetails.UserIncoming
     */
    private SessionUser buildUser(User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        if(user.getRoles() != null) {
            Iterator it = user.getRoles().iterator();

            while (it.hasNext()) {
                authorities.add(new SimpleGrantedAuthority(((UserRole) it.next()).getRole()));
            }
        }
        return new SessionUser(user.getId(), user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }
}