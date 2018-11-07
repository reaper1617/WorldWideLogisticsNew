package com.gerasimchuk.converters;

import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type City to dto converter.
 */
@Component
public class CityToDTOConverterImpl implements CityToDTOConverter {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CityToDTOConverterImpl.class);
    @Override
    public CityDTO convert(City city) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (city == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: city input value is null");
            return null;
        }
        String id = Integer.toString(city.getId());
        String name = city.getName();
        String hasAgency = Boolean.toString(city.isHasAgency());
        String[] driversInCity = null;
        String[] trucksInCity = null;
        if(!city.getDriversInCity().isEmpty()){
            Set<Driver> drivers = city.getDriversInCity();
            driversInCity = new String[drivers.size()];
            int counter = 0;
            for(Driver d: drivers){
                User u = d.getUser();
                driversInCity[counter] = u.getName() + " " + u.getMiddleName() + " " + u.getLastName();
                counter++;
            }
        }
        if (!city.getTrucksInCity().isEmpty()){
            Set<Truck> trucks = city.getTrucksInCity();
            trucksInCity = new String[trucks.size()];
            int counter = 0;
            for(Truck t: trucks){
                trucksInCity[counter] = t.getRegistrationNumber();
            }
        }
        CityDTO cityDTO = new CityDTO(id, name, hasAgency, driversInCity, trucksInCity);
        LOGGER.info("Class: " + this.getClass().getName() + " out from convert method: result dto: " + cityDTO);
        return cityDTO;
    }

    @Override
    public List<CityDTO> convert(List<City> cities) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (cities == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: cities list input value is null");
            return null;
        }
        List<CityDTO> cityDTOS = new ArrayList<CityDTO>();
        for(City c: cities){
            cityDTOS.add(convert(c));
        }
        LOGGER.info("Class: " + this.getClass().getName() + " out from convert method: result list: " + cityDTOS);
        return cityDTOS;
    }
}
