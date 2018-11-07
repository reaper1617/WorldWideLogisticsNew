package com.gerasimchuk.converters;

import com.gerasimchuk.dto.RouteDTO;
import com.gerasimchuk.entities.Route;

import java.util.List;

/**
 * The interface Route to dto converter.
 */
public interface RouteToDTOConverter {
    /**
     * Convert route dto.
     *
     * @param route the route
     * @return the route dto
     */
    RouteDTO convert(Route route);

    /**
     * Convert list.
     *
     * @param routes the routes
     * @return the list
     */
    List<RouteDTO> convert(List<Route> routes);
}
