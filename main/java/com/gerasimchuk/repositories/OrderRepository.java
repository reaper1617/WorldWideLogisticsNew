package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderStatus;

import java.util.Collection;

/**
 * The interface Order repository.
 */
public interface OrderRepository {
    /**
     * Create order.
     *
     * @param personalNumber the personal number
     * @param description    the description
     * @param date           the date
     * @param status         the status
     * @param assignedTruck  the assigned truck
     * @return the order
     */
    Order create(String personalNumber,
                 String description,
                 String date,
                 OrderStatus status,
                 Truck assignedTruck);

    /**
     * Update order.
     *
     * @param id             the id
     * @param personalNumber the personal number
     * @param description    the description
     * @param date           the date
     * @param status         the status
     * @param assignedTruck  the assigned truck
     * @return the order
     */
    Order update(int id,
                 String personalNumber,
                 String description,
                 String date,
                 OrderStatus status,
                 Truck assignedTruck);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Order getById(int id);

    /**
     * Gets by personal number.
     *
     * @param personalNumber the personal number
     * @return the by personal number
     */
    Order getByPersonalNumber(String personalNumber);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Order> getAll();

    /**
     * Gets orders for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the orders for one page
     */
    Collection<Order> getOrdersForOnePage(int size, int pageNumber);

    /**
     * Gets top non executed orders.
     *
     * @param size the size
     * @return the top non executed orders
     */
    Collection<Order> getTopNonExecutedOrders(int size);

    /**
     * Gets last orders.
     *
     * @param numberOfOrders the number of orders
     * @return the last orders
     */
    Collection<Order> getLastOrders(int numberOfOrders);

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);
}
