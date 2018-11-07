package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.OrderStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.exceptions.driverexceptions.TooManyHoursWorkedForOrderException;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.utils.ReturnValuesContainer;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Order Service
 *
 * @author Reaper
 * @version 1.0
 */
public interface OrderService {

    /**
     * Gets chosen cargos.
     *
     * @param orderDTO the order dto
     * @return the chosen cargos
     */
    Collection<Cargo> getChosenCargos(OrderDTO orderDTO);

    /**
     * Gets available trucks.
     *
     * @param orderDTO the order dto
     * @return the available trucks
     * @throws RouteException the route exception
     */
    Collection<Truck> getAvailableTrucks(OrderDTO orderDTO) throws RouteException;

    /**
     * Gets order route.
     *
     * @param orderDTO the order dto
     * @param truck    the truck
     * @return the order route
     * @throws RouteException the route exception
     */
    List<City> getOrderRoute(OrderDTO orderDTO, Truck truck) throws RouteException;
//    Map<Order, Collection<City>> getRoutes(Collection<Order> orders);
//    Collection<Truck> getAvailableTrucks(List<Cargo> cargosInOrder);

    /**
     * Check if drivers hours worked over limit return values container.
     *
     * @param orderExecutingTime the order executing time
     * @param date               the date
     * @param driversInTruck     the drivers in truck
     * @return the return values container
     */
    ReturnValuesContainer<List<Driver>> checkIfDriversHoursWorkedOverLimit(double orderExecutingTime, Date date, Collection<Driver> driversInTruck);

    /**
     * Create order update message type.
     *
     * @param orderDTO the order dto
     * @return the update message type
     * @throws RouteException the route exception
     */
    UpdateMessageType createOrder(OrderDTO orderDTO) throws RouteException;

    /**
     * Create order return values container.
     *
     * @param orderDTO the order dto
     * @param val      the val
     * @return the return values container
     * @throws RouteException the route exception
     */
    ReturnValuesContainer<Order> createOrder(OrderDTO orderDTO, int val) throws RouteException;

    /**
     * Update order update message type.
     *
     * @param orderDTO the order dto
     * @return the update message type
     * @throws RouteException                      the route exception
     * @throws TooManyHoursWorkedForOrderException the too many hours worked for order exception
     */
    UpdateMessageType updateOrder(OrderDTO orderDTO) throws RouteException, TooManyHoursWorkedForOrderException;

    /**
     * Update order return values container.
     *
     * @param orderDTO the order dto
     * @param val      the val
     * @return the return values container
     * @throws RouteException                      the route exception
     * @throws TooManyHoursWorkedForOrderException the too many hours worked for order exception
     */
    ReturnValuesContainer<Order> updateOrder(OrderDTO orderDTO, int val) throws RouteException, TooManyHoursWorkedForOrderException;

    /**
     * Gets order status from string.
     *
     * @param status the status
     * @return the order status from string
     */
    OrderStatus getOrderStatusFromString(String status);

    /**
     * Are all cargos delivered boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean areAllCargosDelivered(Order order);

    /**
     * Delete order update message type.
     *
     * @param orderDTO the order dto
     * @return the update message type
     */
    UpdateMessageType deleteOrder(OrderDTO orderDTO);

    /**
     * Delete order update message type.
     *
     * @param orderId the order id
     * @return the update message type
     */
    UpdateMessageType deleteOrder(int orderId);

    /**
     * Update order status update message type.
     *
     * @param orderId   the order id
     * @param newStatus the new status
     * @return the update message type
     */
    UpdateMessageType updateOrderStatus(int orderId, OrderStatus newStatus);

    /**
     * Gets executing time.
     *
     * @param orderDTO the order dto
     * @return the executing time
     * @throws RouteException the route exception
     */
    double getExecutingTime(OrderDTO orderDTO) throws RouteException;
}
