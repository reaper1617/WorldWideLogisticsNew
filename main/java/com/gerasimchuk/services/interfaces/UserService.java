package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.AdminDTO;
import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.ManagerDTO;
import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.utils.ReturnValuesContainer;

import java.util.Collection;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Create driver update message type.
     *
     * @param driverDTO the driver dto
     * @return the update message type
     */
    UpdateMessageType createDriver(DriverDTO driverDTO);

    /**
     * Create driver return values container.
     *
     * @param driverDTO the driver dto
     * @param val       the val
     * @return the return values container
     */
    ReturnValuesContainer<User> createDriver(DriverDTO driverDTO, int val);

    /**
     * Create manager update message type.
     *
     * @param managerDTO the manager dto
     * @return the update message type
     */
    UpdateMessageType createManager(ManagerDTO managerDTO);

    /**
     * Create manager return values container.
     *
     * @param managerDTO the manager dto
     * @param val        the val
     * @return the return values container
     */
    ReturnValuesContainer<User> createManager(ManagerDTO managerDTO, int val);

    /**
     * Create admin update message type.
     *
     * @param adminDTO the admin dto
     * @return the update message type
     */
    UpdateMessageType createAdmin(AdminDTO adminDTO);

    /**
     * Create admin return values container.
     *
     * @param adminDTO the admin dto
     * @param val      the val
     * @return the return values container
     */
    ReturnValuesContainer<User> createAdmin(AdminDTO adminDTO, int val);


    /**
     * Update driver update message type.
     *
     * @param driverDTO the driver dto
     * @return the update message type
     */
    UpdateMessageType updateDriver(DriverDTO driverDTO);

    /**
     * Update manager update message type.
     *
     * @param managerDTO the manager dto
     * @return the update message type
     */
    UpdateMessageType updateManager(ManagerDTO managerDTO);

    /**
     * Update admin update message type.
     *
     * @param adminDTO the admin dto
     * @return the update message type
     */
    UpdateMessageType updateAdmin(AdminDTO adminDTO);

    /**
     * Delete driver update message type.
     *
     * @param userId the user id
     * @return the update message type
     */
    UpdateMessageType deleteDriver(int userId);

    /**
     * Delete manager update message type.
     *
     * @param userId the user id
     * @return the update message type
     */
    UpdateMessageType deleteManager(int userId);

    /**
     * Delete admin update message type.
     *
     * @param userId the user id
     * @return the update message type
     */
    UpdateMessageType deleteAdmin(int userId);


    /**
     * Gets all drivers.
     *
     * @return the all drivers
     */
    Collection<User> getAllDrivers();

    /**
     * Gets free drivers.
     *
     * @return the free drivers
     */
    Collection<User> getFreeDrivers();

    /**
     * Gets roles.
     *
     * @return the roles
     */
    Collection<UserRole> getRoles();

    /**
     * Create user update message type.
     *
     * @param userDTO the user dto
     * @return the update message type
     */
    UpdateMessageType createUser(UserDTO userDTO);

    /**
     * Create user return values container.
     *
     * @param userDTO the user dto
     * @param val     the val
     * @return the return values container
     */
    ReturnValuesContainer<User> createUser(UserDTO userDTO, int val);

    /**
     * Update user update message type.
     *
     * @param userDTO the user dto
     * @return the update message type
     */
    UpdateMessageType updateUser(UserDTO userDTO);

    /**
     * Delete user update message type.
     *
     * @param id the id
     * @return the update message type
     */
    UpdateMessageType deleteUser(int id);



//    Collection<Manager> getAllManagers();
//    Collection<Admin> getAllAdmins();

}
