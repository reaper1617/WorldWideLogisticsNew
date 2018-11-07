package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;

import java.util.Collection;

/**
 * The interface Route repository.
 */
public interface RouteRepository {
    /**
     * Create route.
     *
     * @param cityFrom the city from
     * @param cityTo   the city to
     * @param distance the distance
     * @return the route
     */
    Route create(City cityFrom, City cityTo, double distance);

    /**
     * Update route.
     *
     * @param id       the id
     * @param cityFrom the city from
     * @param cityTo   the city to
     * @param distance the distance
     * @return the route
     */
    Route update(int id,City cityFrom, City cityTo, double distance);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Route getById(int id);

    /**
     * Gets by cities.
     *
     * @param cityFrom the city from
     * @param cityTo   the city to
     * @return the by cities
     */
    Route getByCities(City cityFrom, City cityTo);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Route> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);

    /**
     * Gets routes for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the routes for one page
     */
//
    Collection<Route> getRoutesForOnePage(int size, int pageNumber);
}
