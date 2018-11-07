package com.gerasimchuk.utils;

import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderStatus;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Order with route.
 */
public class OrderWithRoute {
    private int id;
    private String personalNumber;
    private String description;
    private String date;
    private OrderStatus status;
    private Truck assignedTruck;
    private Set<Cargo> cargosInOrder;
    private List<City> route;

    /**
     * Instantiates a new Order with route.
     *
     * @param id             the id
     * @param personalNumber the personal number
     * @param description    the description
     * @param date           the date
     * @param status         the status
     * @param assignedTruck  the assigned truck
     * @param cargosInOrder  the cargos in order
     * @param route          the route
     */
    public OrderWithRoute(int id, String personalNumber, String description, String date, OrderStatus status, Truck assignedTruck, LinkedHashSet<Cargo> cargosInOrder, List<City> route) {
        this.id = id;
        this.personalNumber = personalNumber;
        this.description = description;
        this.date = date;
        this.status = status;
        this.assignedTruck = assignedTruck;
        this.cargosInOrder = cargosInOrder;
        this.route = route;
    }

    /**
     * Instantiates a new Order with route.
     *
     * @param order the order
     * @param route the route
     */
    public OrderWithRoute(Order order, List<City> route){
        this.id = order.getId();
        this.personalNumber = order.getPersonalNumber();
        this.description = order.getDescription();
        this.date = order.getDate();
        this.status = order.getStatus();
        this.assignedTruck = order.getAssignedTruck();
        this.cargosInOrder = order.getCargosInOrder();
        this.route = route;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
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
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Gets assigned truck.
     *
     * @return the assigned truck
     */
    public Truck getAssignedTruck() {
        return assignedTruck;
    }

    /**
     * Sets assigned truck.
     *
     * @param assignedTruck the assigned truck
     */
    public void setAssignedTruck(Truck assignedTruck) {
        this.assignedTruck = assignedTruck;
    }

    /**
     * Gets cargos in order.
     *
     * @return the cargos in order
     */
    public Set<Cargo> getCargosInOrder() {
        return cargosInOrder;
    }

    /**
     * Sets cargos in order.
     *
     * @param cargosInOrder the cargos in order
     */
    public void setCargosInOrder(Set<Cargo> cargosInOrder) {
        this.cargosInOrder = cargosInOrder;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    public List<City> getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(List<City> route) {
        this.route = route;
    }
}
