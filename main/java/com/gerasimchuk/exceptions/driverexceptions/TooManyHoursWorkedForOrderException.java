package com.gerasimchuk.exceptions.driverexceptions;

/**
 * Too many hours worked for order Exception Class
 *
 * @author Reaper
 * @version 1.0
 */
public class TooManyHoursWorkedForOrderException extends DriverException {

    /**
     * Instantiates a new Too many hours worked for order exception.
     */
    public TooManyHoursWorkedForOrderException() {
        super("Driver hours worked value is too big to execute order");
    }

    /**
     * Instantiates a new Too many hours worked for order exception.
     *
     * @param s the s
     */
    public TooManyHoursWorkedForOrderException(String s) {
        super(s);
    }


}
