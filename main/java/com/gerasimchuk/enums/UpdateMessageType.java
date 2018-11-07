package com.gerasimchuk.enums;

/**
 * The enum Update message type.
 */
public enum UpdateMessageType {

    /**
     * successful action messages
     */
    ORDER_CREATED,
    /**
     * Order edited update message type.
     */
    ORDER_EDITED,
    /**
     * Order deleted update message type.
     */
    ORDER_DELETED,

    /**
     * Driver created update message type.
     */
    DRIVER_CREATED,
    /**
     * Driver edited update message type.
     */
    DRIVER_EDITED,
    /**
     * Driver deleted update message type.
     */
    DRIVER_DELETED,

    /**
     * Manager created update message type.
     */
    MANAGER_CREATED,
    /**
     * Manager edited update message type.
     */
    MANAGER_EDITED,
    /**
     * Manager deleted update message type.
     */
    MANAGER_DELETED,

    /**
     * Admin created update message type.
     */
    ADMIN_CREATED,
    /**
     * Admin edited update message type.
     */
    ADMIN_EDITED,
    /**
     * Admin deleted update message type.
     */
    ADMIN_DELETED,

    /**
     * User created update message type.
     */
    USER_CREATED,
    /**
     * User edited update message type.
     */
    USER_EDITED,
    /**
     * User deleted update message type.
     */
    USER_DELETED,


    /**
     * Truck created update message type.
     */
    TRUCK_CREATED,
    /**
     * Truck edited update message type.
     */
    TRUCK_EDITED,
    /**
     * Truck deleted update message type.
     */
    TRUCK_DELETED,


    /**
     * Truck fields updated update message type.
     */
    TRUCK_FIELDS_UPDATED,

    /**
     * error messages will be here
     */
// todo: add error messages

    // orders
    ORDER_STATUS_UPDATED,
    /**
     * Error order dto is not valid update message type.
     */
    ERROR_ORDER_DTO_IS_NOT_VALID,

    /**
     * Error can not parse order id update message type.
     */
    ERROR_CAN_NOT_PARSE_ORDER_ID,


    /**
     * Error can not create order update message type.
     */
    ERROR_CAN_NOT_CREATE_ORDER,

    /**
     * Error can not update order update message type.
     */
    ERROR_CAN_NOT_UPDATE_ORDER,

    /**
     * Error no order with this id update message type.
     */
    ERROR_NO_ORDER_WITH_THIS_ID,

    /**
     * Error can not delete order with such status update message type.
     */
    ERROR_CAN_NOT_DELETE_ORDER_WITH_SUCH_STATUS,
    /**
     * Error order id is not valid update message type.
     */
    ERROR_ORDER_ID_IS_NOT_VALID,
    /**
     * Error order status is null update message type.
     */
    ERROR_ORDER_STATUS_IS_NULL,
    /**
     * Error can not update order status to lower update message type.
     */
    ERROR_CAN_NOT_UPDATE_ORDER_STATUS_TO_LOWER,
    /**
     * Error can not update status for order with undelivered cargos update message type.
     */
    ERROR_CAN_NOT_UPDATE_STATUS_FOR_ORDER_WITH_UNDELIVERED_CARGOS,
    /**
     * Error assigned truck is null for executing order update message type.
     */
    ERROR_ASSIGNED_TRUCK_IS_NULL_FOR_EXECUTING_ORDER,
    /**
     * Error can not get order route update message type.
     */
    ERROR_CAN_NOT_GET_ORDER_ROUTE,

    /**
     * Error no truck with this id update message type.
     */
// trucks
    ERROR_NO_TRUCK_WITH_THIS_ID,

    /**
     * Error truck has no assigned drivers to execute order update message type.
     */
    ERROR_TRUCK_HAS_NO_ASSIGNED_DRIVERS_TO_EXECUTE_ORDER,

