package com.gerasimchuk.services.impls;


import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.CityRepository;
import com.gerasimchuk.services.interfaces.CityService;
import com.gerasimchuk.validators.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link CityService} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;
    private DTOValidator dtoValidator;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CityServiceImpl.class);

    /**
     * Instantiates a new City service.
     *
     * @param cityRepository the city repository
     * @param dtoValidator   the dto validator
     */
    @Autowired
    public CityServiceImpl(CityRepository cityRepository, DTOValidator dtoValidator) {
        this.cityRepository = cityRepository;
        this.dtoValidator = dtoValidator;
    }

    public boolean createCity(CityDTO cityDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createCity");
        if (!dtoValidator.validate(cityDTO)){
            LOGGER.error("Error: cityDTO validation failed.");
            return false;
        }
        boolean hasAgency = cityDTO.getHasAgency().equals("HAS");
        City city = cityRepository.create(cityDTO.getName(),hasAgency);
        LOGGER.info("City + " + city.getName() + " created successfully.");
        return true;
    }

    public boolean updateCity(CityDTO cityDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateCity");
        if (!dtoValidator.validate(cityDTO)) {
            LOGGER.error("Error: cityDTO validation failed.");
            return false;
        }
        int id = 0;
        try{
            id = Integer.parseInt(cityDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse city id.");
           return false;
        }
        if (id == 0) {
            LOGGER.error("Error: city id is 0.");
            return false;
        }
        City city = cityRepository.getById(id);
        if (city == null) {
            LOGGER.error("Error: no city with id = " + id + " in database");
            return false;
        }
        String newName = null;
        boolean newHasAgency = false;
        if (cityDTO.getName()!= null && cityDTO.getName().length()!=0) newName = cityDTO.getName();
        else newName = city.getName();
        LOGGER.info("New name = " + newName);
        if (cityDTO.getHasAgency()!= null
                && cityDTO.getHasAgency().length()!=0
                && !cityDTO.getHasAgency().equals("Not selected")) newHasAgency = cityDTO.getHasAgency().equals("HAS");
        else newHasAgency = city.isHasAgency();
        LOGGER.info("New hasAgency = " + newHasAgency);
        cityRepository.update(city.getId(),newName, newHasAgency);
        LOGGER.info("City fields successfully updated with cityDTO values.");
        return true;
    }

    public boolean deleteCity(int cityId) throws Exception {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteCity");
        if (cityId <= 0){
            LOGGER.error("Error: id value is invalid.(id = " + cityId + ")");
            return false;
        }
        City city = cityRepository.getById(cityId);
        if (city == null) {
            LOGGER.error("Error: no city with id = " + cityId + " in database");
            return false;
        }
        cityRepository.remove(cityId);
        LOGGER.info("City " + city.getName() + " successfully deleted.");
        return true;
    }

    @Override
    public UpdateMessageType deleteCity(int cityId, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteCity");
        if (cityId <= 0){
            LOGGER.error("Error: id value is invalid.(id = " + cityId + ")");
            return UpdateMessageType.ERROR_CAN_NOT_PARSE_CITY_ID;
        }
        City city = cityRepository.getById(cityId);
        if (city == null) {
            LOGGER.error("Error: no city with id = " + cityId + " in database");
            return UpdateMessageType.ERROR_NO_CITY_WITH_THIS_ID;
        }
        try {
            cityRepository.remove(cityId);
        }
        catch (Exception e){
            LOGGER.error("Class: " + this.getClass().getName() + " out from deleteCity method: catched exception: " + e.getMessage());
            e.printStackTrace();
            return UpdateMessageType.ERROR_THIS_CITY_USED_IN_ROUTES;
        }
        LOGGER.info("City " + city.getName() + " successfully deleted.");
        return UpdateMessageType.CITY_DELETED;
    }
}
