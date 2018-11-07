package com.gerasimchuk.exceptions.driverexceptions;

/**
 * Driver Exception Class
 *
 * @author Reaper
 * @version 1.0
 */
public class DriverException extends Exception {

    /**
     * Instantiates a new Driver exception.
     */
    public DriverException() {
    }

    /**
     * Instantiates a new Driver exception.
     *
     * @param message the message
     */
    public DriverException(String message) {
        super(message);
    }
}
