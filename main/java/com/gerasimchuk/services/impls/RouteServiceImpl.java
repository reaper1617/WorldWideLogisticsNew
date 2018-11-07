package com.gerasimchuk.services.impls;

import com.gerasimchuk.dto.RouteDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.CityRepository;
import com.gerasimchuk.repositories.RouteRepository;
import com.gerasimchuk.services.interfaces.RouteService;
import com.gerasimchuk.utils.ReturnValuesContainer;
import com.gerasimchuk.validators.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link RouteService} interface
 *
 * @author Reaper
 * @version 1.0
 */


@Service
public class RouteServiceImpl implements RouteService {

    private RouteRepository routeRepository;
    private CityRepository cityRepository;
    private DTOValidator dtoValidator;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RouteServiceImpl.class);

    /**
     * Instantiates a new Route service.
     *
     * @param routeRepository the route repository
     * @param cityRepository  the city repository
     * @param dtoValidator    the dto validator
     */
    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, CityRepository cityRepository, DTOValidator dtoValidator) {
        this.routeRepository = routeRepository;
        this.cityRepository = cityRepository;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public boolean createRoute(RouteDTO routeDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createRoute");
        if (!dtoValidator.validate(routeDTO)) {
            LOGGER.error("Error: routeDTO is not valid.");
            return false;
        }
        String cityFrom = routeDTO.getCityFrom();
        String cityTo = routeDTO.getCityTo();
        if (cityFrom == null){
            LOGGER.error("Error: cityFrom is null.");
            return false;
        }
        if (cityTo == null){
            LOGGER.error("Error: cityTo is null.");
            return false;
        }
        if (cityFrom.equals(cityTo)){
            LOGGER.error("Error: cityFrom equals cityTo.");
            return false;
        }
        City cFrom = cityRepository.getByName(cityFrom);
        City cTo = cityRepository.getByName(cityTo);
        double distance = 0;
        try {
            distance = Double.parseDouble(routeDTO.getDistance());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse distance value.");
            return false;
        }
        if (distance == 0) {
            LOGGER.error("Error: distance value is not valid (distance =0).");
            return false;
        }
        Route r = routeRepository.create(cFrom,cTo,distance);
        LOGGER.info("Route with cityFrom = " + r.getCityFrom() + ", cityTo" + r.getCityTo() + " and distance = " + r.getDistance() + " created successfully.");
        return true;
    }

    @Override
    public ReturnValuesContainer<Route> createRoute(RouteDTO routeDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createRoute");
        if (!dtoValidator.validate(routeDTO)) {
            LOGGER.error("Error: routeDTO is not valid.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_ROUTE_DTO_IS_NOT_VALID, null);
        }
        String cityFrom = routeDTO.getCityFrom();
        String cityTo = routeDTO.getCityTo();
        if (cityFrom == null){
            LOGGER.error("Error: cityFrom is null.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CITY_FROM_IS_NULL, null);
        }
        if (cityTo == null){
            LOGGER.error("Error: cityTo is null.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CITY_TO_IS_NULL, null);
        }
        if (cityFrom.equals(cityTo)){
            LOGGER.error("Error: cityFrom equals cityTo.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CITY_FROM_EQUALS_CITY_TO, null);
        }
        City cFrom = cityRepository.getByName(cityFrom);
        City cTo = cityRepository.getByName(cityTo);
        double distance = 0;
        try {
            distance = Double.parseDouble(routeDTO.getDistance());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse distance value.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CAN_NOT_PARSE_DISTANCE_VALUE, null);
        }
        if (distance == 0) {
            LOGGER.error("Error: distance value is not valid (distance =0).");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_DISTANCE_IS_NOT_VALID, null);
        }
        Route r = routeRepository.create(cFrom,cTo,distance);
        LOGGER.info("Route with cityFrom = " + r.getCityFrom() + ", cityTo" + r.getCityTo() + " and distance = " + r.getDistance() + " created successfully.");
        return new ReturnValuesContainer<Route>(UpdateMessageType.ROUTE_CREATED, r);
    }

    @Override
    public boolean updateRoute(RouteDTO routeDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateRoute");
        if (!dtoValidator.validate(routeDTO)) {
            LOGGER.error("Error: routeDTO is not valid.");
            return false;
        }
        if (routeDTO.getId() == null || routeDTO.getId().length()==0){
            LOGGER.error("Error: id is null or empty.");
            return false;
        }
        int id = 0;
        try {
            id = Integer.parseInt(routeDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse id value.");
            return false;
        }
        if (id == 0) {
            LOGGER.error("Error: id value is not valid (id = 0).");
            return false;
        }
        Route updated = routeRepository.getById(id);
        if (updated == null) {
            LOGGER.error("Error: there is no route with id = " + id + " in database.");
            return false;
        }
        City cityFrom = null;
        City cityTo = null;
        double distance = 0;
        if (routeDTO.getCityFrom()!=null
                && routeDTO.getCityFrom().length()!=0
                && !routeDTO.getCityFrom().equals("No cities available")) cityFrom = cityRepository.getByName(routeDTO.getCityFrom());
        else cityFrom = updated.getCityFrom();
        LOGGER.info("New cityFrom = " + cityFrom);
        if (routeDTO.getCityTo()!=null
                && routeDTO.getCityTo().length()!=0
                && !routeDTO.getCityTo().equals("No cities available")) cityTo = cityRepository.getByName(routeDTO.getCityTo());
        else cityTo = updated.getCityTo();
        LOGGER.info("New cityTo = " + cityTo);
        if (cityFrom.getName().equals(cityTo.getName())){
            LOGGER.error("Error: cityFrom and cityTo are equal.");
            return false;
        }
        if (routeDTO.getDistance()!= null && routeDTO.getDistance().length()!=0) {
            try {
                distance = Double.parseDouble(routeDTO.getDistance());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else distance = updated.getDistance();
        LOGGER.info("New distance = " + distance);
        routeRepository.update(updated.getId(),cityFrom,cityTo,distance);
        LOGGER.info("Route id = " + updated.getId()
                + " cityFrom = "
                + updated.getCityFrom().getName()
                + " cityTo = " + updated.getCityTo().getName()
                + " distance = " + updated.getDistance()
                + " updated successfully.");
        return true;
    }

    @Override
    public ReturnValuesContainer<Route> updateRoute(RouteDTO routeDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateRoute");
        if (!dtoValidator.validate(routeDTO)) {
            LOGGER.error("Error: routeDTO is not valid.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_ROUTE_DTO_IS_NOT_VALID, null);
        }
        if (routeDTO.getId() == null || routeDTO.getId().length()==0){
            LOGGER.error("Error: id is null or empty.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_ID_IS_NOT_VALID, null);
        }
        int id = 0;
        try {
            id = Integer.parseInt(routeDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse id value.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_ID_IS_NOT_VALID, null);
        }
        if (id == 0) {
            LOGGER.error("Error: id value is not valid (id = 0).");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_ID_IS_NOT_VALID, null);
        }
        Route updated = routeRepository.getById(id);
        if (updated == null) {
            LOGGER.error("Error: there is no route with id = " + id + " in database.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_NO_ROUTE_WITH_THIS_ID, null);
        }

        City cityFrom = null;
        City cityTo = null;
        double distance = 0;
        if (routeDTO.getCityFrom()!=null
                && routeDTO.getCityFrom().length()!=0
                && !routeDTO.getCityFrom().equals("No cities available")) cityFrom = cityRepository.getByName(routeDTO.getCityFrom());
        else cityFrom = updated.getCityFrom();
        LOGGER.info("New cityFrom = " + cityFrom);
        if (routeDTO.getCityTo()!=null
                && routeDTO.getCityTo().length()!=0
                && !routeDTO.getCityTo().equals("No cities available")) cityTo = cityRepository.getByName(routeDTO.getCityTo());
        else cityTo = updated.getCityTo();
        LOGGER.info("New cityTo = " + cityTo);
        if (cityFrom.getName().equals(cityTo.getName())){
            LOGGER.error("Error: cityFrom and cityTo are equal.");
            return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CITY_FROM_EQUALS_CITY_TO, null);
        }
        if (routeDTO.getDistance()!= null && routeDTO.getDistance().length()!=0) {
            try {
                distance = Double.parseDouble(routeDTO.getDistance());
            } catch (Exception e) {
                e.printStackTrace();
                return new ReturnValuesContainer<Route>(UpdateMessageType.ERROR_CAN_NOT_PARSE_DISTANCE_VALUE, null);
            }
        }
        else distance = updated.getDistance();
        LOGGER.info("New distance = " + distance);
        Route r = routeRepository.update(updated.getId(),cityFrom,cityTo,distance);
        LOGGER.info("Route id = " + updated.getId()
                + " cityFrom = "
                + updated.getCityFrom().getName()
                + " cityTo = " + updated.getCityTo().getName()
                + " distance = " + updated.getDistance()
                + " updated successfully.");
        return new ReturnValuesContainer<Route>(UpdateMessageType.ROUTE_EDITED, r);
    }

    @Override
    public boolean deleteRoute(int routeId) throws  Exception{
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteRoute");
        if (routeId <= 0) {
            LOGGER.error("Error: routeId value is not valid (id = " + routeId + ").");
            return false;
        }
        Route deleted = routeRepository.getById(routeId);
        if (deleted == null){
            LOGGER.error("Error: there is no route with id = " + routeId + " in database.");
            return false;
        }
        routeRepository.remove(routeId);
        LOGGER.info("Route id = " + routeId + "deleted successfully.");
        return true;
    }

    @Override
    public UpdateMessageType deleteRoute(int routeId, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteRoute");
        if (routeId <= 0) {
            LOGGER.error("Error: routeId value is not valid (id = " + routeId + ").");
            return UpdateMessageType.ERROR_ROUTE_ID_IS_NOT_VALID;
        }
        Route deleted = routeRepository.getById(routeId);
        if (deleted == null){
            LOGGER.error("Error: there is no route with id = " + routeId + " in database.");
            return UpdateMessageType.ERROR_NO_ROUTE_WITH_THIS_ID;
        }
        routeRepository.remove(routeId);
        LOGGER.info("Route id = " + routeId + "deleted successfully.");
        return UpdateMessageType.ROUTE_DELETED;
    }
}
