package com.gerasimchuk.services.interfaces;

import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.enums.UpdateMessageType;


/**
 * The interface City service.
 */
public interface CityService {
    /**
     * Create city boolean.
     *
     * @param cityDTO the city dto
     * @return the boolean
     */
    boolean createCity(CityDTO cityDTO);

    /**
     * Update city boolean.
     *
     * @param cityDTO the city dto
     * @return the boolean
     */
    boolean updateCity(CityDTO cityDTO);

    /**
     * Delete city boolean.
     *
     * @param cityId the city id
     * @return the boolean
     * @throws Exception the exception
     */
    boolean deleteCity(int cityId) throws Exception;

    /**
     * Delete city update message type.
     *
     * @param cityId the city id
     * @param val    the val
     * @return the update message type
     */
    UpdateMessageType deleteCity(int cityId, int val);
}