    /**
     * Cargo created update message type.
     */
// cargos
    CARGO_CREATED,
    /**
     * Cargo deleted update message type.
     */
    CARGO_DELETED,
    /**
     * Cargo status updated update message type.
     */
    CARGO_STATUS_UPDATED,
    /**
     * Error can not parse cargo id update message type.
     */
    ERROR_CAN_NOT_PARSE_CARGO_ID,
    /**
     * Error cargo dto is not valid update message type.
     */
    ERROR_CARGO_DTO_IS_NOT_VALID,
    /**
     * Error no cargo with this id update message type.
     */
    ERROR_NO_CARGO_WITH_THIS_ID,
    /**
     * Error can not delete cargo with such status update message type.
     */
    ERROR_CAN_NOT_DELETE_CARGO_WITH_SUCH_STATUS,
    /**
     * Error cargo id is not valid update message type.
     */
    ERROR_CARGO_ID_IS_NOT_VALID,
    /**
     * Error cargo status is not valid update message type.
     */
    ERROR_CARGO_STATUS_IS_NOT_VALID,
    /**
     * Error can not change cargo status from delivered to another update message type.
     */
    ERROR_CAN_NOT_CHANGE_CARGO_STATUS_FROM_DELIVERED_TO_ANOTHER,

    /**
     * City deleted update message type.
     */
// cities
    CITY_DELETED,
    /**
     * Error can not parse city id update message type.
     */
    ERROR_CAN_NOT_PARSE_CITY_ID,
    /**
     * Error no city with this id update message type.
     */
    ERROR_NO_CITY_WITH_THIS_ID,
    /**
     * Error this city used in routes update message type.
     */
    ERROR_THIS_CITY_USED_IN_ROUTES,
    /**
     * Error no city with this name update message type.
     */
    ERROR_NO_CITY_WITH_THIS_NAME,


    /**
     * Driver status updated update message type.
     */
// drivers
    DRIVER_STATUS_UPDATED,
    /**
     * All drivers hours worked valid update message type.
     */
    ALL_DRIVERS_HOURS_WORKED_VALID,

    /**
     * Error can not parse driver id update message type.
     */
    ERROR_CAN_NOT_PARSE_DRIVER_ID,

    /**
     * Error driver hours worked over limit update message type.
     */
    ERROR_DRIVER_HOURS_WORKED_OVER_LIMIT,

    /**
     * Error driver dto is not valid update message type.
     */
    ERROR_DRIVER_DTO_IS_NOT_VALID,

    /**
     * Error can not update driver update message type.
     */
    ERROR_CAN_NOT_UPDATE_DRIVER,

    /**
     * Error user is not a driver update message type.
     */
    ERROR_USER_IS_NOT_A_DRIVER,

    /**
     * Error driver id is not valid update message type.
     */
    ERROR_DRIVER_ID_IS_NOT_VALID,
    /**
     * Error driver status is null update message type.
     */
    ERROR_DRIVER_STATUS_IS_NULL,
    /**
     * Error no driver with this id update message type.
     */
    ERROR_NO_DRIVER_WITH_THIS_ID,
    /**
     * Error driver has no assigned order update message type.
     */
    ERROR_DRIVER_HAS_NO_ASSIGNED_ORDER,
    /**
     * Error driver has unexecuted assigned order update message type.
     */
    ERROR_DRIVER_HAS_UNEXECUTED_ASSIGNED_ORDER,
    /**
     * Error can not count order executing time update message type.
     */
    ERROR_CAN_NOT_COUNT_ORDER_EXECUTING_TIME,

    //managers

    /**
     * Error manager dto is not valid update message type.
     */
    ERROR_MANAGER_DTO_IS_NOT_VALID,
    /**
     * Error can not update manager update message type.
     */
    ERROR_CAN_NOT_UPDATE_MANAGER,
    /**
     * Error user is not a manager update message type.
     */
    ERROR_USER_IS_NOT_A_MANAGER,
    // admins

    /**
     * Error admin dto is not valid update message type.
     */
    ERROR_ADMIN_DTO_IS_NOT_VALID,
    /**
     * Error can not update admin update message type.
     */
    ERROR_CAN_NOT_UPDATE_ADMIN,
    /**
     * Error user is not an admin update message type.
     */
    ERROR_USER_IS_NOT_AN_ADMIN,
    // users

