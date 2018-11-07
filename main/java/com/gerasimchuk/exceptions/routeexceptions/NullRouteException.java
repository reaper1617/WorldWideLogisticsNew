package com.gerasimchuk.exceptions.routeexceptions;

/**
 * Null route Exception Class
 *
 * @author Reaper
 * @version 1.0
 */
public class NullRouteException extends RouteException {


    /**
     * Instantiates a new Null route exception.
     */
    public NullRouteException() {
        super("No such route!");
    }

    /**
     * Instantiates a new Null route exception.
     *
     * @param message the message
     */
    public NullRouteException(String message) {
        super(message);
    }
}
