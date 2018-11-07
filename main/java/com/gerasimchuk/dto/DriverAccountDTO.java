package com.gerasimchuk.dto;


/**
 * Driver Account Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class DriverAccountDTO {
    private String driverId; // its user id !!
    private String driverStatus;
    private String orderId;
    private String orderStatus;
    private String cargoId;
    private String cargoStatus;

    /**
     * Instantiates a new Driver account dto.
     */
    public DriverAccountDTO() {
    }

    /**
     * Instantiates a new Driver account dto.
     *
     * @param driverId     the driver id
     * @param driverStatus the driver status
     * @param orderId      the order id
     * @param orderStatus  the order status
     * @param cargoId      the cargo id
     * @param cargoStatus  the cargo status
     */
    public DriverAccountDTO(String driverId, String driverStatus, String orderId, String orderStatus, String cargoId, String cargoStatus) {
        this.driverId = driverId;
        this.driverStatus = driverStatus;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.cargoId = cargoId;
        this.cargoStatus = cargoStatus;
    }

    /**
     * Gets driver id.
     *
     * @return the driver id
     */
    public String getDriverId() {
        return driverId;
    }

    /**
     * Sets driver id.
     *
     * @param driverId the driver id
     */
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    /**
     * Gets driver status.
     *
     * @return the driver status
     */
    public String getDriverStatus() {
        return driverStatus;
    }

    /**
     * Sets driver status.
     *
     * @param driverStatus the driver status
     */
    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets order status.
     *
     * @return the order status
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets order status.
     *
     * @param orderStatus the order status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Gets cargo id.
     *
     * @return the cargo id
     */
    public String getCargoId() {
        return cargoId;
    }

    /**
     * Sets cargo id.
     *
     * @param cargoId the cargo id
     */
    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    /**
     * Gets cargo status.
     *
     * @return the cargo status
     */
    public String getCargoStatus() {
        return cargoStatus;
    }

    /**
     * Sets cargo status.
     *
     * @param cargoStatus the cargo status
     */
    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
