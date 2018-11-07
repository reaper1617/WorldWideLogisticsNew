package com.gerasimchuk.services.impls;

import com.gerasimchuk.entities.User;
import com.gerasimchuk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for {@link UserDetailsService} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CustomUserDetailsService.class);

    private UserRepository userRepository;

    /**
     * Instantiates a new Custom user details service.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String personalNumber) throws UsernameNotFoundException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: loadUserByUsername");
        User user = userRepository.getByPersonalNumber(personalNumber);
        if (user == null){
            LOGGER.error("Error: load user failed");
            throw new UsernameNotFoundException("Error: user not found, input personal number is " + personalNumber);
        }
        LOGGER.info("Loaded user: id = " + user.getId());
        String username = user.getPersonalNumber();
        String password = user.getPassword();
        LOGGER.info("Loaded user personal number = " + user.getPersonalNumber());
        LOGGER.info("Loaded user password = " + user.getPassword());
        String role = null;
        if (user.getDriver()!=null) role="DRIVER";
        if (user.getManager()!=null) role="MANAGER";
        if (user.getAdmin()!=null) role = "ADMIN";
        LOGGER.info("Loaded user role = " + role);
        GrantedAuthority auth = new SimpleGrantedAuthority(role);
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        set.add(auth);
        UserDetails details = new org.springframework.security.core.userdetails.User(username,password,set);
        LOGGER.info("UserDetails object " + details + " successfully created");
        return details;
    }
}
