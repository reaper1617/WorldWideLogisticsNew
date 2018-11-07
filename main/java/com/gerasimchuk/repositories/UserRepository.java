package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Admin;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;

import java.util.Collection;

/**
 * The interface User repository.
 */
public interface UserRepository {

    /**
     * Create user.
     *
     * @param name           the name
     * @param middleName     the middle name
     * @param lastName       the last name
     * @param personalNumber the personal number
     * @param password       the password
     * @param driver         the driver
     * @param manager        the manager
     * @param admin          the admin
     * @return the user
     */
    User create(String name,
                String middleName,
                String lastName,
                String personalNumber,
                String password,
                Driver driver,
                Manager manager,
                Admin admin);

    /**
     * Update user.
     *
     * @param id             the id
     * @param name           the name
     * @param middleName     the middle name
     * @param lastName       the last name
     * @param personalNumber the personal number
     * @param password       the password
     * @param driver         the driver
     * @param manager        the manager
     * @param admin          the admin
     * @return the user
     */
    User update(int id,
                String name,
                String middleName,
                String lastName,
                String personalNumber,
                String password,
                Driver driver,
                Manager manager,
                Admin admin);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    User getById(int id);

    /**
     * Gets by driver id.
     *
     * @param driverId the driver id
     * @return the by driver id
     */
    User getByDriverId(int driverId);

    /**
     * Gets by manager id.
     *
     * @param managerId the manager id
     * @return the by manager id
     */
    User getByManagerId(int managerId);

    /**
     * Gets by admin id.
     *
     * @param adminId the admin id
     * @return the by admin id
     */
    User getByAdminId(int adminId);

    /**
     * Gets by personal number.
     *
     * @param personalNumber the personal number
     * @return the by personal number
     */
    User getByPersonalNumber(String personalNumber);

    /**
     * Gets all.
     *
     * @return the all
     */
    Collection<User> getAll();

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(int id);


    /**
     * Gets users for one page.
     *
     * @param size       the size
     * @param pageNumber the page number
     * @return the users for one page
     */
    Collection<User> getUsersForOnePage(int size, int pageNumber);
}
