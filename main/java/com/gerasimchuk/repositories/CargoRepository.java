package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.CargoStatus;

import java.util.Collection;

/**
 * The interface Cargo repository.
 */
public interface CargoRepository {
    /**
     * Create cargo.
     *
     * @param personalNumber the personal number
     * @param name           the name
     * @param weight         the weight
     * @param status         the status
     * @param route          the route
     * @return the cargo
     */
    Cargo create(String personalNumber, String name, double weight, CargoStatus status, Route route);

    /**
     * Update cargo.
     *
     * @param id             the id
     * @param personalNumber the personal number
     * @param name           the name
     * @param weight         the weight
     * @param status         the status
     * @param route          the route
     * @return the cargo
     */
    Cargo update(int id,String personalNumber, String name, double weight, CargoStatus status, Route route);

    /**
     * Update cargo.
     *
     * @param id             the id
     * @param personalNumber the personal number
     * @param name           the name
     * @param weight         the weight
     * @param status         the status
     * @param route          the route
     * @param order          the order
     * @return the cargo
     */
    Cargo update(int id,String personalNumber, String name, double weight, CargoStatus status, Route route, Order order);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Cargo getById(int id);

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    Cargo getByName(String name);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Cargo> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);

    /**
     * Gets cargos for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the cargos for one page
     */
    Collection<Cargo> getCargosForOnePage(int size, int pageNumber);
}
