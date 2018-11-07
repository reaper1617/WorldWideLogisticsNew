package com.gerasimchuk.exceptions.routeexceptions;

/**
 * Route Exception Class
 *
 * @author Reaper
 * @version 1.0
 */
public class RouteException extends Exception {


    /**
     * Instantiates a new Route exception.
     */
    public RouteException() {
    }

    /**
     * Instantiates a new Route exception.
     *
     * @param message the message
     */
    public RouteException(String message) {
        super(message);
    }
}
