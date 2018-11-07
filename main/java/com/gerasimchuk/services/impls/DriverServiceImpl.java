package com.gerasimchuk.services.impls;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.DriverStatus;
import com.gerasimchuk.enums.OrderStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.rabbit.RabbitMQSender;
import com.gerasimchuk.repositories.DriverRepository;
import com.gerasimchuk.services.interfaces.DriverService;
import com.gerasimchuk.services.interfaces.UserService;
import com.gerasimchuk.utils.MessageConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation for {@link DriverService} interface
 *
 * @author Reaper
 * @version 1.0
 */


@Service
public class DriverServiceImpl implements DriverService {

    private UserService userService;

    private DriverRepository driverRepository;

    /**
     * Instantiates a new Driver service.
     *
     * @param userService        the user service
     * @param rabbitMQSender     the rabbit mq sender
     * @param messageConstructor the message constructor
     * @param driverRepository   the driver repository
     */
    @Autowired
    public DriverServiceImpl(UserService userService, RabbitMQSender rabbitMQSender, MessageConstructor messageConstructor, DriverRepository driverRepository) {
        this.userService = userService;
        this.driverRepository = driverRepository;
    }

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DriverServiceImpl.class);

    public DriverStatus getDriverStatusValFromString(String status) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getDriverStatusValFromString");
        if (status == null || status.length()==0) {
            LOGGER.error("Error: status string is null or empty.");
            return null;
        }
        DriverStatus result = null;
        if (status.equals("FREE")) result = DriverStatus.FREE;
        if (status.equals("DRIVING")) result = DriverStatus.DRIVING;
        if (status.equals("RESTING")) result = DriverStatus.RESTING;
        if (status.equals("SECOND_DRIVER")) result = DriverStatus.SECOND_DRIVER;
        if (status.equals("LOAD_UNLOAD_WORKS")) result = DriverStatus.LOAD_UNLOAD_WORKS;
        if (result != null){
            LOGGER.info("Driver status is " + result);
        }
        else {
            LOGGER.error("Error: driver status is null");
        }
        return result;
    }

    @Transactional
    @Override
    public void updateDriverHoursWorked() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateDriverHoursWorked");
        Collection<User> driversWithoutOrders = userService.getFreeDrivers();
        for(User user: driversWithoutOrders){
            Driver d = user.getDriver();
            driverRepository.update(d.getId(),0,d.getStatus(),d.getCurrentCity(),d.getCurrentTruck());
        }
        LOGGER.info("Class: " + this.getClass().getName() + " out from updateDriverHoursWorked method: all free drivers");
    }

    @Override
    public UpdateMessageType updateDriverStatus(int driverId, DriverStatus newStatus) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateDriverStatus");
        if (driverId <= 0 ){
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: driver id is not valid.");
            return UpdateMessageType.ERROR_DRIVER_ID_IS_NOT_VALID;
        }
        if (newStatus == null){
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: new driver status is null.");
            return UpdateMessageType.ERROR_DRIVER_STATUS_IS_NULL;
        }
        Driver d = driverRepository.getById(driverId);
        if (d == null) {
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: there is no such driver in database.");
            return UpdateMessageType.ERROR_NO_DRIVER_WITH_THIS_ID;
        }
        Truck currentTruck = d.getCurrentTruck();
        if (currentTruck == null && !newStatus.equals(DriverStatus.FREE)){
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: can not change driver status from FREE when driver has no assigned order to execute");
            return UpdateMessageType.ERROR_DRIVER_HAS_NO_ASSIGNED_ORDER;
        }
        Order currentOrder = null;
        if (currentTruck != null) currentOrder = currentTruck.getAssignedOrder();
        if (currentOrder == null && !newStatus.equals(DriverStatus.FREE)){
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: can not change driver status: driver doesn't execute ny order");
            return UpdateMessageType.ERROR_DRIVER_HAS_NO_ASSIGNED_ORDER;
        }
        if (newStatus.equals(DriverStatus.FREE) && currentOrder!=null && !currentOrder.getStatus().equals(OrderStatus.EXECUTED)){
            LOGGER.error("Class:" + this.getClass().getName() + " out from updateDriverStatus method: can not change driver status to FREE when driver has an assigned order which is not executed yet");
            return UpdateMessageType.ERROR_DRIVER_HAS_UNEXECUTED_ASSIGNED_ORDER;
        }
        Driver upd = driverRepository.update(d.getId(),d.getHoursWorked(),newStatus,d.getCurrentCity(),d.getCurrentTruck());
        return UpdateMessageType.DRIVER_STATUS_UPDATED;
    }

}
