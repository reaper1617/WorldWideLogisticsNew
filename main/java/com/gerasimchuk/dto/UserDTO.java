package com.gerasimchuk.dto;

/**
 * User Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class UserDTO {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String personalNumber;
    private String password;
    private String role;
    private String hoursWorked;
    private String driverStatus;
    private String currentCityName;
    private String currentTruckRegistrationNumber;
    private String orderId;
    private String orderDescription;


    /**
     * Instantiates a new User dto.
     */
    public UserDTO() {
    }

    /**
     * Instantiates a new User dto.
     *
     * @param id                             the id
     * @param firstName                      the first name
     * @param middleName                     the middle name
     * @param lastName                       the last name
     * @param personalNumber                 the personal number
     * @param password                       the password
     * @param role                           the role
     * @param hoursWorked                    the hours worked
     * @param driverStatus                   the driver status
     * @param currentCityName                the current city name
     * @param currentTruckRegistrationNumber the current truck registration number
     * @param orderId                        the order id
     */
    public UserDTO(String id, String firstName, String middleName, String lastName, String personalNumber, String password, String role, String hoursWorked, String driverStatus, String currentCityName, String currentTruckRegistrationNumber, String orderId) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.password = password;
        this.role = role;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.currentCityName = currentCityName;
        this.currentTruckRegistrationNumber = currentTruckRegistrationNumber;
        this.orderId = orderId;
    }

    /**
     * Instantiates a new User dto.
     *
     * @param id                             the id
     * @param firstName                      the first name
     * @param middleName                     the middle name
     * @param lastName                       the last name
     * @param personalNumber                 the personal number
     * @param password                       the password
     * @param role                           the role
     * @param hoursWorked                    the hours worked
     * @param driverStatus                   the driver status
     * @param currentCityName                the current city name
     * @param currentTruckRegistrationNumber the current truck registration number
     * @param orderId                        the order id
     * @param orderDescription               the order description
     */
    public UserDTO(String id, String firstName, String middleName, String lastName, String personalNumber, String password, String role, String hoursWorked, String driverStatus, String currentCityName, String currentTruckRegistrationNumber, String orderId, String orderDescription) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.password = password;
        this.role = role;
        this.hoursWorked = hoursWorked;
        this.driverStatus = driverStatus;
        this.currentCityName = currentCityName;
        this.currentTruckRegistrationNumber = currentTruckRegistrationNumber;
        this.orderId = orderId;
        this.orderDescription = orderDescription;
    }

    /**
     * Gets order description.
     *
     * @return the order description
     */
    public String getOrderDescription() {
        return orderDescription;
    }

    /**
     * Sets order description.
     *
     * @param orderDescription the order description
     */
    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
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
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets middle name.
     *
     * @param middleName the middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Gets hours worked.
     *
     * @return the hours worked
     */
    public String getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Sets hours worked.
     *
     * @param hoursWorked the hours worked
     */
    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
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
     * Gets current city name.
     *
     * @return the current city name
     */
    public String getCurrentCityName() {
        return currentCityName;
    }

    /**
     * Sets current city name.
     *
     * @param currentCityName the current city name
     */
    public void setCurrentCityName(String currentCityName) {
        this.currentCityName = currentCityName;
    }

    /**
     * Gets current truck registration number.
     *
     * @return the current truck registration number
     */
    public String getCurrentTruckRegistrationNumber() {
        return currentTruckRegistrationNumber;
    }

    /**
     * Sets current truck registration number.
     *
     * @param currentTruckRegistrationNumber the current truck registration number
     */
    public void setCurrentTruckRegistrationNumber(String currentTruckRegistrationNumber) {
        this.currentTruckRegistrationNumber = currentTruckRegistrationNumber;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO driverDTO = (UserDTO) o;

        if (!firstName.equals(driverDTO.firstName)) return false;
        if (!middleName.equals(driverDTO.middleName)) return false;
        if (!lastName.equals(driverDTO.lastName)) return false;
        if (personalNumber != null ? !personalNumber.equals(driverDTO.personalNumber) : driverDTO.personalNumber != null)
            return false;
        if (!hoursWorked.equals(driverDTO.hoursWorked)) return false;
        if (!driverStatus.equals(driverDTO.driverStatus)) return false;
        if (!currentCityName.equals(driverDTO.currentCityName)) return false;
        return currentTruckRegistrationNumber != null ? currentTruckRegistrationNumber.equals(driverDTO.currentTruckRegistrationNumber) : driverDTO.currentTruckRegistrationNumber == null;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (personalNumber != null ? personalNumber.hashCode() : 0);
        result = 31 * result + hoursWorked.hashCode();
        result = 31 * result + driverStatus.hashCode();
        result = 31 * result + currentCityName.hashCode();
        result = 31 * result + (currentTruckRegistrationNumber != null ? currentTruckRegistrationNumber.hashCode() : 0);
        return result;
    }
}
