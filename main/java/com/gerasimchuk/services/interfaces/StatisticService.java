package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.utils.JSONconvertable;

/**
 * The interface Statistic service.
 */
public interface StatisticService extends JSONconvertable {
    /**
     * Gets num of trucks total.
     *
     * @return the num of trucks total
     */
    int getNumOfTrucksTotal();

    /**
     * Gets num of trucks free.
     *
     * @return the num of trucks free
     */
    int getNumOfTrucksFree();

    /**
     * Gets num of trucks not ready.
     *
     * @return the num of trucks not ready
     */
    int getNumOfTrucksNotReady();

    /**
     * Gets num of trucks executing orders.
     *
     * @return the num of trucks executing orders
     */
    int getNumOfTrucksExecutingOrders();

    /**
     * Gets num of drivers total.
     *
     * @return the num of drivers total
     */
    int getNumOfDriversTotal();

    /**
     * Gets num of drivers free.
     *
     * @return the num of drivers free
     */
    int getNumOfDriversFree();

    /**
     * Gets num of drivers executing orders.
     *
     * @return the num of drivers executing orders
     */
    int getNumOfDriversExecutingOrders();
}
