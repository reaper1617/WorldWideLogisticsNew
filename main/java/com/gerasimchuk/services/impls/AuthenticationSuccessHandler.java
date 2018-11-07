package com.gerasimchuk.services.impls;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom authentication success handler class extends {@link SavedRequestAwareAuthenticationSuccessHandler} class
 *
 * @author Reaper
 * @version 1.0
 */


public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AuthenticationSuccessHandler.class);
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: determineTargetUrl");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        String targetUrl = "";
        if(role.contains("MANAGER")) {
            targetUrl = "/managermainpage/0";
        } else if(role.contains("DRIVER")) {
            targetUrl = "/drivermainpage/0";
        }
        else if (role.contains("ADMIN")){
            targetUrl = "/adminmainpage/0";
        }
        LOGGER.info("Defined target url: " + targetUrl);
        return targetUrl;
    }
}
