package com.gerasimchuk.converters;

import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Truck;

import java.util.List;

/**
 * The interface Truck to dto converter.
 */
public interface TruckToDTOConverter {
    /**
     * Convert truck dto.
     *
     * @param truck the truck
     * @return the truck dto
     */
    TruckDTO convert(Truck truck);

    /**
     * Convert list.
     *
     * @param trucks the trucks
     * @return the list
     */
    List<TruckDTO> convert(List<Truck> trucks);
}
