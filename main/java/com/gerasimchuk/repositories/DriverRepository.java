package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverStatus;

import java.util.Collection;

/**
 * The interface Driver repository.
 */
public interface DriverRepository {
    /**
     * Create driver.
     *
     * @param hoursWorked  the hours worked
     * @param status       the status
     * @param currentCity  the current city
     * @param currentTruck the current truck
     * @return the driver
     */
    Driver create(double hoursWorked, DriverStatus status, City currentCity, Truck currentTruck);

    /**
     * Update driver.
     *
     * @param id           the id
     * @param hoursWorked  the hours worked
     * @param status       the status
     * @param currentCity  the current city
     * @param currentTruck the current truck
     * @return the driver
     */
    Driver update(int id,double hoursWorked, DriverStatus status, City currentCity, Truck currentTruck);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Driver getById(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Driver> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);


    /**
     * Gets num of drivers total.
     *
     * @return the num of drivers total
     */
    int getNumOfDriversTotal();

    /**
     * Gets num of drivers executing orders.
     *
     * @return the num of drivers executing orders
     */
    int getNumOfDriversExecutingOrders();

    /**
     * Gets num of drivers free.
     *
     * @return the num of drivers free
     */
    int getNumOfDriversFree();
}
