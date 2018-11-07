package com.gerasimchuk.entities;


import com.gerasimchuk.enums.OrderStatus;
import com.gerasimchuk.utils.JSONconvertable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * The type Order.
 */
@Entity(name = "Orders")
@Table(name = "orders", schema = "mywwldatabase", catalog = "")
public class Order implements JSONconvertable {
    private int id;
    private String personalNumber;
    private String description;
    private String date;
    private OrderStatus status;
    private Truck assignedTruck;
    private Set<Cargo> cargosInOrder = new LinkedHashSet<Cargo>();

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param personalNumber the personal number
     * @param description    the description
     * @param date           the date
     * @param status         the status
     * @param assignedTruck  the assigned truck
     */
    public Order(String personalNumber,
                 String description,
                 String date,
                 OrderStatus status,
                 Truck assignedTruck) {
        this.personalNumber = personalNumber;
        this.description = description;
        this.date = date;
        this.status = status;
        this.assignedTruck = assignedTruck;
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
     * Gets description.
     *
     * @return the description
     */
    @Column(name = "description", nullable = false)
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
    @Column(name = "date", nullable = false)
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
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
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
    @OneToOne
    @JoinColumn(name = "assigned_truck_id")
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
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
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


    @Override
    public String convertToJSONString() {
        // todo: add fields (assignedTruck, cargosInOrders) to result!
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"id\":").append("\"").append(id).append("\"").append(",");
        result.append("\"personalNumber\":").append("\"").append(personalNumber).append("\"").append(",");
        result.append("\"description\":").append("\"").append(description).append("\"").append(",");
        result.append("\"date\":").append("\"").append(date).append("\"").append(",");
        result.append("\"status\":").append("\"").append(status).append("\"").append("}");
        return result.toString();
    }
}
