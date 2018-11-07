package com.gerasimchuk.converters;

import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.entities.City;

import java.util.List;

/**
 * The interface City to dto converter.
 */
public interface CityToDTOConverter {
    /**
     * Convert city dto.
     *
     * @param city the city
     * @return the city dto
     */
    CityDTO convert(City city);

    /**
     * Convert list.
     *
     * @param cities the cities
     * @return the list
     */
    List<CityDTO> convert(List<City> cities);

}
