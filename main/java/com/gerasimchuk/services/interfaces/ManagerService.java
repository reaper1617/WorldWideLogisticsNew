package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.entities.*;

import java.util.Collection;

/**
 * Manager Service
 *
 * @author Reaper
 * @version 1.0
 */
public interface ManagerService {

    /**
     * Add new cargo.
     *
     * @param cargo the cargo
     */
    void addNewCargo(Cargo cargo);

    /**
     * Change cargo.
     *
     * @param cargo the cargo
     */
    void changeCargo(Cargo cargo);

    /**
     * Delete cargo.
     *
     * @param cargo the cargo
     */
    void deleteCargo(Cargo cargo);

    /**
     * Add new driver.
     *
     * @param driver the driver
     */
    void addNewDriver(User driver);

    /**
     * Change driver.
     *
     * @param driver the driver
     */
    void changeDriver(User driver);

    /**
     * Delete driver.
     *
     * @param driver the driver
     */
    void deleteDriver(User driver);

    /**
     * Add new truck.
     *
     * @param truck the truck
     */
    void addNewTruck(Truck truck);

    /**
     * Change truck.
     *
     * @param truck the truck
     */
    void changeTruck(Truck truck);

    /**
     * Delete truck.
     *
     * @param truck the truck
     */
    void deleteTruck(Truck truck);

    /**
     * Add new order.
     *
     * @param order the order
     */
    void addNewOrder(Order order);

    /**
     * Gets trucks fit to order.
     *
     * @param order the order
     * @return the trucks fit to order
     */
    Collection<Truck> getTrucksFitToOrder(Order order);

    /**
     * Assign truck to order.
     *
     * @param order the order
     * @param truck the truck
     */
    void assignTruckToOrder(Order order, Truck truck);

    /**
     * Gets drivers fit to truck with assigned order.
     *
     * @param order the order
     * @return the drivers fit to truck with assigned order
     */
    Collection<Driver> getDriversFitToTruckWithAssignedOrder(Order order);

    /**
     * Assign drivers to truck.
     *
     * @param truck   the truck
     * @param drivers the drivers
     */
    void assignDriversToTruck(Truck truck, Collection<Driver> drivers);


}
