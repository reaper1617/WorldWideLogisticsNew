package com.gerasimchuk.converters;

import com.gerasimchuk.dto.GoogleMarkerDTO;
import com.gerasimchuk.dto.OrderWithRouteDTO;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;

import java.util.Collection;

/**
 * The interface Order to dto converter.
 */
public interface OrderToDTOConverter {
    /**
     * Convert to dto with route order with route dto.
     *
     * @param order the order
     * @return the order with route dto
     * @throws RouteException the route exception
     */
    OrderWithRouteDTO convertToDTOWithRoute(Order order) throws RouteException;

    /**
     * Convert to google marker dto google marker dto.
     *
     * @param order the order
     * @return the google marker dto
     * @throws RouteException the route exception
     */
    GoogleMarkerDTO convertToGoogleMarkerDto(Order order) throws RouteException;

    /**
     * Convert to dto with route collection collection.
     *
     * @param orders the orders
     * @return the collection
     * @throws RouteException the route exception
     */
    Collection<OrderWithRouteDTO> convertToDTOWithRouteCollection(Collection<Order> orders) throws RouteException;
}
