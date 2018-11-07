package com.gerasimchuk.dto;

import java.util.Arrays;

/**
 * Truck Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class TruckDTO {
    private String id;
    private String registrationNumber;
    private String numberOfDrivers;
    private String capacity;
    private String state;
    private String currentCity;
    private String[] assignedDrivers;
    private String[] assignedDriversNames;
    private String assignedOrderId;
    private String assignedOrderDescription;

    /**
     * Instantiates a new Truck dto.
     */
    public TruckDTO() {
    }

    /**
     * Instantiates a new Truck dto.
     *
     * @param id                 the id
     * @param registrationNumber the registration number
     * @param numberOfDrivers    the number of drivers
     * @param capacity           the capacity
     * @param state              the state
     * @param currentCity        the current city
     * @param assignedDrivers    the assigned drivers
     * @param assignedOrderId    the assigned order id
     */
    public TruckDTO(String id, String registrationNumber, String numberOfDrivers, String capacity, String state, String currentCity, String[] assignedDrivers, String assignedOrderId) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.numberOfDrivers = numberOfDrivers;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
        this.assignedDrivers = assignedDrivers;
        this.assignedOrderId = assignedOrderId;
    }

    /**
     * Instantiates a new Truck dto.
     *
     * @param id                       the id
     * @param registrationNumber       the registration number
     * @param numberOfDrivers          the number of drivers
     * @param capacity                 the capacity
     * @param state                    the state
     * @param currentCity              the current city
     * @param assignedDrivers          the assigned drivers
     * @param assignedDriversNames     the assigned drivers names
     * @param assignedOrderId          the assigned order id
     * @param assignedOrderDescription the assigned order description
     */
    public TruckDTO(String id, String registrationNumber, String numberOfDrivers, String capacity, String state, String currentCity, String[] assignedDrivers, String[] assignedDriversNames, String assignedOrderId, String assignedOrderDescription) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.numberOfDrivers = numberOfDrivers;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
        this.assignedDrivers = assignedDrivers;
        this.assignedDriversNames = assignedDriversNames;
        this.assignedOrderId = assignedOrderId;
        this.assignedOrderDescription = assignedOrderDescription;
    }

    /**
     * Get assigned drivers names string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAssignedDriversNames() {
        return assignedDriversNames;
    }

    /**
     * Sets assigned drivers names.
     *
     * @param assignedDriversNames the assigned drivers names
     */
    public void setAssignedDriversNames(String[] assignedDriversNames) {
        this.assignedDriversNames = assignedDriversNames;
    }

    /**
     * Gets assigned order description.
     *
     * @return the assigned order description
     */
    public String getAssignedOrderDescription() {
        return assignedOrderDescription;
    }

    /**
     * Sets assigned order description.
     *
     * @param assignedOrderDescription the assigned order description
     */
    public void setAssignedOrderDescription(String assignedOrderDescription) {
        this.assignedOrderDescription = assignedOrderDescription;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets registration number.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets registration number.
     *
     * @param registrationNumber the registration number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets number of drivers.
     *
     * @return the number of drivers
     */
    public String getNumberOfDrivers() {
        return numberOfDrivers;
    }

    /**
     * Sets number of drivers.
     *
     * @param numberOfDrivers the number of drivers
     */
    public void setNumberOfDrivers(String numberOfDrivers) {
        this.numberOfDrivers = numberOfDrivers;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets current city.
     *
     * @return the current city
     */
    public String getCurrentCity() {
        return currentCity;
    }

    /**
     * Sets current city.
     *
     * @param currentCity the current city
     */
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    /**
     * Get assigned drivers string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAssignedDrivers() {
        return assignedDrivers;
    }

    /**
     * Sets assigned drivers.
     *
     * @param assignedDrivers the assigned drivers
     */
    public void setAssignedDrivers(String[] assignedDrivers) {
        this.assignedDrivers = assignedDrivers;
    }

    /**
     * Gets assigned order id.
     *
     * @return the assigned order id
     */
    public String getAssignedOrderId() {
        return assignedOrderId;
    }

    /**
     * Sets assigned order id.
     *
     * @param assignedOrderId the assigned order id
     */
    public void setAssignedOrderId(String assignedOrderId) {
        this.assignedOrderId = assignedOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckDTO)) return false;

        TruckDTO truckDTO = (TruckDTO) o;

        if (!registrationNumber.equals(truckDTO.registrationNumber)) return false;
        if (!numberOfDrivers.equals(truckDTO.numberOfDrivers)) return false;
        if (!capacity.equals(truckDTO.capacity)) return false;
        if (!state.equals(truckDTO.state)) return false;
        if (!currentCity.equals(truckDTO.currentCity)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(assignedDrivers, truckDTO.assignedDrivers)) return false;
        return assignedOrderId != null ? assignedOrderId.equals(truckDTO.assignedOrderId) : truckDTO.assignedOrderId == null;
    }

    @Override
    public int hashCode() {
        int result = registrationNumber.hashCode();
        result = 31 * result + numberOfDrivers.hashCode();
        result = 31 * result + capacity.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + currentCity.hashCode();
        result = 31 * result + (assignedDrivers != null ? assignedDrivers.hashCode() : 0);
        result = 31 * result + (assignedOrderId != null ? assignedOrderId.hashCode() : 0);
        return result;
    }
}
