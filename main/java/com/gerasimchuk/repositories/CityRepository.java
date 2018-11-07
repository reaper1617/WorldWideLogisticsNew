package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;

import java.util.Collection;

/**
 * The interface City repository.
 */
public interface CityRepository {
    /**
     * Create city.
     *
     * @param name      the name
     * @param hasAgency the has agency
     * @return the city
     */
    City create(String name, boolean hasAgency);

    /**
     * Update city.
     *
     * @param id        the id
     * @param name      the name
     * @param hasAgency the has agency
     * @return the city
     */
    City update(int id,String name, boolean hasAgency);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    City getById(int id);

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    City getByName(String name);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<City> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);

    /**
     * Gets cities for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the cities for one page
     */
    Collection<City> getCitiesForOnePage(int size, int pageNumber);
}
