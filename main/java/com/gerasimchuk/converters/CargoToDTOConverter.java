package com.gerasimchuk.converters;

import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;

import java.util.List;

/**
 * The interface Cargo to dto converter.
 */
public interface CargoToDTOConverter {
    /**
     * Convert cargo dto.
     *
     * @param cargo the cargo
     * @return the cargo dto
     */
    CargoDTO convert(Cargo cargo);

    /**
     * Convert list.
     *
     * @param cargoList the cargo list
     * @return the list
     */
    List<CargoDTO> convert(List<Cargo> cargoList);
}
