package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Admin;

import java.util.Collection;

/**
 * The interface Admin repository.
 */
public interface AdminRepository {
    /**
     * Create admin.
     *
     * @return the admin
     */
    Admin create();

    /**
     * Update admin.
     *
     * @param id the id
     * @return the admin
     */
    Admin update(int id);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Admin getById(int id);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<Admin> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);
}
