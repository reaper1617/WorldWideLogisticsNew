package com.gerasimchuk.services.impls;


import com.gerasimchuk.services.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


/**
 * Implementation of {@link SecurityService} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SecurityServiceImpl.class);

    /**
     * Instantiates a new Security service.
     *
     * @param authenticationManager the authentication manager
     * @param userDetailsService    the user details service
     */
    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public String findLoggedInUsername() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: findLoggedInUsername");
        Object userDetails = null;
        try {
            userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        }
        catch (Exception e){
            LOGGER.error("Error: userDetails object is null.");
            e.printStackTrace();
            return null;
        }
        LOGGER.info("userDetails object is " + userDetails);
        if (userDetails instanceof UserDetails){
            String username = ((UserDetails) userDetails).getUsername();
            LOGGER.info("Found loggedInUsername is " + username);
            return username;
        }
        LOGGER.error("Error: userDetails object is not instance of UserDetails.");
        return null;
    }

    /**
     * Auto login.
     *
     * @param username the username
     * @param password the password
     */
    public void autoLogin(String username, String password) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: autoLogin");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        LOGGER.info("userDetails object is " + userDetails);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            LOGGER.info("Successfully logged as " + username);
        }
        LOGGER.error("Authentication failed.");
    }
}
