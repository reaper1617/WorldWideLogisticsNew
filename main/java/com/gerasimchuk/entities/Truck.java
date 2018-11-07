package com.gerasimchuk.entities;


import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.utils.JSONconvertable;

import javax.persistence.*;
import java.util.Set;

/**
 * The type Truck.
 */
@Entity(name = "Trucks")
@Table(name = "trucks", schema = "mywwldatabase", catalog = "")
public class Truck implements JSONconvertable {

    private int id;
    private String registrationNumber;
    private int numOfDrivers;
    private double capacity;
    private TruckState state;
    private City currentCity;
    private Set<Driver> driversInTruck;
    private Order assignedOrder;

    /**
     * Instantiates a new Truck.
     */
    public Truck() {
    }

    /**
     * Instantiates a new Truck.
     *
     * @param registrationNumber the registration number
     * @param numOfDrivers       the num of drivers
     * @param capacity           the capacity
     * @param state              the state
     * @param currentCity        the current city
     */
    public Truck(String registrationNumber, int numOfDrivers, double capacity, TruckState state, City currentCity) {
        this.registrationNumber = registrationNumber;
        this.numOfDrivers = numOfDrivers;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
    }

    /**
     * Instantiates a new Truck.
     *
     * @param registrationNumber the registration number
     * @param numOfDrivers       the num of drivers
     * @param capacity           the capacity
     * @param state              the state
     * @param currentCity        the current city
     * @param driversInTruck     the drivers in truck
     * @param assignedOrder      the assigned order
     */
    public Truck(String registrationNumber,
                 int numOfDrivers,
                 double capacity,
                 TruckState state,
                 City currentCity,
                 Set<Driver> driversInTruck,
                 Order assignedOrder) {
        this.registrationNumber = registrationNumber;
        this.numOfDrivers = numOfDrivers;
        this.capacity = capacity;
        this.state = state;
        this.currentCity = currentCity;
        this.driversInTruck = driversInTruck;
        this.assignedOrder = assignedOrder;
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
     * Gets registration number.
     *
     * @return the registration number
     */
    @Column(name = "registration_number", nullable = false, unique = true)
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
     * Gets num of drivers.
     *
     * @return the num of drivers
     */
    @Column(name = "num_of_drivers", nullable = false)
    public int getNumOfDrivers() {
        return numOfDrivers;
    }

    /**
     * Sets num of drivers.
     *
     * @param numOfDrivers the num of drivers
     */
    public void setNumOfDrivers(int numOfDrivers) {
        this.numOfDrivers = numOfDrivers;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    @Column(name = "capacity", nullable = false)
    public double getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    public TruckState getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(TruckState state) {
        this.state = state;
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
     * Gets drivers in truck.
     *
     * @return the drivers in truck
     */
    @OneToMany(mappedBy = "currentTruck",fetch = FetchType.EAGER)
    public Set<Driver> getDriversInTruck() {
        return driversInTruck;
    }

    /**
     * Sets drivers in truck.
     *
     * @param driversInTruck the drivers in truck
     */
    public void setDriversInTruck(Set<Driver> driversInTruck) {
        this.driversInTruck = driversInTruck;
    }

    /**
     * Gets assigned order.
     *
     * @return the assigned order
     */
    @OneToOne(mappedBy = "assignedTruck")
    public Order getAssignedOrder() {
        return assignedOrder;
    }

    /**
     * Sets assigned order.
     *
     * @param assignedOrder the assigned order
     */
    public void setAssignedOrder(Order assignedOrder) {
        this.assignedOrder = assignedOrder;
    }

    @Override
    public String convertToJSONString() {
        StringBuilder result = new StringBuilder("{");
        result.append("\"id\":\"").append(id).append("\",");
        result.append("\"registrationNumber\":\"").append(registrationNumber).append("\",");
        result.append("\"numOfDrivers\":\"").append(numOfDrivers).append("\",");
        result.append("\"capacity\":\"").append(capacity).append("\",");
        result.append("\"state\":\"").append(state).append("\",");
        result.append("\"currentCity\":\"").append(currentCity).append("\"}");
        return result.toString();
    }


    //todo: onetomany with drivers
}
