package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.enums.DriverStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.utils.ReturnValuesContainer;

/**
 * Driver Service
 *
 * @author Reaper
 * @version 1.0
 */
public interface DriverService {

    /**
     * Gets driver status val from string.
     *
     * @param status the status
     * @return the driver status val from string
     */
    DriverStatus getDriverStatusValFromString(String status);

    /**
     * Update driver hours worked.
     */
    void updateDriverHoursWorked();

    /**
     * Update driver status update message type.
     *
     * @param driverId  the driver id
     * @param newStatus the new status
     * @return the update message type
     */
    UpdateMessageType updateDriverStatus(int driverId, DriverStatus newStatus);
//    ReturnValuesContainer<DriverStatus> updateDriverStatus(int driverId, DriverStatus newStatus, int val);
//    void testShedule();
}
