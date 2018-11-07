package com.gerasimchuk.dto;

/**
 * The type Stats dto.
 */
public class StatsDTO {
    private int numOfTrucksTotal;
    private int numOfTrucksFree;
    private int numOfTrucksNotReady;
    private int numOfTrucksExecutingOrders;
    private int numOfDriversTotal;
    private int numOfDriversFree;
    private int numOfDriversExecutingOrders;

    /**
     * Instantiates a new Stats dto.
     */
    public StatsDTO() {
    }

    /**
     * Instantiates a new Stats dto.
     *
     * @param numOfTrucksTotal            the num of trucks total
     * @param numOfTrucksFree             the num of trucks free
     * @param numOfTrucksNotReady         the num of trucks not ready
     * @param numOfTrucksExecutingOrders  the num of trucks executing orders
     * @param numOfDriversTotal           the num of drivers total
     * @param numOfDriversFree            the num of drivers free
     * @param numOfDriversExecutingOrders the num of drivers executing orders
     */
    public StatsDTO(int numOfTrucksTotal, int numOfTrucksFree, int numOfTrucksNotReady, int numOfTrucksExecutingOrders, int numOfDriversTotal, int numOfDriversFree, int numOfDriversExecutingOrders) {
        this.numOfTrucksTotal = numOfTrucksTotal;
        this.numOfTrucksFree = numOfTrucksFree;
        this.numOfTrucksNotReady = numOfTrucksNotReady;
        this.numOfTrucksExecutingOrders = numOfTrucksExecutingOrders;
        this.numOfDriversTotal = numOfDriversTotal;
        this.numOfDriversFree = numOfDriversFree;
        this.numOfDriversExecutingOrders = numOfDriversExecutingOrders;
    }

    /**
     * Gets num of trucks total.
     *
     * @return the num of trucks total
     */
    public int getNumOfTrucksTotal() {
        return numOfTrucksTotal;
    }

    /**
     * Sets num of trucks total.
     *
     * @param numOfTrucksTotal the num of trucks total
     */
    public void setNumOfTrucksTotal(int numOfTrucksTotal) {
        this.numOfTrucksTotal = numOfTrucksTotal;
    }

    /**
     * Gets num of trucks free.
     *
     * @return the num of trucks free
     */
    public int getNumOfTrucksFree() {
        return numOfTrucksFree;
    }

    /**
     * Sets num of trucks free.
     *
     * @param numOfTrucksFree the num of trucks free
     */
    public void setNumOfTrucksFree(int numOfTrucksFree) {
        this.numOfTrucksFree = numOfTrucksFree;
    }

    /**
     * Gets num of trucks not ready.
     *
     * @return the num of trucks not ready
     */
    public int getNumOfTrucksNotReady() {
        return numOfTrucksNotReady;
    }

    /**
     * Sets num of trucks not ready.
     *
     * @param numOfTrucksNotReady the num of trucks not ready
     */
    public void setNumOfTrucksNotReady(int numOfTrucksNotReady) {
        this.numOfTrucksNotReady = numOfTrucksNotReady;
    }

    /**
     * Gets num of trucks executing orders.
     *
     * @return the num of trucks executing orders
     */
    public int getNumOfTrucksExecutingOrders() {
        return numOfTrucksExecutingOrders;
    }

    /**
     * Sets num of trucks executing orders.
     *
     * @param numOfTrucksExecutingOrders the num of trucks executing orders
     */
    public void setNumOfTrucksExecutingOrders(int numOfTrucksExecutingOrders) {
        this.numOfTrucksExecutingOrders = numOfTrucksExecutingOrders;
    }

    /**
     * Gets num of drivers total.
     *
     * @return the num of drivers total
     */
    public int getNumOfDriversTotal() {
        return numOfDriversTotal;
    }

    /**
     * Sets num of drivers total.
     *
     * @param numOfDriversTotal the num of drivers total
     */
    public void setNumOfDriversTotal(int numOfDriversTotal) {
        this.numOfDriversTotal = numOfDriversTotal;
    }

    /**
     * Gets num of drivers free.
     *
     * @return the num of drivers free
     */
    public int getNumOfDriversFree() {
        return numOfDriversFree;
    }

    /**
     * Sets num of drivers free.
     *
     * @param numOfDriversFree the num of drivers free
     */
    public void setNumOfDriversFree(int numOfDriversFree) {
        this.numOfDriversFree = numOfDriversFree;
    }

    /**
     * Gets num of drivers executing orders.
     *
     * @return the num of drivers executing orders
     */
    public int getNumOfDriversExecutingOrders() {
        return numOfDriversExecutingOrders;
    }

    /**
     * Sets num of drivers executing orders.
     *
     * @param numOfDriversExecutingOrders the num of drivers executing orders
     */
    public void setNumOfDriversExecutingOrders(int numOfDriversExecutingOrders) {
        this.numOfDriversExecutingOrders = numOfDriversExecutingOrders;
    }
}
