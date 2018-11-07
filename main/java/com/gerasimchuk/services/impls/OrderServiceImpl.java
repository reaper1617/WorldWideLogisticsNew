package com.gerasimchuk.services.impls;

import com.gerasimchuk.constants.WWLConstants;
import com.gerasimchuk.converters.OrderToDTOConverterImpl;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.OrderWithRouteDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.exceptions.driverexceptions.TooManyHoursWorkedForOrderException;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.rabbit.RabbitMQSender;
import com.gerasimchuk.repositories.*;
import com.gerasimchuk.services.interfaces.OrderService;
import com.gerasimchuk.utils.DateParser;
import com.gerasimchuk.utils.MessageConstructor;
import com.gerasimchuk.utils.PersonalNumberGenerator;
import com.gerasimchuk.utils.ReturnValuesContainer;
import com.gerasimchuk.validators.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implementation for {@link OrderService} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {


    private CargoRepository cargoRepository;
    private TruckRepository truckRepository;
    private OrderRepository orderRepository;
    private CityRepository cityRepository;
    private RouteRepository routeRepository;
    private DTOValidator dtoValidator;
    private DriverRepository driverRepository;
    private RabbitMQSender rabbitMQSender;
    private MessageConstructor messageConstructor;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(OrderServiceImpl.class);

    /**
     * Instantiates a new Order service.
     *
     * @param cargoRepository    the cargo repository
     * @param truckRepository    the truck repository
     * @param orderRepository    the order repository
     * @param cityRepository     the city repository
     * @param routeRepository    the route repository
     * @param dtoValidator       the dto validator
     * @param driverRepository   the driver repository
     * @param rabbitMQSender     the rabbit mq sender
     * @param messageConstructor the message constructor
     */
    @Autowired
    public OrderServiceImpl(CargoRepository cargoRepository, TruckRepository truckRepository, OrderRepository orderRepository, CityRepository cityRepository, RouteRepository routeRepository, DTOValidator dtoValidator, DriverRepository driverRepository, RabbitMQSender rabbitMQSender, MessageConstructor messageConstructor) {
        this.cargoRepository = cargoRepository;
        this.truckRepository = truckRepository;
        this.orderRepository = orderRepository;
        this.cityRepository = cityRepository;
        this.routeRepository = routeRepository;
        this.dtoValidator = dtoValidator;
        this.driverRepository = driverRepository;
        this.rabbitMQSender = rabbitMQSender;
        this.messageConstructor = messageConstructor;
    }

    public Collection<Cargo> getChosenCargos(OrderDTO orderDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getChosenCargos");
        if (!dtoValidator.validate(orderDTO)) {
            LOGGER.error("Error: orderDTO is not valid.");
            return null;
        }
        Collection<Cargo> cargos = new ArrayList<Cargo>();
        if (orderDTO.getCargosInOrder()!=null){
            String[] cargosInOrder = orderDTO.getCargosInOrder();
            for(int i = 0; i < cargosInOrder.length; i++){
                int id = 0;
                try{
                    id = Integer.parseInt(cargosInOrder[i]);
                }
                catch (Exception e){
                    e.printStackTrace();
                    LOGGER.warn("Cannot parse id value.");
                }
                if (id != 0) {
                    Cargo cargo = cargoRepository.getById(id);
                    if (cargo!=null) {
                        cargos.add(cargo);
                        LOGGER.info("Cargo " + cargo.getName() + " added in chosenCargosList");
                    }
                    else {
                        LOGGER.warn("There is no cargo with id = " + id + " in database.");
                    }
                }
                else {
                    LOGGER.warn("Id value is not valid (id = 0)");
                }
            }
        }
        else {
            LOGGER.error("Error: cargosInOrder field in cargoDTO is null");
        }
        return cargos;
    }

    public Collection<Truck> getAvailableTrucks(OrderDTO orderDTO) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAvailableTrucks");
        if (!dtoValidator.validate(orderDTO)){
            LOGGER.error("Error: orderDTO is not valid!");
            return null;
        }
        Collection<Truck> result = new ArrayList<Truck>();
        Collection<Truck> allTrucks = truckRepository.getAll();
        for(Truck t: allTrucks){
            double maxCargoWeightOnRoute = getMaxCargoWeightOnRoute(orderDTO,t);
            if (t.getState().equals(TruckState.READY) && t.getAssignedOrder()==null){
                if (t.getCapacity()*WWLConstants.TON >= maxCargoWeightOnRoute){
                    result.add(t);
                    LOGGER.info("Truck " + t.getRegistrationNumber() + " added to availableTrucksList");
                }
                else {
                    LOGGER.info("Truck " + t.getRegistrationNumber() + " capacity less than cargos' weight");
                }
            }
            else {
                LOGGER.info("Truck " + t.getRegistrationNumber() + " has state = "
                        + t.getState() + " and assigned order = " + t.getAssignedOrder());
            }
        }
        LOGGER.info("AvailableTrucks collection: " + result + ", size = " + result.size());
        return result;
    }

    public ReturnValuesContainer<List<Driver>> checkIfDriversHoursWorkedOverLimit(double orderExecutingTime, Date date, Collection<Driver> driversInTruck) {
        List<Driver> driversWithHoursOverLimit = new ArrayList<Driver>();
        for(Driver d : driversInTruck){
            LOGGER.info("Driver: " + d.getUser().getPersonalNumber());
            double hoursWorked = d.getHoursWorked();
            LOGGER.info("Hours worked = " + hoursWorked);
            long createOrderTimeMs = date.getTime();
            Date orderExecDate = new Date(createOrderTimeMs + (long)orderExecutingTime*WWLConstants.MILLISECONDS_IN_HOUR);
            boolean nextMonthDuringOrderExecuting = false;
            if (date.getMonth() != orderExecDate.getMonth()) nextMonthDuringOrderExecuting = true;
            if (hoursWorked + orderExecutingTime > WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH
                    && !nextMonthDuringOrderExecuting) {
                LOGGER.error("Driver " + d.getUser().getPersonalNumber() + " cannot execute this order.");
                driversWithHoursOverLimit.add(d);
            }
        }
        if (!driversWithHoursOverLimit.isEmpty()) return new ReturnValuesContainer<List<Driver>>(UpdateMessageType.ERROR_DRIVER_HOURS_WORKED_OVER_LIMIT,driversWithHoursOverLimit);
        else return new ReturnValuesContainer<List<Driver>>(UpdateMessageType.ALL_DRIVERS_HOURS_WORKED_VALID,null);
    }


    private double getMaxCargoWeightOnRoute(OrderDTO orderDTO, Truck truck) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getMaxCargoWeightOnRoute");
        Collection<Cargo> cargosInOrder = getChosenCargos(orderDTO);
        Collection<City> orderRoute = getOrderRoute(orderDTO, truck);
        Object[] orderRouteArray = orderRoute.toArray();
        double[] weightsOnRoute = new double[orderRoute.size()];
        countWeightsOnRoute(cargosInOrder, orderRouteArray, weightsOnRoute);
        for( int i = 1; i < weightsOnRoute.length; i++){
            weightsOnRoute[i] += weightsOnRoute[i-1];
        }
        Arrays.sort(weightsOnRoute);
        double max = weightsOnRoute[weightsOnRoute.length-1];
        LOGGER.info("Max weight on route = " + max);
        LOGGER.info("Class: " + this.getClass().getName() + " out from getMaxCargoWeightOnRoute method");
        return max;
    }

    private void countWeightsOnRoute(Collection<Cargo> cargosInOrder, Object[] orderRouteArray, double[] weightsOnRoute) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getMaxCargoWeightOnRoute");
        for(Cargo cargo: cargosInOrder){
            LOGGER.info("Cargo: " + cargo.getName());
            LOGGER.info("Cargo load city:" + cargo.getRoute().getCityFrom().getName());
            LOGGER.info("Cargo unload city:" + cargo.getRoute().getCityTo().getName());
            LOGGER.info("Cargo weight: " + cargo.getWeight());
            boolean isLoadActionFound = false;
            for(int i = 0; i < orderRouteArray.length; i++){
                if (cargo.getRoute().getCityFrom().getName().equals(((City)orderRouteArray[i]).getName()) ){
                    weightsOnRoute[i] += cargo.getWeight();
                    LOGGER.info("Current weight in load city value: " + weightsOnRoute[i]);
                    isLoadActionFound = true;
                }
                if (cargo.getRoute().getCityTo().getName().equals(((City)orderRouteArray[i]).getName())){
                    if (isLoadActionFound){
                        weightsOnRoute[i] -= cargo.getWeight();
                        LOGGER.info("Current weight in unload city value: " + weightsOnRoute[i]);
                    }
                }
            }
        }
        LOGGER.info("Class: " + this.getClass().getName() + " out from getMaxCargoWeightOnRoute method");
    }


    public List<City> getOrderRoute(OrderDTO orderDTO, Truck truck) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute");
        if (!dtoValidator.validate(orderDTO)){
            LOGGER.info("Class: " + this.getClass().getName() + " out from getOrderRoute: orderDTO is not valid.");
            return null;
        }
        Collection<Cargo> cargosInOrder = getChosenCargos(orderDTO);
        List<City> citiesWithCargoLoad = new ArrayList<City>();
        List<City> citiesWithCargoUnload = new ArrayList<City>();
        for(Cargo c: cargosInOrder){
            City cityFrom = c.getRoute().getCityFrom();
            if (!citiesWithCargoLoad.contains(cityFrom)){ // todo: check if it works correctly!!!
                citiesWithCargoLoad.add(cityFrom);
            }
            City cityTo = c.getRoute().getCityTo();
            if (!citiesWithCargoUnload.contains(cityTo)){
                citiesWithCargoUnload.add(cityTo);
            }
        }
        if (truck != null){
            City truckCurrentCity = truck.getCurrentCity();
            citiesWithCargoLoad.remove(truckCurrentCity);
            citiesWithCargoLoad.add(0,truckCurrentCity);
        }
        List<City> route = new ArrayList<City>(citiesWithCargoLoad);
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, citiesWithCargoLoadList: " + citiesWithCargoLoad);
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, citiesWithCargoUnloadList: " + citiesWithCargoUnload);
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, start to count second part of the route...");
        for(City city: citiesWithCargoUnload){
            // get all cargos which need to be unload in this city
            LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, city: " + city);
            if (citiesWithCargoLoad.contains(city)){
                LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, city: " + cargosInOrder + " is in citiesWithCargoLoad list!");
                List<Cargo> cargosToUnloadInThisCity = getCargosToUnloadInCity(cargosInOrder,city);
                LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, cargosToUnloadInThisCity list: " + cargosToUnloadInThisCity);
                List<City> citiesForLoadCargos = getLoadCityListForCargos(cargosToUnloadInThisCity);
                LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, citiesForLoadCargos list: " + citiesForLoadCargos);
                List<City> citiesBeforeCurrent = citiesWithCargoLoad.subList(0, citiesWithCargoLoad.indexOf(city));
                LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, citiesBeforeCurrent list: " + citiesBeforeCurrent);
                boolean allCargosLoadedBefore = false;
                if (citiesBeforeCurrent.size() != 0){
                    if (citiesBeforeCurrent.containsAll(citiesForLoadCargos)) allCargosLoadedBefore = true;
                    LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, allCargosLoadedBefore: " + allCargosLoadedBefore);
                }
                if (!allCargosLoadedBefore){
                    route.add(city);
                    LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, city: " + city + " added into route");
                }
            }
            else{
                route.add(city);
                LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, city: " + city + " added into route");
            }
        }
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderRoute, route: " + route);
        LOGGER.info("Class: " + this.getClass().getName() + " out from getOrderRoute method");
        return route;
    }

    private List<Cargo> getCargosToUnloadInCity(Collection<Cargo> cargosInOrder, City city){
        if (cargosInOrder == null || city == null) return null;
        List<Cargo> cargosToUnloadInCity = new ArrayList<Cargo>();
        for(Cargo cargo: cargosInOrder){
            if (cargo.getRoute().getCityTo().getName().equals(city.getName())) cargosToUnloadInCity.add(cargo);
        }
        return cargosToUnloadInCity;
    }

    private List<City> getLoadCityListForCargos(List<Cargo> cargos){
        if (cargos == null) return null;
        List<City> cities = new ArrayList<City>();
        for(Cargo cargo: cargos){
            City city = cargo.getRoute().getCityFrom();
            if (!cities.contains(city)){
                cities.add(city);
            }
        }
        return cities;
    }

    @Transactional
    public UpdateMessageType createOrder(OrderDTO orderDTO) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createOrder");
        if (!dtoValidator.validate(orderDTO)) {
            LOGGER.error("Error: orderDTO is not valid.");
            return UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID;
        }
        String personalNumber = PersonalNumberGenerator.generate(10);
        Date date = new Date();
        String currentDate = date.toString();
        int truckId = 0;
        try{
            truckId = Integer.parseInt(orderDTO.getAssignedTruckId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse id value.");
            return UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID;
        }
        Truck assignedTruck = truckRepository.getById(truckId);
        if (assignedTruck == null){
            LOGGER.error("Error: there is no truck with id = " + truckId + " in database");
            return UpdateMessageType.ERROR_NO_TRUCK_WITH_THIS_ID;
        }
        LOGGER.info("Checking if there are drivers with too much hours worked.");
        double orderExecutingTime = getExecutingTime(orderDTO);
        LOGGER.info("Order executing time = " + orderExecutingTime + " hours");
        Collection<Driver> driversInTruck = assignedTruck.getDriversInTruck();
        if (driversInTruck == null || driversInTruck.isEmpty()){
            LOGGER.error("Error: truck " + assignedTruck.getRegistrationNumber() + " has no assigned drivers to execute order.");
            return UpdateMessageType.ERROR_TRUCK_HAS_NO_ASSIGNED_DRIVERS_TO_EXECUTE_ORDER;
        }
        ReturnValuesContainer<List<Driver>> driversWithHoursOverLimit = checkIfDriversHoursWorkedOverLimit(orderExecutingTime, date, driversInTruck);
        if (!driversWithHoursOverLimit.getUpdateMessageType().equals(UpdateMessageType.ALL_DRIVERS_HOURS_WORKED_VALID)){
            LOGGER.error("Truck " + assignedTruck.getRegistrationNumber() + " has drivers with hours worked over limit: " + driversWithHoursOverLimit.getReturnedValue());
            return driversWithHoursOverLimit.getUpdateMessageType();
        }
        LOGGER.info("All drivers are able to execute this order");
        Order newOrder = orderRepository.create(personalNumber, orderDTO.getDescription(), currentDate, OrderStatus.NOT_PREPARED, assignedTruck);

        if (newOrder!=null) {
            Collection<Cargo> cargosInOrder = getChosenCargos(orderDTO);
            LOGGER.info("Assigning cargos to order...");
            for (Cargo c : cargosInOrder) {
                LOGGER.info("Cargo " + c.getName());
                cargoRepository.update(c.getId(), c.getPersonalNumber(), c.getName(), c.getWeight(), c.getStatus(), c.getRoute(),newOrder);
                LOGGER.info("Cargo successfully associated");
            }
            LOGGER.info("All cargos are assigned to order.");
            LOGGER.info("Changing driver(s) status to RESTING...");

            for(Driver d: driversInTruck){
                LOGGER.info("Updating status for driver " + d.getUser().getPersonalNumber() + "...");
                driverRepository.update(d.getId(),d.getHoursWorked(), DriverStatus.RESTING,d.getCurrentCity(),d.getCurrentTruck());
                LOGGER.info("Status for driver " + d.getUser().getPersonalNumber() + " updated to RESTING successfully");
            }
            LOGGER.info("All drivers statuses are updated successfully");
            LOGGER.info("Order " + newOrder.getDescription() + " successfully created");
            return UpdateMessageType.ORDER_CREATED;
        }
        LOGGER.error("Error: failed to create order.");
        return UpdateMessageType.ERROR_CAN_NOT_CREATE_ORDER;
    }

    @Override
    @Transactional
    public ReturnValuesContainer<Order> createOrder(OrderDTO orderDTO, int val) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createOrder");
        if (!dtoValidator.validate(orderDTO)) {
            LOGGER.error("Error: orderDTO is not valid.");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID,null);
        }
        String personalNumber = PersonalNumberGenerator.generate(10);
        Date date = new Date();
        String currentDate = date.toString();
        int truckId = 0;
        try{
            truckId = Integer.parseInt(orderDTO.getAssignedTruckId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse id value.");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID,null);
        }
        Truck assignedTruck = truckRepository.getById(truckId);
        if (assignedTruck == null){
            LOGGER.error("Error: there is no truck with id = " + truckId + " in database");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_NO_TRUCK_WITH_THIS_ID,null);
        }
        LOGGER.info("Checking if there are drivers with too much hours worked.");
        double orderExecutingTime = getExecutingTime(orderDTO);
        LOGGER.info("Order executing time = " + orderExecutingTime + " hours");
        Collection<Driver> driversInTruck = assignedTruck.getDriversInTruck();
        if (driversInTruck == null || driversInTruck.isEmpty()){
            LOGGER.error("Error: truck " + assignedTruck.getRegistrationNumber() + " has no assigned drivers to execute order.");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_TRUCK_HAS_NO_ASSIGNED_DRIVERS_TO_EXECUTE_ORDER, null);
        }
        for(Driver d : driversInTruck){
            LOGGER.info("Driver: " + d.getUser().getPersonalNumber());
            double hoursWorked = d.getHoursWorked();
            LOGGER.info("Hours worked = " + hoursWorked);
            long createOrderTimeMs = date.getTime();
            Date orderExecDate = new Date(createOrderTimeMs + (long)orderExecutingTime*WWLConstants.MILLISECONDS_IN_HOUR);
            boolean nextMonthDuringOrderExecuting = false;
            if (date.getMonth() != orderExecDate.getMonth()) nextMonthDuringOrderExecuting = true;
            if (hoursWorked + orderExecutingTime > WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH
                    && !nextMonthDuringOrderExecuting) {
                LOGGER.error("Driver " + d.getUser().getPersonalNumber() + " cannot execute this order.");
                return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_DRIVER_HOURS_WORKED_OVER_LIMIT,null);
            }
        }
        LOGGER.info("All drivers are able to execute this order");
        Order newOrder = orderRepository.create(personalNumber, orderDTO.getDescription(), currentDate, OrderStatus.NOT_PREPARED, assignedTruck);

        if (newOrder!=null) {
            Collection<Cargo> cargosInOrder = getChosenCargos(orderDTO);
            LOGGER.info("Assigning cargos to order...");
            for (Cargo c : cargosInOrder) {
                LOGGER.info("Cargo " + c.getName());
                cargoRepository.update(c.getId(), c.getPersonalNumber(), c.getName(), c.getWeight(), c.getStatus(), c.getRoute(),newOrder);
                LOGGER.info("Cargo successfully associated");
            }
            LOGGER.info("All cargos are assigned to order.");
            for(Driver d: driversInTruck){
                LOGGER.info("Updating status for driver " + d.getUser().getPersonalNumber() + "...");
                driverRepository.update(d.getId(),d.getHoursWorked(), DriverStatus.RESTING,d.getCurrentCity(),d.getCurrentTruck());
                LOGGER.info("Status for driver " + d.getUser().getPersonalNumber() + " updated to RESTING successfully");
            }
            LOGGER.info("All drivers statuses are updated successfully");
            LOGGER.info("Order " + newOrder.getDescription() + " successfully created");
            LOGGER.info("Order " + newOrder.getDescription() + " successfully created");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ORDER_CREATED,newOrder);
        }
        LOGGER.error("Error: failed to create order.");
        return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_CAN_NOT_CREATE_ORDER,null);
    }

    @Transactional
    public UpdateMessageType updateOrder(OrderDTO orderDTO) throws RouteException, TooManyHoursWorkedForOrderException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateOrder");
        if (!dtoValidator.validate(orderDTO)){
            LOGGER.error("Error: orderDTO is not valid.");
            return UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID;
        }

        if (orderDTO.getId() == null) {
            LOGGER.error("Error: id field in orderDTO is null");
            return UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID;
        }
        int id = 0;
        try {
            id = Integer.parseInt(orderDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.info("Error: cannot parse id value.");
            return UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID;
        }
        Order updated = null;
        if (id != 0){
            updated = orderRepository.getById(id);
        }
        else {
            LOGGER.error("Error: id value is not valid (id = 0)");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        if (updated != null){
            updateOrderWithFieldsFromDTO(updated,orderDTO);
            LOGGER.info("Order " + updated.getDescription() + " successfully updated");
            return UpdateMessageType.ORDER_EDITED;
        }
        LOGGER.error("Error: failed to update order.");
        return UpdateMessageType.ERROR_CAN_NOT_UPDATE_ORDER;
    }

    @Override
    @Transactional
    public ReturnValuesContainer<Order> updateOrder(OrderDTO orderDTO, int val) throws RouteException, TooManyHoursWorkedForOrderException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateOrder");
        if (!dtoValidator.validate(orderDTO)){
            LOGGER.error("Error: orderDTO is not valid.");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID, null);
        }

        if (orderDTO.getId() == null) {
            LOGGER.error("Error: id field in orderDTO is null");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID, null);
        }
        int id = 0;
        try {
            id = Integer.parseInt(orderDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.info("Error: cannot parse id value.");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID, null);
        }
        Order updated = null;
        if (id != 0){
            updated = orderRepository.getById(id);
        }
        else {
            LOGGER.error("Error: id value is not valid (id = 0)");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_ID_IS_NOT_VALID,null);
        }
        if (updated != null){
            Order persistedUpd = updateOrderWithFieldsFromDTO(updated,orderDTO);
            LOGGER.info("Order " + updated.getDescription() + " successfully updated");
            return new ReturnValuesContainer<Order>(UpdateMessageType.ORDER_EDITED, persistedUpd);
        }
        LOGGER.error("Error: failed to update order.");
        return new ReturnValuesContainer<Order>(UpdateMessageType.ERROR_CAN_NOT_UPDATE_ORDER, null);
    }

    private Order updateOrderWithFieldsFromDTO(Order updated, OrderDTO orderDTO) throws RouteException, TooManyHoursWorkedForOrderException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateOrderWithFieldsFromDTO");
        if (updated == null){
            LOGGER.error("Error: updating order is null.");
            return null;
        }
        if (orderDTO == null){
            LOGGER.error("Error: orderDTO is null.");
            return null;
        }
        if (updated.getStatus().equals(OrderStatus.EXECUTING) || updated.getStatus().equals(OrderStatus.EXECUTED)){
            LOGGER.error("Error: cannot edit order with status = " + updated.getStatus());
            return null;
        }
        String newPersonalNumber = null;
        String newDescription = null;
        OrderStatus newOrderStatus = null;
        Truck newAssignedTruck = null;
        if (orderDTO.getPersonalNumber() != null && orderDTO.getPersonalNumber().length() != 0) newPersonalNumber = orderDTO.getPersonalNumber();
        else newPersonalNumber = updated.getPersonalNumber();
        LOGGER.info("New personal number:" + newPersonalNumber);
        if (orderDTO.getDescription() != null && orderDTO.getDescription().length()!=0) newDescription = orderDTO.getDescription();
        else newDescription = updated.getDescription();
        LOGGER.info("New description: " + newDescription);
        if (orderDTO.getStatus() != null && orderDTO.getStatus().length()!=0 && !orderDTO.getStatus().equals("Not selected")) newOrderStatus = getOrderStatusFromString(orderDTO.getStatus());
        else newOrderStatus = updated.getStatus();
        LOGGER.info("New status: " + newOrderStatus);
        if (orderDTO.getAssignedTruckId() != null && orderDTO.getAssignedTruckId().length() != 0){
            int id;
            try{
                id = Integer.parseInt(orderDTO.getAssignedTruckId());
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: cannot parse truck id.");
                return null;
            }
            if (id == 0) {
                LOGGER.error("Error: truck id value is not valid (id = 0)");
                return null;
            }
            newAssignedTruck = truckRepository.getById(id);
        }
        else newAssignedTruck = updated.getAssignedTruck();
        LOGGER.info("New assigned truck: " + newAssignedTruck.getRegistrationNumber());
        Collection<Driver> driversInTruck = newAssignedTruck.getDriversInTruck();

        String sDate = updated.getDate();
        Date date = new Date();
        date.setYear(DateParser.parseYear(sDate));
        date.setMonth(DateParser.parseMonth(sDate));
        date.setDate(DateParser.parseDay(sDate));
        date.setHours(DateParser.parseHour(sDate));
        date.setMinutes(DateParser.parseMinutes(sDate));
        date.setSeconds(DateParser.parseSeconds(sDate));

        LOGGER.info("Checking if there are drivers with too much hours worked...");
        double orderExecutingTime = getExecutingTime(orderDTO);
        LOGGER.info("Order executing time: " + orderExecutingTime + " hours");
        for(Driver d : driversInTruck){
            LOGGER.info("Driver: " + d.getUser().getPersonalNumber());
            double hoursWorked = d.getHoursWorked();
            LOGGER.info("Hours worked: " + hoursWorked);
            long createOrderTimeMs = date.getTime();
            Date orderExecDate = new Date(createOrderTimeMs + (long)orderExecutingTime*WWLConstants.MILLISECONDS_IN_HOUR);
            boolean nextMonthDuringOrderExecuting = false;
            if (date.getMonth() != orderExecDate.getMonth()) nextMonthDuringOrderExecuting = true;
            if (hoursWorked + orderExecutingTime > WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH
                    && !nextMonthDuringOrderExecuting) {
                LOGGER.error("Driver " + d.getUser().getPersonalNumber() + " cannot execute this order.");
                throw new TooManyHoursWorkedForOrderException();
            }
        }
        Collection<Cargo> currentCargosInOrder = updated.getCargosInOrder();
        LOGGER.info("Reassigning new set of cargos in order...");
        for(Cargo c: currentCargosInOrder){
            cargoRepository.update(c.getId(),c.getPersonalNumber(),c.getName(),c.getWeight(),CargoStatus.PREPARED,c.getRoute(), null);
            LOGGER.info("Cargo " + c.getName() + " unassigned from order");
        }
        Collection<Cargo> newCargosInOrder = getChosenCargos(orderDTO);
        for(Cargo c: newCargosInOrder){
            cargoRepository.update(c.getId(),c.getPersonalNumber(),c.getName(),c.getWeight(),c.getStatus(),c.getRoute(), updated);
            LOGGER.info("Cargo " + c.getName() + " assigned to order");
        }
        Order upd = orderRepository.update(updated.getId(),newPersonalNumber, newDescription, updated.getDate(),newOrderStatus,newAssignedTruck);
        LOGGER.info("Order " + updated.getDescription() + " updated successfully.");
        return upd;
    }

    public OrderStatus getOrderStatusFromString(String status) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrderStatusFromString");
        if (status == null || status.length()==0) {
            LOGGER.error("Error: status string is null or empty.");
            return null;
        }
        OrderStatus result = null;
        if (status.equals("NOT_PREPARED")) result = OrderStatus.NOT_PREPARED;
        if (status.equals("PREPARED")) result =  OrderStatus.PREPARED;
        if (status.equals("EXECUTING")) result = OrderStatus.EXECUTING;
        if (status.equals("EXECUTED")) result =  OrderStatus.EXECUTED;
        if (result != null){
            LOGGER.info("Status is " + result);
        }
        else {
            LOGGER.error("Error: status is null.");
        }
        return result;
    }

    public boolean areAllCargosDelivered(Order order) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: areAllCargosDelivered");
        if (order == null) {
            LOGGER.error("Error: order is null.");
            return false;
        }
        for(Cargo c: order.getCargosInOrder()){
            if (!c.getStatus().equals(CargoStatus.DELIVERED)) {
                LOGGER.error("Error: cargo " + c.getName() + " has status " + c.getStatus());
                return false;
            }
        }
        LOGGER.info("All cargos have status DELIVERED");
        return true;
    }

    public UpdateMessageType deleteOrder(OrderDTO orderDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteOrder");
        if (!dtoValidator.validate(orderDTO)) {
            LOGGER.error("Error: orderDTO is not valid.");
            return UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID;
        }
        if (orderDTO.getId() == null || orderDTO.getId().length()==0){
            LOGGER.error("Error: orderDTO is null or empty.");
            return UpdateMessageType.ERROR_ORDER_DTO_IS_NOT_VALID;
        }
        int id = 0;
        try{
            id = Integer.parseInt(orderDTO.getId());
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Error: cannot parse order id.");
            return UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID;
        }
        return deleteOrder(id);
    }

    @Override
    @Transactional
    public UpdateMessageType deleteOrder(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteOrder");
        if (id <= 0) {
            LOGGER.error("Error: order id value is not valid (id = 0)");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        Order deleted = orderRepository.getById(id);
        if (deleted == null){
            LOGGER.error("Error: there is no order with id = " + id + " in database");
            return UpdateMessageType.ERROR_NO_ORDER_WITH_THIS_ID;
        }
        if (deleted.getStatus().equals(OrderStatus.EXECUTED) || deleted.getStatus().equals(OrderStatus.EXECUTING)){
            LOGGER.error("Error: can not delete order with status =" + deleted.getStatus());
            return UpdateMessageType.ERROR_CAN_NOT_DELETE_ORDER_WITH_SUCH_STATUS;
        }
        Collection<Cargo> cargosInOrder = deleted.getCargosInOrder();
        LOGGER.info("Unassigning cargos from order before deleting...");
        for(Cargo c: cargosInOrder){
            cargoRepository.update(c.getId(),c.getPersonalNumber(),c.getName(),c.getWeight(),c.getStatus(),c.getRoute(), null);
            LOGGER.info("Cargo " + c.getName() + " unassigned successfully.");
        }
        LOGGER.info("All cargos are unassigned successfully.");
        Collection<Driver> drivers = deleted.getAssignedTruck().getDriversInTruck();
        LOGGER.info("Updating driver(s) statuses to FREE...");
        for(Driver d: drivers){
            driverRepository.update(d.getId(),d.getHoursWorked(),DriverStatus.FREE,d.getCurrentCity(),d.getCurrentTruck());
        }
        LOGGER.info("All drivers statuses uprated to FREE...");
        orderRepository.remove(deleted.getId());
        LOGGER.info("Order " + deleted.getDescription() + " deleted successfully");
        LOGGER.info("Class: " + this.getClass().getName() + " out from deleteOrder method: order deleted successfully");
        return UpdateMessageType.ORDER_DELETED;
    }

    @Override
    @Transactional
    public UpdateMessageType updateOrderStatus(int orderId, OrderStatus newStatus) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateOrderStatus");
        if (orderId <=0){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: order id is invalid");
            return UpdateMessageType.ERROR_ORDER_ID_IS_NOT_VALID;
        }
        if (newStatus == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: new order status is null");
            return UpdateMessageType.ERROR_ORDER_STATUS_IS_NULL;
        }
        Order order = orderRepository.getById(orderId);
        if (order == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: there is no such order");
            return UpdateMessageType.ERROR_NO_ORDER_WITH_THIS_ID;
        }
        OrderStatus currentStatus = order.getStatus();
        List<OrderStatus> statuses = new ArrayList<OrderStatus>();
        statuses.add(OrderStatus.NOT_PREPARED);
        statuses.add(OrderStatus.PREPARED);
        statuses.add(OrderStatus.EXECUTING);
        statuses.add(OrderStatus.EXECUTED);
        int currentStatusIndex = statuses.indexOf(currentStatus);
        int newStatusIndex = statuses.indexOf(newStatus);
        if (newStatusIndex < currentStatusIndex){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: can not update order status to lower");
            return UpdateMessageType.ERROR_CAN_NOT_UPDATE_ORDER_STATUS_TO_LOWER;
        }
        if (newStatus.equals(OrderStatus.EXECUTED)&& !areAllCargosDelivered(order)){
            LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: can not update order status to EXECUTED while not all cargos are delivered");
            return UpdateMessageType.ERROR_CAN_NOT_UPDATE_STATUS_FOR_ORDER_WITH_UNDELIVERED_CARGOS;
        }
        if (newStatus.equals(OrderStatus.EXECUTED) && areAllCargosDelivered(order)){
            Truck truck = order.getAssignedTruck();
            OrderWithRouteDTO orderWithRouteDTO = null;
            try {
                orderWithRouteDTO = new OrderToDTOConverterImpl(this).convertToDTOWithRoute(order);
            } catch (RouteException e) {
                e.printStackTrace();
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: catched exception: " + e.getMessage());
                return UpdateMessageType.ERROR_NO_ROUTE_BETWEEN_THESE_CITIES;
            }
            if (orderWithRouteDTO == null){
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: can not create orderWithRouteDTO");
                return UpdateMessageType.ERROR_CAN_NOT_GET_ORDER_ROUTE;
            }
            String[] route = orderWithRouteDTO.getRoute();
            String finalCityName = route[route.length-1];
            City finalDestinationCity = cityRepository.getByName(finalCityName);
            if (finalDestinationCity == null){
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: can not find final city");
                return UpdateMessageType.ERROR_NO_CITY_WITH_THIS_NAME;
            }
            if (truck == null){
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: assigned truck is null!!!");
                return UpdateMessageType.ERROR_ASSIGNED_TRUCK_IS_NULL_FOR_EXECUTING_ORDER;
            }
            Collection<Driver> drivers = truck.getDriversInTruck();
            double orderExecutingTime = 0;
            try {
                orderExecutingTime = getExecutingTime(OrderToDTOConverterImpl.convert(order));
            } catch (RouteException e) {
                e.printStackTrace();
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: catched exception: " +e.getMessage());
                return UpdateMessageType.ERROR_NO_ROUTE_BETWEEN_THESE_CITIES;
            }
            if (orderExecutingTime == 0) {
                LOGGER.error("Class: " + this.getClass().getName() + " out from updateOrderStatus method: can not count order executing time");
                return UpdateMessageType.ERROR_CAN_NOT_COUNT_ORDER_EXECUTING_TIME;
            }
            for(Driver d: drivers){
                double newHoursWorked = d.getHoursWorked() + orderExecutingTime;
                if (newHoursWorked > WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH) newHoursWorked -=WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH;
                driverRepository.update(d.getId(),newHoursWorked,DriverStatus.FREE,finalDestinationCity,d.getCurrentTruck());
            }
            orderRepository.update(order.getId(),order.getPersonalNumber(),order.getDescription(),order.getDate(),newStatus,null);
            return UpdateMessageType.ORDER_STATUS_UPDATED;
        }
        orderRepository.update(order.getId(),order.getPersonalNumber(),order.getDescription(),order.getDate(),newStatus,order.getAssignedTruck());
        rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.ORDER_EDITED, order));
        return UpdateMessageType.ORDER_STATUS_UPDATED;
    }

    @Override
    public double getExecutingTime(OrderDTO orderDTO) throws RouteException {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getExecutingTime");
        int truckId = 0;
        try{
            truckId = Integer.parseInt(orderDTO.getAssignedTruckId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Truck truck = null;
        if (truckId != 0) truck = truckRepository.getById(truckId);
        Collection <City> route = getOrderRoute(orderDTO, truck);
         if (route == null) {
             LOGGER.error("Error: route is null.");
             return 0;
         }
         Object[] routeArr = route.toArray();
         int size = routeArr.length;
         if (size == 0) {
             LOGGER.error("Error: route length is 0.");
             return 0;
         }
         double routeLength = 0;
         LOGGER.info("Counting route length...");
         for(int i = 0; i < size-1; i++){
             City cityFrom = (City)routeArr[i];
             City cityTo = (City)routeArr[i+1];
             Route r = routeRepository.getByCities(cityFrom,cityTo);
             if (r == null) {
                 LOGGER.error("Error: there is no route with cityFrom = "
                         + cityFrom.getName()
                         + " and cityTo = "
                         + cityTo.getName() +
                         " in database. Counting route length failed.");
                 return 0;
             }
             routeLength += r.getDistance();
             LOGGER.info("Current length = " + routeLength);
         }
         LOGGER.info("Executing time is " + routeLength/WWLConstants.AVERAGE_TRUCK_SPEED);
         return routeLength/WWLConstants.AVERAGE_TRUCK_SPEED;
    }

}
