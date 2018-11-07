package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;

import java.util.Collection;
import java.util.Set;

/**
 * The interface Truck repository.
 */
public interface TruckRepository {
    /**
     * Create truck.
     *
     * @param registrationNumber the registration number
     * @param numOfDrivers       the num of drivers
     * @param capacity           the capacity
     * @param state              the state
     * @param currentCity        the current city
     * @return the truck
     */
    Truck create(String registrationNumber,
                 int numOfDrivers,
                 double capacity,
                 TruckState state,
                 City currentCity);

//    Truck create(String registrationNumber,
//                 int numOfDrivers,
//                 double capacity,
//                 TruckState state,
//                 City currentCity,
//                 Set<Driver> driverInTruck,
//                 Order assignedOrder);

    /**
     * Update truck.
     *
     * @param id                 the id
     * @param registrationNumber the registration number
     * @param numOfDrivers       the num of drivers
     * @param capacity           the capacity
     * @param state              the state
     * @param currentCity        the current city
     * @return the truck
     */
    Truck update(int id,String registrationNumber, int numOfDrivers, double capacity, TruckState state, City currentCity);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Truck getById(int id);

    /**
     * Gets by registration number.
     *
     * @param registrationNumber the registration number
     * @return the by registration number
     */
    Truck getByRegistrationNumber(String registrationNumber);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Truck> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);


    /**
     * Gets number of trucks total.
     *
     * @return the number of trucks total
     */
// for statistics
    int getNumberOfTrucksTotal();

    /**
     * Gets number of trucks free.
     *
     * @return the number of trucks free
     */
    int getNumberOfTrucksFree();

    /**
     * Gets number of trucks not ready.
     *
     * @return the number of trucks not ready
     */
    int getNumberOfTrucksNotReady();

    /**
     * Gets number of trucks executing order.
     *
     * @return the number of trucks executing order
     */
    int getNumberOfTrucksExecutingOrder();

    /**
     * Gets trucks for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the trucks for one page
     */
// for pagination
    Collection<Truck> getTrucksForOnePage(int size, int pageNumber);

}
