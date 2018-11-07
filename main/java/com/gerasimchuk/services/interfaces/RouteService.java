package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.RouteDTO;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.utils.ReturnValuesContainer;

/**
 * The interface Route service.
 */
public interface RouteService {
    /**
     * Create route boolean.
     *
     * @param routeDTO the route dto
     * @return the boolean
     */
    boolean createRoute(RouteDTO routeDTO);

    /**
     * Create route return values container.
     *
     * @param routeDTO the route dto
     * @param val      the val
     * @return the return values container
     */
    ReturnValuesContainer<Route> createRoute(RouteDTO routeDTO, int val);

    /**
     * Update route boolean.
     *
     * @param routeDTO the route dto
     * @return the boolean
     */
    boolean updateRoute(RouteDTO routeDTO);

    /**
     * Update route return values container.
     *
     * @param routeDTO the route dto
     * @param val      the val
     * @return the return values container
     */
    ReturnValuesContainer<Route>  updateRoute(RouteDTO routeDTO, int val);

    /**
     * Delete route boolean.
     *
     * @param routeId the route id
     * @return the boolean
     * @throws Exception the exception
     */
    boolean deleteRoute(int routeId) throws Exception;

    /**
     * Delete route update message type.
     *
     * @param routeId the route id
     * @param val     the val
     * @return the update message type
     */
    UpdateMessageType deleteRoute(int routeId, int val);
}
