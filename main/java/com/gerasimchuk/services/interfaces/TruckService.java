package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.UpdateMessageType;

import java.util.Collection;

/**
 * Truck Service
 *
 * @author Reaper
 * @version 1.0
 */
public interface TruckService {

    /**
     * Create truck update message type.
     *
     * @param truckDTO the truck dto
     * @return the update message type
     */
    UpdateMessageType createTruck(TruckDTO truckDTO);

    /**
     * Update truck update message type.
     *
     * @param truckDTO the truck dto
     * @return the update message type
     */
    UpdateMessageType updateTruck(TruckDTO truckDTO);

    /**
     * Delete truck update message type.
     *
     * @param id the id
     * @return the update message type
     */
    UpdateMessageType deleteTruck(int id);

    /**
     * Gets free trucks.
     *
     * @return the free trucks
     */
    Collection<Truck> getFreeTrucks();
}
