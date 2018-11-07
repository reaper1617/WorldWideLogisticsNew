package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.utils.ReturnValuesContainer;

import java.util.Collection;

/**
 * Cargo Service
 *
 * @author Reaper
 * @version 1.0
 */
public interface CargoService {

    /**
     * Create cargo boolean.
     *
     * @param cargoDTO the cargo dto
     * @return the boolean
     */
    boolean createCargo(CargoDTO cargoDTO);

    /**
     * Update cargo boolean.
     *
     * @param cargoDTO the cargo dto
     * @return the boolean
     */
//ReturnValuesContainer<Cargo> createCargo(CargoDTO cargoDTO, int val);
    boolean updateCargo(CargoDTO cargoDTO);

    /**
     * Delete cargo boolean.
     *
     * @param cargoId the cargo id
     * @return the boolean
     */
    boolean deleteCargo(int cargoId);

    /**
     * Delete cargo update message type.
     *
     * @param cargoId the cargo id
     * @param val     the val
     * @return the update message type
     */
    UpdateMessageType deleteCargo(int cargoId, int val);

    /**
     * Gets available cargos.
     *
     * @return the available cargos
     */
    Collection<Cargo> getAvailableCargos();

    /**
     * Gets cargo status from string.
     *
     * @param status the status
     * @return the cargo status from string
     */
    CargoStatus getCargoStatusFromString(String status);

    /**
     * Update cargo status update message type.
     *
     * @param cargoId   the cargo id
     * @param newStatus the new status
     * @return the update message type
     */
    UpdateMessageType updateCargoStatus(int cargoId, CargoStatus newStatus);
}
