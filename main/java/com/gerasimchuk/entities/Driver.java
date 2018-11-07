package com.gerasimchuk.entities;

import com.gerasimchuk.enums.DriverStatus;
import com.gerasimchuk.utils.JSONconvertable;
import com.google.gson.Gson;

import javax.persistence.*;

/**
 * The type Driver.
 */
@Entity(name = "Drivers")
@Table(name = "drivers", schema = "mywwldatabase", catalog = "")
public class Driver implements JSONconvertable {

    private int id;
    private double hoursWorked;
    private DriverStatus status;
    private City currentCity;
    private Truck currentTruck;
    private User user;


    /**
     * Instantiates a new Driver.
     */
    public Driver() {
    }

    /**
     * Instantiates a new Driver.
     *
     * @param hoursWorked  the hours worked
     * @param status       the status
     * @param currentCity  the current city
     * @param currentTruck the current truck
     */
    public Driver(double hoursWorked, DriverStatus status, City currentCity, Truck currentTruck) {
        this.hoursWorked = hoursWorked;
        this.status = status;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets hours worked.
     *
     * @return the hours worked
     */
    @Column(name = "hours_worked", nullable = false)
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Sets hours worked.
     *
     * @param hoursWorked the hours worked
     */
    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public DriverStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    /**
     * Gets current city.
     *
     * @return the current city
     */
    @ManyToOne
    @JoinColumn(name = "current_city_id", nullable = false)
    public City getCurrentCity() {
        return currentCity;
    }

    /**
     * Sets current city.
     *
     * @param currentCity the current city
     */
    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }


    /**
     * Gets current truck.
     *
     * @return the current truck
     */
    @ManyToOne
    @JoinColumn(name = "current_truck_id")
    public Truck getCurrentTruck() {
        return currentTruck;
    }

    /**
     * Sets current truck.
     *
     * @param currentTruck the current truck
     */
    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    @OneToOne(mappedBy = "driver")
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String convertToJSONString() {
        StringBuilder result = new StringBuilder("{");
        result.append("\"id\":\"").append(id).append("\",");
        result.append("\"hoursWorked\":\"").append(hoursWorked).append("\",");
        result.append("\"status\":\"").append(status).append("\",");
        result.append("\"currentCity\":\"").append(currentCity.getName()).append("\"");
        if (currentTruck != null) result.append(",\"currentTruck\":\"").append(currentTruck.getRegistrationNumber()).append("\"");
        result.append("}");
        return result.toString();
    }
}
