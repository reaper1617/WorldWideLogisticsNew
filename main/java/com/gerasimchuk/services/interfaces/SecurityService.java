package com.gerasimchuk.services.interfaces;

/**
 * Service for security
 *
 * @author Reaper
 * @version 1.0
 */
public interface SecurityService {

    /**
     * Find logged in username string.
     *
     * @return the string
     */
    String findLoggedInUsername();

//    void autoLogin(String username, String password);

}
