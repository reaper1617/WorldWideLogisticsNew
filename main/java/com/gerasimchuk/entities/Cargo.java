package com.gerasimchuk.entities;

import com.gerasimchuk.enums.CargoStatus;

import javax.persistence.*;

/**
 * The type Cargo.
 */
@Entity(name = "Cargos")
@Table(name = "cargos",schema = "mywwldatabase", catalog = "")
public class Cargo implements Comparable<Cargo> {
    private int id;
    private String personalNumber;
    private String name;
    private double weight;
    private CargoStatus status;
    private Route route;
    private Order order;

    /**
     * Instantiates a new Cargo.
     */
    public Cargo() {
    }

    /**
     * Instantiates a new Cargo.
     *
     * @param personalNumber the personal number
     * @param name           the name
     * @param weight         the weight
     * @param status         the status
     * @param route          the route
     */
    public Cargo(String personalNumber, String name, double weight, CargoStatus status, Route route) {
        this.personalNumber = personalNumber;
        this.name = name;
        this.weight = weight;
        this.status = status;
        this.route = route;
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
     * Gets personal number.
     *
     * @return the personal number
     */
    @Column(name = "personal_number", nullable = false, unique = true)
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
     * Gets name.
     *
     * @return the name
     */
    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    @Column(name = "weight", nullable = false)
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public CargoStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(CargoStatus status) {
        this.status = status;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    public Route getRoute() {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Gets order.
     *
     * @return the order
     */
    @ManyToOne
    @JoinColumn(name = "assigned_order_id")
    public Order getOrder() {
        return order;
    }

    /**
     * Sets order.
     *
     * @param order the order
     */
    public void setOrder(Order order) {
        this.order = order;
    }


    public int compareTo(Cargo o) {
        if (this.id == o.getId()) return 0;
        else if (this.id > o.getId()) return 1;
        else return -1;
    }
}