    /**
     * Error no user with this id update message type.
     */
    ERROR_NO_USER_WITH_THIS_ID,

    /**
     * Error user dto is not valid update message type.
     */
    ERROR_USER_DTO_IS_NOT_VALID,
    /**
     * Error can not parse user id update message type.
     */
    ERROR_CAN_NOT_PARSE_USER_ID,

    /**
     * Error can not create user update message type.
     */
    ERROR_CAN_NOT_CREATE_USER,
    /**
     * Error can not edit user update message type.
     */
    ERROR_CAN_NOT_EDIT_USER,

    /**
     * Error can not delete user update message type.
     */
    ERROR_CAN_NOT_DELETE_USER,
    // trucks

    /**
     * Error can not parse num of drivers and capacity update message type.
     */
    ERROR_CAN_NOT_PARSE_NUM_OF_DRIVERS_AND_CAPACITY,
    /**
     * Error truck dto is not valid update message type.
     */
    ERROR_TRUCK_DTO_IS_NOT_VALID,

    /**
     * Error can not parse truck id update message type.
     */
    ERROR_CAN_NOT_PARSE_TRUCK_ID,
    /**
     * Error can not parse num of drivers update message type.
     */
    ERROR_CAN_NOT_PARSE_NUM_OF_DRIVERS,
    /**
     * Error can not parse capacity update message type.
     */
    ERROR_CAN_NOT_PARSE_CAPACITY,


    /**
     * Error can not edit truck with assigned order update message type.
     */
    ERROR_CAN_NOT_EDIT_TRUCK_WITH_ASSIGNED_ORDER,

    /**
     * Error num of drivers to assign more than maximal for this truck update message type.
     */
    ERROR_NUM_OF_DRIVERS_TO_ASSIGN_MORE_THAN_MAXIMAL_FOR_THIS_TRUCK,

    /**
     * Error new num of drivers less than num of current assigned drivers update message type.
     */
    ERROR_NEW_NUM_OF_DRIVERS_LESS_THAN_NUM_OF_CURRENT_ASSIGNED_DRIVERS,
    /**
     * Error can not update truck update message type.
     */
    ERROR_CAN_NOT_UPDATE_TRUCK,


    /**
     * Error can not delete truck with exec order update message type.
     */
    ERROR_CAN_NOT_DELETE_TRUCK_WITH_EXEC_ORDER,

    // routes

    /**
     * Route created update message type.
     */
    ROUTE_CREATED,
    /**
     * Route edited update message type.
     */
    ROUTE_EDITED,
    /**
     * Route deleted update message type.
     */
    ROUTE_DELETED,

    /**
     * Error route dto is not valid update message type.
     */
    ERROR_ROUTE_DTO_IS_NOT_VALID,
    /**
     * Error route id is not valid update message type.
     */
    ERROR_ROUTE_ID_IS_NOT_VALID,

    /**
     * Error city from is null update message type.
     */
    ERROR_CITY_FROM_IS_NULL,
    /**
     * Error city to is null update message type.
     */
    ERROR_CITY_TO_IS_NULL,
    /**
     * Error city from equals city to update message type.
     */
    ERROR_CITY_FROM_EQUALS_CITY_TO,

    /**
     * Error can not parse distance value update message type.
     */
    ERROR_CAN_NOT_PARSE_DISTANCE_VALUE,
    /**
     * Error distance is not valid update message type.
     */
    ERROR_DISTANCE_IS_NOT_VALID,
    /**
     * Error no route with this id update message type.
     */
    ERROR_NO_ROUTE_WITH_THIS_ID,
    /**
     * Error no route between these cities update message type.
     */
    ERROR_NO_ROUTE_BETWEEN_THESE_CITIES,
    // for all

    /**
     * Error id is not valid update message type.
     */
    ERROR_ID_IS_NOT_VALID

}
