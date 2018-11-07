package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Manager;

import java.util.Collection;

/**
 * The interface Manager repository.
 */
public interface ManagerRepository {

    /**
     * Create manager.
     *
     * @return the manager
     */
    Manager create();

    /**
     * Update manager.
     *
     * @param id the id
     * @return the manager
     */
    Manager update(int id);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Manager getById(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Manager> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);
}
