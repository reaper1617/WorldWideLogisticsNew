package com.gerasimchuk.services.impls;


import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.CargoStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.CargoRepository;
import com.gerasimchuk.repositories.CityRepository;
import com.gerasimchuk.repositories.RouteRepository;
import com.gerasimchuk.services.interfaces.CargoService;
import com.gerasimchuk.utils.PersonalNumberGenerator;
import com.gerasimchuk.validators.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation for {@link CargoService} interface
 *
 * @author Reaper
 * @version 1.0
 */


@Service
public class CargoServiceImpl implements CargoService {

    private CargoRepository cargoRepository;
    private RouteRepository routeRepository;
    private CityRepository cityRepository;
    private DTOValidator dtoValidator;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CargoServiceImpl.class);

    /**
     * Instantiates a new Cargo service.
     *
     * @param cargoRepository the cargo repository
     * @param routeRepository the route repository
     * @param cityRepository  the city repository
     * @param dtoValidator    the dto validator
     */
    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository, RouteRepository routeRepository, CityRepository cityRepository, DTOValidator dtoValidator) {
        this.cargoRepository = cargoRepository;
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
        this.dtoValidator = dtoValidator;
    }

    public boolean createCargo(CargoDTO cargoDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createCargo");
        if (!dtoValidator.validate(cargoDTO)) {
            LOGGER.error("Error: cargoDTO validation failed.");
            return false;
        }
        String personalNumber = PersonalNumberGenerator.generate(10);
        double weight = Double.parseDouble(cargoDTO.getWeight());
        CargoStatus status = CargoStatus.PREPARED;//getCargoStatusFromCargoDTO(cargoDTO);
        City cityFrom = cityRepository.getByName(cargoDTO.getCityFrom());
        City cityTo = cityRepository.getByName(cargoDTO.getCityTo());
        if (cityFrom == null) {
            LOGGER.error("Error: cityFrom is null.");
            return false;
        }
        if (cityTo == null) {
            LOGGER.error("Error: cityTo is null.");
            return false;
        }
        if (cityFrom.getName().equals(cityTo.getName())){
            LOGGER.error("Error: cityFrom and cityTo are equal.");
            return false;
        }
        Route route = routeRepository.getByCities(cityFrom,cityTo);
        if (route!=null){
            Cargo created = cargoRepository.create(personalNumber,cargoDTO.getName(),weight,status,route);
            LOGGER.info("Cargo id = " + created.getId() + ", name = " + created.getName() + "successfully created");
            return true;
        }
        else {
            LOGGER.error("Error: route between cityFrom = " + cityFrom.getName() + " and cityTo = " + cityTo.getName() + " doesn't exist.");
            return false;
        }
    }

    public boolean updateCargo(CargoDTO cargoDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateCargo");
        if (!dtoValidator.validate(cargoDTO)) {
            LOGGER.error("Error: cargoDTO validation failed.");
            return false;
        }
        if (cargoDTO.getId()!=null && cargoDTO.getId().length()!=0){
            int id = 0;
            try {
                id = Integer.parseInt(cargoDTO.getId());
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: cannot parse id from cargoDTO.");
                return false;
            }
            if (id == 0 ){
                LOGGER.error("Error: id value is 0");
                return false;
            }
            Cargo updated = cargoRepository.getById(id);
            if (updated.getOrder()!=null){
                LOGGER.error("Error: cannot change cargo which is already in order.");
                return false;
            }
            updateCargoWithFieldsFromDTO(updated, cargoDTO);
            LOGGER.info("Cargo id = " + updated.getId() + ", name = " + updated.getName() + "successfully updated.");
            return true;
        }
        LOGGER.error("Error: cargoId field in cargoDTO is null or empty");
        return false;
    }

    public boolean deleteCargo(int cargoId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteCargo");
        if (cargoId <= 0) {
            LOGGER.error("Error: cargoId is not valid.");
            return false;
        }
        Cargo deleted = cargoRepository.getById(cargoId);
        if (deleted == null) {
            LOGGER.error("Error: there is no cargo with this id in database.");
            return false;
        }
        if (!deleted.getStatus().equals(CargoStatus.PREPARED)){
            LOGGER.error("Error: cannot delete cargo which status is already " + deleted.getStatus());
            return false;
        }
        cargoRepository.remove(cargoId);
        LOGGER.info("Cargo id = " + deleted.getId() + ", name = " + deleted.getName() + " deleted successfully");
        return true;
    }

    @Override
    public UpdateMessageType deleteCargo(int cargoId, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteCargo");
        if (cargoId <= 0) {
            LOGGER.error("Error: cargoId is not valid.");
            return UpdateMessageType.ERROR_CAN_NOT_PARSE_CARGO_ID;
        }
        Cargo deleted = cargoRepository.getById(cargoId);
        if (deleted == null) {
            LOGGER.error("Error: there is no cargo with this id in database.");
            return UpdateMessageType.ERROR_NO_CARGO_WITH_THIS_ID;
        }
        if (!deleted.getStatus().equals(CargoStatus.PREPARED)){
            LOGGER.error("Error: cannot delete cargo which status is already " + deleted.getStatus());
            return UpdateMessageType.ERROR_CAN_NOT_DELETE_CARGO_WITH_SUCH_STATUS;
        }
        cargoRepository.remove(cargoId);
        LOGGER.info("Cargo id = " + deleted.getId() + ", name = " + deleted.getName() + " deleted successfully");
        return UpdateMessageType.CARGO_DELETED;
    }

    public Collection<Cargo> getAvailableCargos() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAvailableCargos");
        Collection<Cargo> cargos = cargoRepository.getAll();
        Collection<Cargo> availableCargos = new ArrayList<Cargo>();
        for (Cargo c: cargos){
            if (c.getOrder()!=null){
                LOGGER.info("Cargo " + c.getName() + " is not added into availableCargos list because it's already in order" + c.getOrder().getDescription());
            }
            else if (c.getStatus().equals(CargoStatus.SHIPPING) || c.getStatus().equals(CargoStatus.DELIVERED)){
                LOGGER.info("Cargo " + c.getName() + " is not added into availableCargos list because it has status = " + c.getStatus());
            }
            else {
                availableCargos.add(c);
                LOGGER.info("Cargo " + c.getName() + " added to availableCargos collection.");
            }
        }
        LOGGER.info("availableCargos collection: " + availableCargos + ", size = " + availableCargos.size());
        return availableCargos;
    }

    public CargoStatus getCargoStatusFromString(String status) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCargoStatusFromString");
        if (status == null || status.length()==0) {
            LOGGER.error("Error: status string is null or empty.");
            return null;
        }
        CargoStatus result = null;
        if (status.equals("PREPARED") ) result =  CargoStatus.PREPARED;
        if (status.equals("SHIPPING") ) result =  CargoStatus.SHIPPING;
        if (status.equals("LOADED") ) result = CargoStatus.LOADED;
        if (status.equals("DELIVERED") ) result = CargoStatus.DELIVERED;
        if (result != null){
            LOGGER.info("Cargo status is " + result);
        }
        else {
            LOGGER.error("Cargo status is null.");
        }
        return result;
    }

    @Override
    public UpdateMessageType updateCargoStatus(int cargoId, CargoStatus newStatus) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateCargoStatus");
        if (cargoId <=0){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateCargoStatus method: cargo id is not valid");
            return UpdateMessageType.ERROR_CARGO_ID_IS_NOT_VALID;
        }
        if (newStatus == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateCargoStatus method: new cargo status is null");
            return UpdateMessageType.ERROR_CARGO_STATUS_IS_NOT_VALID;
        }
        Cargo cargo = cargoRepository.getById(cargoId);
        if (cargo == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateCargoStatus method: no such cargo in database.");
            return UpdateMessageType.ERROR_NO_CARGO_WITH_THIS_ID;
        }
        CargoStatus currentStatus = cargo.getStatus();
        if (currentStatus.equals(CargoStatus.DELIVERED) && !newStatus.equals(CargoStatus.DELIVERED)){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateCargoStatus method: can not change status of cargo which is already delivered.");
            return UpdateMessageType.ERROR_CAN_NOT_CHANGE_CARGO_STATUS_FROM_DELIVERED_TO_ANOTHER;
        }
        cargoRepository.update(cargo.getId(),cargo.getPersonalNumber(),cargo.getName(),cargo.getWeight(),newStatus,cargo.getRoute());
        LOGGER.info("Class: " + this.getClass().getName() + " out from updateCargoStatus method: cargo status updated successfully.");
        return UpdateMessageType.CARGO_STATUS_UPDATED;
    }

    private CargoStatus getCargoStatusFromCargoDTO(CargoDTO cargoDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCargoStatusFromCargoDTO");
        CargoStatus result = null;
        if (cargoDTO.getStatus() != null) {
            if (cargoDTO.getStatus().equals("Delivered")) result = CargoStatus.DELIVERED;
            if (cargoDTO.getStatus().equals("Loaded")) result =  CargoStatus.LOADED;
            if (cargoDTO.getStatus().equals("Prepared")) result = CargoStatus.PREPARED;
            if (cargoDTO.getStatus().equals("Shipping")) result = CargoStatus.SHIPPING;
        }
        if (result != null){
            LOGGER.info("Cargo status is " + result);
        }
        else {
            LOGGER.error("Cargo status is null");
        }
        return result;
    }

    private void updateCargoWithFieldsFromDTO(Cargo updated, CargoDTO cargoDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateCargoWithFieldsFromDTO");
        if (updated == null){
            LOGGER.error("Error: cargo parameter is null.");
            return;
        }

        if (cargoDTO == null){
            LOGGER.error("Error: cargoDTO parameter is null.");
            return;
        }
        String personalNumber = null;
        String name= null;
        double weight = 0;
        City cityFrom = null;
        City cityTo = null;
        CargoStatus status = null;
        Route route = null;
        if (cargoDTO.getPersonalNumber()!=null && cargoDTO.getPersonalNumber().length()!=0) personalNumber = cargoDTO.getPersonalNumber();
        else personalNumber = updated.getPersonalNumber();
        LOGGER.info("New personalNumber = " + personalNumber);
        if (cargoDTO.getName()!=null && cargoDTO.getName().length()!=0) name = cargoDTO.getName();
        else name = updated.getName();
        LOGGER.info("New name = " + name);
        if (cargoDTO.getWeight()!=null && cargoDTO.getWeight().length()!=0) weight = Double.parseDouble(cargoDTO.getWeight());
        else weight = updated.getWeight();
        LOGGER.info("New weight = " + weight);
        if (cargoDTO.getCityFrom()!=null
                && cargoDTO.getCityFrom().length()!=0
                && !cargoDTO.getCityFrom().equals("No cities available")) cityFrom = cityRepository.getByName(cargoDTO.getCityFrom());
        else cityFrom = updated.getRoute().getCityFrom();
        LOGGER.info("New cityFrom = " + cityFrom.getName());
        if (cargoDTO.getCityTo()!=null
                && cargoDTO.getCityTo().length()!=0
                && !cargoDTO.getCityTo().equals("No cities available")) cityTo = cityRepository.getByName(cargoDTO.getCityTo());
        else cityTo = updated.getRoute().getCityTo();
        LOGGER.info("New cityTo = " + cityTo.getName());
        if (cargoDTO.getStatus()!=null && cargoDTO.getStatus().length()!=0 && !cargoDTO.getStatus().equals("Not selected")) status = getCargoStatusFromCargoDTO(cargoDTO);
        else status = updated.getStatus();
        LOGGER.info("New status = " + status);
        if (cargoDTO.getCityFrom()!=null
                && cargoDTO.getCityFrom().length()!=0
                && !cargoDTO.getCityFrom().equals("Not selected")
                && !cargoDTO.getCityFrom().equals("No cities available")
                && cargoDTO.getCityTo()!=null
                && cargoDTO.getCityTo().length()!=0
                && !cargoDTO.getCityTo().equals("Not selected")
                && !cargoDTO.getCityTo().equals("No cities available")) {
            cityFrom = cityRepository.getByName(cargoDTO.getCityFrom());
            cityTo = cityRepository.getByName(cargoDTO.getCityTo());
            route = routeRepository.getByCities(cityFrom, cityTo);
        }
        else route = updated.getRoute();
        LOGGER.info("New route: cityFrom = " + route.getCityFrom() + ", cityTo = " + route.getCityTo() + ", distance = "+ route.getDistance());
        cargoRepository.update(updated.getId(),personalNumber,name,weight,status,route);
        LOGGER.info("Cargo fields successfully updated with cargoDTO values.");
    }
}
