package com.gerasimchuk.dto;

/**
 * The type Google marker dto.
 */
public class GoogleMarkerDTO {
    private String id;
    private String personalNumber;
    private String description;
    private String date;
    private String status;
    private String assignedTruckId;
    private String assignedTruckRegistrationNumber;
    private String[] cargosInOrder;
    private String[] assignedDrivers;
    private String[] route;
    private String currentCity;

    /**
     * Instantiates a new Google marker dto.
     */
    public GoogleMarkerDTO() {
    }

    /**
     * Instantiates a new Google marker dto.
     *
     * @param id                              the id
     * @param personalNumber                  the personal number
     * @param description                     the description
     * @param date                            the date
     * @param status                          the status
     * @param assignedTruckId                 the assigned truck id
     * @param assignedTruckRegistrationNumber the assigned truck registration number
     * @param cargosInOrder                   the cargos in order
     * @param assignedDrivers                 the assigned drivers
     * @param route                           the route
     * @param currentCity                     the current city
     */
    public GoogleMarkerDTO(String id,
                           String personalNumber,
                           String description,
                           String date,
                           String status,
                           String assignedTruckId,
                           String assignedTruckRegistrationNumber,
                           String[] cargosInOrder,
                           String[] assignedDrivers,
                           String[] route,
                           String currentCity) {
        this.id = id;
        this.personalNumber = personalNumber;
        this.description = description;
        this.date = date;
        this.status = status;
        this.assignedTruckId = assignedTruckId;
        this.assignedTruckRegistrationNumber = assignedTruckRegistrationNumber;
        this.cargosInOrder = cargosInOrder;
        this.assignedDrivers = assignedDrivers;
        this.route = route;
        this.currentCity = currentCity;
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
     * Gets personal number.
     *
     * @return the personal number
     */
    public String getPersonalNumber() {
        return personalNumber;
    }

    /**
     * Sets personal number.
     *
     * @param personalNumber the personal number
     */
    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets assigned truck id.
     *
     * @return the assigned truck id
     */
    public String getAssignedTruckId() {
        return assignedTruckId;
    }

    /**
     * Sets assigned truck id.
     *
     * @param assignedTruckId the assigned truck id
     */
    public void setAssignedTruckId(String assignedTruckId) {
        this.assignedTruckId = assignedTruckId;
    }

    /**
     * Gets assigned truck registration number.
     *
     * @return the assigned truck registration number
     */
    public String getAssignedTruckRegistrationNumber() {
        return assignedTruckRegistrationNumber;
    }

    /**
     * Sets assigned truck registration number.
     *
     * @param assignedTruckRegistrationNumber the assigned truck registration number
     */
    public void setAssignedTruckRegistrationNumber(String assignedTruckRegistrationNumber) {
        this.assignedTruckRegistrationNumber = assignedTruckRegistrationNumber;
    }

    /**
     * Get cargos in order string [ ].
     *
     * @return the string [ ]
     */
    public String[] getCargosInOrder() {
        return cargosInOrder;
    }

    /**
     * Sets cargos in order.
     *
     * @param cargosInOrder the cargos in order
     */
    public void setCargosInOrder(String[] cargosInOrder) {
        this.cargosInOrder = cargosInOrder;
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
     * Get route string [ ].
     *
     * @return the string [ ]
     */
    public String[] getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(String[] route) {
        this.route = route;
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
}
