package com.gerasimchuk.controllers;

import com.gerasimchuk.converters.*;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.OrderStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.rabbit.RabbitMQSender;
import com.gerasimchuk.repositories.*;
import com.gerasimchuk.services.interfaces.*;
import com.gerasimchuk.utils.MessageConstructor;
import com.gerasimchuk.utils.OrderWithRoute;
import com.gerasimchuk.utils.ReturnValuesContainer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Admin Controller
 *
 * @author Reaper
 * @version 1.0
 */
@Controller
public class AdminController {


    private OrderRepository orderRepository;
    private TruckRepository truckRepository;
    private UserRepository userRepository;
    private CargoRepository cargoRepository;
    private CityRepository cityRepository;
    private RouteRepository routeRepository;
    private DriverRepository driverRepository;

    private UserService userService;
    private OrderService orderService;
    private CargoService cargoService;
    private TruckService truckService;
    private CityService cityService;
    private RouteService routeService;
    private RabbitMQSender rabbitMQSender;
    private MessageConstructor messageConstructor;
    private StatisticService statisticService;
    private OrderToDTOConverter orderToDTOConverter;
    private TruckToDTOConverter truckToDTOConverter;
    private UserToDTOConverter userToDTOConverter;
    private CargoToDTOConverter cargoToDTOConverter;
    private CityToDTOConverter cityToDTOConverter;
    private RouteToDTOConverter routeToDTOConverter;

    /**
     * Instantiates a new Admin controller.
     *
     * @param orderRepository     the order repository
     * @param truckRepository     the truck repository
     * @param userRepository      the user repository
     * @param cargoRepository     the cargo repository
     * @param cityRepository      the city repository
     * @param routeRepository     the route repository
     * @param driverRepository    the driver repository
     * @param userService         the user service
     * @param orderService        the order service
     * @param cargoService        the cargo service
     * @param truckService        the truck service
     * @param cityService         the city service
     * @param routeService        the route service
     * @param rabbitMQSender      the rabbit mq sender
     * @param messageConstructor  the message constructor
     * @param statisticService    the statistic service
     * @param orderToDTOConverter the order to dto converter
     * @param truckToDTOConverter the truck to dto converter
     * @param userToDTOConverter  the user to dto converter
     * @param cargoToDTOConverter the cargo to dto converter
     * @param cityToDTOConverter  the city to dto converter
     * @param routeToDTOConverter the route to dto converter
     */
    @Autowired
    public AdminController(OrderRepository orderRepository, TruckRepository truckRepository, UserRepository userRepository, CargoRepository cargoRepository, CityRepository cityRepository, RouteRepository routeRepository, DriverRepository driverRepository, UserService userService, OrderService orderService, CargoService cargoService, TruckService truckService, CityService cityService, RouteService routeService, RabbitMQSender rabbitMQSender, MessageConstructor messageConstructor, StatisticService statisticService, OrderToDTOConverter orderToDTOConverter, TruckToDTOConverter truckToDTOConverter, UserToDTOConverter userToDTOConverter, CargoToDTOConverter cargoToDTOConverter, CityToDTOConverter cityToDTOConverter, RouteToDTOConverter routeToDTOConverter) {
        this.orderRepository = orderRepository;
        this.truckRepository = truckRepository;
        this.userRepository = userRepository;
        this.cargoRepository = cargoRepository;
        this.cityRepository = cityRepository;
        this.routeRepository = routeRepository;
        this.driverRepository = driverRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.truckService = truckService;
        this.cityService = cityService;
        this.routeService = routeService;
        this.rabbitMQSender = rabbitMQSender;
        this.messageConstructor = messageConstructor;
        this.statisticService = statisticService;
        this.orderToDTOConverter = orderToDTOConverter;
        this.truckToDTOConverter = truckToDTOConverter;
        this.userToDTOConverter = userToDTOConverter;
        this.cargoToDTOConverter = cargoToDTOConverter;
        this.cityToDTOConverter = cityToDTOConverter;
        this.routeToDTOConverter = routeToDTOConverter;
    }

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AdminController.class);


    /**
     * Set up admin page attributes.
     *
     * @param ui the ui
     */
    public void setUpAdminPageAttributes(Model ui){
        LOGGER.info("Controller: AdminController, metod = setUpAdminPageAttributes ");
        Collection<Order> ordersPgntd = orderRepository.getOrdersForOnePage(2,0);
        if (ordersPgntd != null) {
            List<OrderWithRouteDTO> orderWithRouteDTOS = new ArrayList<OrderWithRouteDTO>();
            for (Order o : ordersPgntd) {
                try {
                    orderWithRouteDTOS.add(orderToDTOConverter.convertToDTOWithRoute(o));
                } catch (RouteException e) {
                    LOGGER.info("Controller: AdminController, metod = setUpAdminPageAttributes, catched exception:" + e.getMessage());
                    e.printStackTrace();
                }
            }
            ui.addAttribute("ordersPgntd", orderWithRouteDTOS);
        }
        else {
            ui.addAttribute("ordersPgntd", null);
        }
        // trucks
        List<Truck> trucksPgntd = (List<Truck>)truckRepository.getTrucksForOnePage(2,0);
        List<TruckDTO> truckDTOS = truckToDTOConverter.convert(trucksPgntd);
        ui.addAttribute("trucksPgntd", truckDTOS);

        List<User> users = (List<User>) userRepository.getUsersForOnePage(2,0);
        List<UserDTO> userDTOS = userToDTOConverter.convert(users);
        ui.addAttribute("usersPgntd", userDTOS);

        List<Cargo> cargos = (List<Cargo>) cargoRepository.getCargosForOnePage(2,0);
        List<CargoDTO> cargoDTOS = cargoToDTOConverter.convert(cargos);
        ui.addAttribute("cargosPgntd", cargoDTOS);

        List<City> cities = (List<City>) cityRepository.getCitiesForOnePage(2,0);
        List<CityDTO> citiesDTOS = cityToDTOConverter.convert(cities);
        ui.addAttribute("citiesPgntd", citiesDTOS);

        List<Route> routes = (List<Route>) routeRepository.getRoutesForOnePage(2,0);
        List<RouteDTO> routeDTOS = routeToDTOConverter.convert(routes);
        ui.addAttribute("routesPgntd", routeDTOS);
        LOGGER.info("Controller: AdminController, out from setUpAdminPageAttributes metod ");
    }

    /**
     * Admin main page string.
     *
     * @param id  the id
     * @param ui  the ui
     * @param req the req
     * @return the string
     */
    @RequestMapping(value = "/adminmainpage/{id}", method = RequestMethod.GET)
    public String adminMainPage(@PathVariable("id") int id, Model ui, HttpServletRequest req) {
        LOGGER.info("Controller: AdminController, metod = adminMainPage,  action = \"/adminmainpage\", request = GET");
        // orders
        setUpAdminPageAttributes(ui);

        LOGGER.info("Controller: AdminController, out from metod = adminMainPage");
        return "admin/adminmainpage";
    }




    /**
     * Gets paginated orders list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated orders list
     */
    @RequestMapping(value = "/getpaginatedorderslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedOrdersList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedOrdersList,  action = \"/getpaginatedorderslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedOrdersList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<Order> orders = (List<Order>)orderRepository.getOrdersForOnePage(pageSize, pageNum);
        List<OrderWithRouteDTO> orderDTOS = makeOrderDtoWithRouteFromOrdersList(orders);
        Gson gson = new Gson();
        //OrderDTO dto =  OrderToDTOConverter.convert(orders.get(0));
        String s = gson.toJson(orderDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedOrdersList, GSON = " + s);
        return s;
    }

    /**
     * Gets paginated trucks list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated trucks list
     */
    @RequestMapping(value = "/getpaginatedtruckslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedTrucksList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedTrucksList,  action = \"/getpaginatedtruckslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedTrucksList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<Truck> trucks = (List<Truck>)truckRepository.getTrucksForOnePage(pageSize, pageNum);
        List<TruckDTO> truckDTOS = new ArrayList<TruckDTO>();
        for(Truck t: trucks){
            truckDTOS.add(truckToDTOConverter.convert(t));
        }
        Gson gson = new Gson();
        String s = gson.toJson(truckDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedOrdersList, GSON = " + s);
        return s;
    }

    /**
     * Gets paginated users list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated users list
     */
    @RequestMapping(value = "/getpaginateduserslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedUsersList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedUsersList,  action = \"/getpaginateduserslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedUsersList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<User> users = (List<User>)userRepository.getUsersForOnePage(pageSize, pageNum);
        List<UserDTO> userDTOS = userToDTOConverter.convert(users);
        Gson gson = new Gson();
        String s = gson.toJson(userDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedUsersList, GSON = " + s);
        return s;
    }

    /**
     * Gets paginated cargos list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated cargos list
     */
    @RequestMapping(value = "/getpaginatedcargoslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedCargosList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedCargosList,  action = \"/getpaginatedcargoslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedCargosList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<Cargo> cargos = (List<Cargo>)cargoRepository.getCargosForOnePage(pageSize, pageNum);
        List<CargoDTO> cargoDTOS = cargoToDTOConverter.convert(cargos);
        Gson gson = new Gson();
        String s = gson.toJson(cargoDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedCargosList, GSON = " + s);
        return s;
    }

    /**
     * Gets paginated cities list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated cities list
     */
    @RequestMapping(value = "/getpaginatedcitieslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedCitiesList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedCitiesList,  action = \"/getpaginatedcitieslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedCitiesList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<City> cities = (List<City>)cityRepository.getCitiesForOnePage(pageSize, pageNum);
        List<CityDTO> cityDTOS = cityToDTOConverter.convert(cities);
        Gson gson = new Gson();
        String s = gson.toJson(cityDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedCitiesList, GSON = " + s);
        return s;
    }

    /**
     * Gets paginated routes list.
     *
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the paginated routes list
     */
    @RequestMapping(value = "/getpaginatedrouteslist", method = RequestMethod.GET)
    @ResponseBody
    public String getPaginatedRoutesList(@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNum){
        LOGGER.info("Controller: AdminController, metod = getPaginatedRoutesList,  action = \"/getpaginatedrouteslist\", request = GET");
        LOGGER.info("Controller: AdminController, metod = getPaginatedRoutesList, pageSize = " + pageSize + " , pageNumber = " + pageNum);
        List<Route> routes = (List<Route>)routeRepository.getRoutesForOnePage(pageSize, pageNum);
        List<RouteDTO> routeDTOS = routeToDTOConverter.convert(routes);
        Gson gson = new Gson();
        String s = gson.toJson(routeDTOS);
        LOGGER.info("Controller: AdminController, metod = getPaginatedRoutesList, GSON = " + s);
        return s;
    }

    /**
     * Gets order route for chosen cargos.
     *
     * @param vals    the vals
     * @param truckId the truck id
     * @return the order route for chosen cargos
     */
    @RequestMapping(value = "/getorderroute", method = RequestMethod.GET)
    @ResponseBody
    public String getOrderRouteForChosenCargos(@RequestParam("selectedVal") String[] vals, @RequestParam("truck") int truckId){
        LOGGER.info("Controller: AdminController, metod = getOrderRouteForChosenCargos, action = \"getorderroute\", request = GET");
        if (vals == null || vals.length == 0) {
            LOGGER.error("Controller: AdminController, out from getOrderRouteForChosenCargos method: values array is null or empty.");
            return null;
        }
        if (truckId < 0 ){
            LOGGER.error("Controller: AdminController, out from getOrderRouteForChosenCargos method: truckId is not valid");
            return null;
        }
        OrderDTO orderDTO = new OrderDTO(null,null,null,null,null,vals);
        List<City> route = null;
        Truck t = null;
        if (truckId != 0) t = truckRepository.getById(truckId);
        try {
            route = orderService.getOrderRoute(orderDTO, t);
        } catch (RouteException e) {
            e.printStackTrace();
            LOGGER.error("Controller: AdminController, out from getOrderRouteForChosenCargos method: catched exception: " + e.getMessage());
            return null;
        }
        List<CityDTO> cityDTOS = new ArrayList<CityDTO>();
        for(City city: route){
            cityDTOS.add(new CityDTO(Integer.toString(city.getId()),city.getName(), Boolean.toString(city.isHasAgency())));
        }
        Gson gson = new Gson();
        String out = gson.toJson(cityDTOS);
        return out;
    }

    /**
     * Gets drivers with hours worked over limit.
     *
     * @param vals    the vals
     * @param truckId the truck id
     * @return the drivers with hours worked over limit
     */
    @RequestMapping(value = "/getdrivershoursoverlimit", method = RequestMethod.GET)
    @ResponseBody
    public String getDriversWithHoursWorkedOverLimit(@RequestParam("selectedVal") String[] vals, @RequestParam("truck") int truckId){
        LOGGER.info("Controller: AdminController, metod = getDriversWithHoursWorkedOverLimit, action = \"getdrivershoursoverlimit\", request = GET");
        if (vals == null || vals.length == 0) {
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: values array is null or empty.");
            return null;
        }
        if (truckId < 0 ){
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: truckId is not valid");
            return null;
        }
        Truck t = null;
        if (truckId!=0) t = truckRepository.getById(truckId);
        if (t == null){
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: truck is null.");
            return null;
        }
        if (t.getDriversInTruck() ==null || t.getDriversInTruck().isEmpty()){
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: there are no assigned drivers.");
            return "{\"result\":\"ok\", \"driversInTruck\":\"empty\"}";
        }
        OrderDTO orderDTO = new OrderDTO(null,null,null,null,Integer.toString(truckId),vals);
        double orderExecutingTime = 0;
        try {
            orderExecutingTime = orderService.getExecutingTime(orderDTO);
        } catch (RouteException e) {
            e.printStackTrace();
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: catched exception: " + e.getMessage());
            return null;
        }
        if (orderExecutingTime == 0) {
            LOGGER.error("Controller: AdminController, out from getDriversWithHoursWorkedOverLimit method: counted order executing time is 0");
            return null;
        }
        ReturnValuesContainer<List<Driver>> driversHoursOverLimit = orderService.checkIfDriversHoursWorkedOverLimit(orderExecutingTime, new Date(),t.getDriversInTruck());
        if (driversHoursOverLimit.getUpdateMessageType().equals(UpdateMessageType.ALL_DRIVERS_HOURS_WORKED_VALID)){
            return "{\"result\":\"ok\"}";
        }
        else {
            Gson gson = new Gson();
            List<Driver> drivers = driversHoursOverLimit.getReturnedValue();
            List<DriverDTO> driverDTOS = new ArrayList<DriverDTO>();
            for(Driver d: drivers){
                User u = d.getUser();
                driverDTOS.add(new DriverDTO(null,u.getName(),u.getMiddleName(),u.getLastName(),u.getPersonalNumber(),null, Double.toString(d.getHoursWorked()),null,null,null,null));
            }
            String out = gson.toJson(driverDTOS);
            return out;
        }
    }

    private List<OrderWithRouteDTO> makeOrderDtoWithRouteFromOrdersList(List<Order> orders){
        LOGGER.info("Controller: AdminController, metod = makeOrderDtoWithRouteFromOrdersList");
        if (orders == null) {
            LOGGER.info("Controller: AdminController, out from makeOrderDtoWithRouteFromOrdersList method: input orders list is null");
            return null;
        }
        List<OrderWithRouteDTO> orderDTOS = new ArrayList<OrderWithRouteDTO>();
        for(Order o: orders){
            try {
                orderDTOS.add(orderToDTOConverter.convertToDTOWithRoute(o));
            } catch (RouteException e) {
                LOGGER.info("Controller: AdminController, metod = makeOrderDtoWithRouteFromOrdersList, catched exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
        LOGGER.info("Controller: AdminController, metod = makeOrderDtoWithRouteFromOrdersList, result collection: " + orderDTOS);
        LOGGER.info("Controller: AdminController, out from makeOrderDtoWithRouteFromOrdersList");
        return orderDTOS;
    }

    /**
     * Gets top orders.
     *
     * @param size the size
     * @return the top orders
     */
    @RequestMapping(value = "/gettoporders")
    @ResponseBody
    public String getTopOrders(@RequestParam(name = "size")  int size){
        LOGGER.info("Controller: AdminController, metod = getTopOrders,  action = \"/gettoporders\", request = GET");
        if (size == 0) return null;
        List<Order> orders = (List<Order>)orderRepository.getTopNonExecutedOrders(size);

        List<GoogleMarkerDTO> googleMarkerDTOS = new ArrayList<GoogleMarkerDTO>();
        for(Order o: orders){
            try {
                googleMarkerDTOS.add( orderToDTOConverter.convertToGoogleMarkerDto(o));
            } catch (RouteException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        String res = gson.toJson(googleMarkerDTOS);
        LOGGER.info("Controller: AdminController, metod = getTopOrders, result json string: " + res);
        LOGGER.info("Controller: AdminController, out from getTopOrders method");
        return res;
    }

    /**
     * Delete order by id string.
     *
     * @param orderId the order id
     * @return the string
     */
    @RequestMapping(value = "/deleteorder", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrderById(@RequestParam(name = "orderId") int orderId){
        LOGGER.info("Controller: AdminController, metod = deleteOrderById,  action = \"/deleteorder\", request = GET");
        if (orderId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteOrderById: orderId is invalid");
            return null;
        }
        Order deleted = orderRepository.getById(orderId);
        UpdateMessageType result =  orderService.deleteOrder(orderId);
        if (result.equals(UpdateMessageType.ORDER_DELETED)) rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.ORDER_DELETED,deleted));
        return new Gson().toJson(result);
    }

    /**
     * Delete truck by id string.
     *
     * @param truckId the truck id
     * @return the string
     */
    @RequestMapping(value = "/deletetruck", method = RequestMethod.POST)
    @ResponseBody
    public String deleteTruckById(@RequestParam(name = "truckId") int truckId){
        LOGGER.info("Controller: AdminController, metod = deleteTruckById,  action = \"/deletetruck\", request = POST");
        if (truckId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteOrderById: orderId is invalid");
            return null;
        }
        Truck deleted = truckRepository.getById(truckId);
        UpdateMessageType result =  truckService.deleteTruck(truckId);
        String s = new Gson().toJson(result);
        if (result.equals(UpdateMessageType.TRUCK_DELETED)) rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.TRUCK_DELETED,deleted));
        return s;
    }

    /**
     * Delete user by id string.
     *
     * @param userId the user id
     * @return the string
     */
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUserById(@RequestParam(name = "userId") int userId){
        LOGGER.info("Controller: AdminController, metod = deleteUserById,  action = \"/deleteuser\", request = POST");
        if (userId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteUserById: orderId is invalid");
            return null;
        }
        User deleted = userRepository.getById(userId);
        UpdateMessageType result =  userService.deleteUser(userId);
        String s = new Gson().toJson(result);
        if (result.equals(UpdateMessageType.USER_DELETED)) rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.USER_DELETED,deleted));
        return s;
    }

    /**
     * Delete cargo by id string.
     *
     * @param cargoId the cargo id
     * @return the string
     */
    @RequestMapping(value = "/deletecargo", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCargoById(@RequestParam(name = "cargoId") int cargoId){
        LOGGER.info("Controller: AdminController, metod = deleteCargoById,  action = \"/deletecargo\", request = POST");
        if (cargoId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteCargoById: cargoId is invalid");
            return null;
        }
        UpdateMessageType result =  cargoService.deleteCargo(cargoId,0);
        String s = new Gson().toJson(result);
        LOGGER.info("Controller: AdminController, out from deleteCargoById method,  result: " + s);
        return s;
    }

    /**
     * Delete city by id string.
     *
     * @param cityId the city id
     * @return the string
     */
    @RequestMapping(value = "/deletecity", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCityById(@RequestParam(name = "cityId") int cityId){
        LOGGER.info("Controller: AdminController, metod = deleteCityById,  action = \"/deletecity\", request = POST");
        if (cityId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteCityById: cargoId is invalid");
            return null;
        }
        UpdateMessageType result =  cityService.deleteCity(cityId,0);
        String s = new Gson().toJson(result);
        LOGGER.info("Controller: AdminController, out from deleteCityById method,  result: " + s);
        return s;
    }

    /**
     * Delete route by id string.
     *
     * @param routeId the route id
     * @return the string
     */
    @RequestMapping(value = "/deleteroute", method = RequestMethod.POST)
    @ResponseBody
    public String deleteRouteById(@RequestParam(name = "routeId") int routeId){
        LOGGER.info("Controller: AdminController, metod = deleteRouteById,  action = \"/deleteroute\", request = POST");
        if (routeId <= 0){
            LOGGER.info("Controller: AdminController, out from deleteRouteById: routeId is invalid");
            return null;
        }
        UpdateMessageType result =  routeService.deleteRoute(routeId,0);
        String s = new Gson().toJson(result);
        LOGGER.info("Controller: AdminController, out from deleteCityById method,  result: " + s);
        return s;
    }


    /**
     * Get google map string.
     *
     * @return the string
     */
    @RequestMapping(value = "/adminmainpagegoogle", method = RequestMethod.GET)
    public String getGoogleMap(){
        return "/admin/adminmainpagegoogle";
    }

    private int parseId(OrderDTO orderDTO){
        int id = 0;
        try {
            id = Integer.parseInt(orderDTO.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return id;
    }


    /**
     * Admin main page post string.
     *
     * @param action        the action
     * @param orderDTO      the order dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
// todo: replace OrderDTO with IdDTO !
    @RequestMapping(value = "/adminmainpage/{id}", method = RequestMethod.POST)
    public String adminMainPagePost(@PathVariable("id") int action, OrderDTO orderDTO, BindingResult bindingResult, Model ui) {
        LOGGER.info("Controller: AdminController, metod = adminMainPage,  action = \"/adminmainpage\", request = POST");
        if (orderDTO == null) {
            LOGGER.error("Error: order Data Access object is empty!");
            ui.addAttribute("actionFailed", "Error: order Data Access object is empty");
            return "failure";
        }
        if (action == 1) {
            LOGGER.info("Trying to edit order");
            int id = parseId(orderDTO);
            Order updated = null;
            if (id != 0) updated = orderRepository.getById(id);
            else {
                LOGGER.error("Error: order id value is zero!");
                ui.addAttribute("actionFailed", "Error: order id value is zero!");
                return "failure";
            }
            if (updated != null) {
                if (!updated.getStatus().equals(OrderStatus.NOT_PREPARED)){
                    String errMessage = "Error: can not edit executing/executed order";
                    LOGGER.error(errMessage);
                    ui.addAttribute("actionFailed", errMessage);
                    return "failure";
                }
                Collection<Cargo> availableCargos = cargoService.getAvailableCargos();
                Collection<Cargo> cargos = new ArrayList<Cargo>();
                cargos.addAll(availableCargos);
                cargos.addAll(updated.getCargosInOrder());
                ui.addAttribute("availableCargos", cargos);
                ui.addAttribute("updatedOrder", updated);
                return "/admin/orderchangepage";
            }
            LOGGER.error("Error: no such order in database");
            ui.addAttribute("actionFailed", "Error: no such order in database");
            return "failure";
        }
        if (action == 2) {
            // delete order
            Order deleted = null;
            int id = parseId(orderDTO);
            if (id != 0) deleted = orderRepository.getById(id);
            else {
                LOGGER.error("Error: order id value is zero!");
                ui.addAttribute("actionFailed", "Error: order id value is zero!");
                return "failure";
            }
            if (deleted != null) {
                UpdateMessageType result = orderService.deleteOrder(orderDTO);
                if (result.equals(UpdateMessageType.ORDER_DELETED)) {
                    LOGGER.info("Order deleted successfully!");
                    ui.addAttribute("actionSuccess", "Order deleted successfully!");
                    rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.ORDER_DELETED, deleted));
                    return "success";
                } else {
                    LOGGER.error("Error: deleteOrder method in OrderService returned false.");
                    ui.addAttribute("actionFailed", "Error while trying to delete order.");
                    return "failure";
                }
            }
        }
        if (action == 3) {
            // edit truck
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: truck id value is zero!");
                ui.addAttribute("actionFailed", "Error: truck id value is zero!");
                return "failure";
            }
            Truck truck = truckRepository.getById(id);
            Collection<City> cities = cityRepository.getAll();
            Collection<User> drivers = userService.getAllDrivers();
            ui.addAttribute("updatedTruckId", id);
            ui.addAttribute("updatedTruck", truck);
            ui.addAttribute("citiesList", cities);
            ui.addAttribute("driversList", drivers);
            return "/manager/truckchangepage";
        }
        if (action == 4) {
            // delete truck
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: truck id value is zero!");
                ui.addAttribute("actionFailed", "Error: truck id value is zero!");
                return "failure";
            }
            UpdateMessageType result = truckService.deleteTruck(id);
            if (result.equals(UpdateMessageType.TRUCK_DELETED)) {
                LOGGER.info("Truck deleted successfully!");
                ui.addAttribute("actionSuccess", "Truck deleted successfully!");
                rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.TRUCK_DELETED, statisticService));
                return "success";
            } else {
                LOGGER.error("Error: method deleteTruck in TruckService returned false.");
                ui.addAttribute("actionFailed", "Error: method deleteTruck in TruckService returned false.");
                return "failure";
            }
        }
        if (action == 5){
            int id = parseId(orderDTO);
            if (id == 0){
                LOGGER.error("Error: user id value is zero!");
                ui.addAttribute("actionFailed", "Error: user id value is zero!");
                return "failure";
            }
            User updated = userRepository.getById(id);
            if (updated == null){
                LOGGER.error("Error: no such user in database!");
                ui.addAttribute("actionFailed", "Error: no such user in database!");
                return "failure";
            }
            ui.addAttribute("updatedUser", updated);

            Collection<City> cities = cityRepository.getAll();
            ui.addAttribute("citiesList", cities);
            Collection<Truck> availableTrucks = truckService.getFreeTrucks();
            if (updated.getDriver()!=null){
                if (updated.getDriver().getCurrentTruck()!=null){
                    availableTrucks.add(updated.getDriver().getCurrentTruck());
                }
            }
            ui.addAttribute("trucksList", availableTrucks);
            return "/admin/userchangepage";
        }
        if (action == 6){
            // delete user
            int id = parseId(orderDTO);
            if (id == 0){
                LOGGER.error("Error: user id value is zero!");
                ui.addAttribute("actionFailed", "Error: user id value is zero!");
                return "failure";
            }
            UpdateMessageType result = userService.deleteUser(id);
            if (result.equals(UpdateMessageType.USER_DELETED)){
                LOGGER.info("User deleted successfully!");
                ui.addAttribute("actionSuccess","User deleted successfully!");
                rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.USER_DELETED, statisticService));
                return "success";
            }
            else {
                LOGGER.error("Error: deleteUser method in UserService returned false!");
                ui.addAttribute("actionFailed","Error: deleteUser method in UserService returned false!");
                return "failure";
            }
        }
        if (action == 7){
            int id = parseId(orderDTO);
            if (id == 0){
                LOGGER.error("Error: cargo id value is zero!");
                ui.addAttribute("actionFailed", "Error: cargo id value is zero!");
                return "failure";
            }
            ui.addAttribute("updatedCargoId", id);
            Collection<City> citiesList = cityRepository.getAll();
            ui.addAttribute("citiesList", citiesList);
            return "/manager/cargochangepage";
        }
        if (action == 8){
            int id = parseId(orderDTO);
            if (id == 0){
                LOGGER.error("Error: cargo id value is zero!");
                ui.addAttribute("actionFailed", "Error: cargo id value is zero!");
                return "failure";
            }
            boolean result = cargoService.deleteCargo(id);
            if (result){
                LOGGER.info("Cargo deleted successfully!");
                ui.addAttribute("actionSuccess", "Cargo deleted successfully!");
                return "success";
            }
            else {
                LOGGER.error("Error: deleteCargo method in CargoService returned false!");
                ui.addAttribute("actionFailed", "Error: deleteCargo method in CargoService returned false!");
                return "failure";
            }
        }
        if (action == 9){
            // edit city
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: city id value is zero!");
                ui.addAttribute("actionFailed", "Error: city id value is zero!");
                return "failure";
            }
            ui.addAttribute("changedCityId", id);
            ui.addAttribute("changedCity", cityRepository.getById(id));
            return "/admin/changecitypage";
        }
        if (action == 10){
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: city id value is zero!");
                ui.addAttribute("actionFailed", "Error: city id value is zero!");
                return "failure";
            }
            boolean result = false;
            try {
                result = cityService.deleteCity(id);
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: there are some drivers or trucks in this city. They need to be unassigned from city for successful remove.");
                ui.addAttribute("actionFailed", "Error: there are some drivers or trucks in this city. They need to be unassigned from city for successful remove.");
                return "failure";
            }
            if (result){
                LOGGER.info("City deleted successfully!");
                ui.addAttribute("actionSuccess", "City deleted successfully!");
                return "success";
            }
            else {
                LOGGER.error("Error: deleteCity method in CityService returned false!");
                ui.addAttribute("actionFailed", "Error: deleteCity method in CityService returned false!");
                return "failure";
            }
        }
        if (action == 11){
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: route id value is zero!");
                ui.addAttribute("actionFailed", "Error: route id value is zero!");
                return "failure";
            }
            ui.addAttribute("updatedRouteId", id);
            ui.addAttribute("updatedRoute", routeRepository.getById(id));
            Collection<City> cities = cityRepository.getAll();
            ui.addAttribute("citiesList", cities);
            return "/admin/changeroutepage";

        }
        if (action == 12){
            int id = parseId(orderDTO);
            if (id == 0) {
                LOGGER.error("Error: route id value is zero!");
                ui.addAttribute("actionFailed", "Error: route id value is zero!");
                return "failure";
            }
            boolean result = false;
            try {
                result = routeService.deleteRoute(id);
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: there are some cargos with this route. They need to be unassigned from route for successful remove.");
                ui.addAttribute("actionFailed", "Error: there are some cargos with this route. They need to be unassigned from route for successful remove.");
                return "failure";
            }
            if (result){
                LOGGER.info("Route deleted successfully!");
                ui.addAttribute("actionSuccess", "Route deleted successfully!");
                return "success";
            }
            else {
                LOGGER.error("Error: deleteRoute method in RouteService returned false!");
                ui.addAttribute("actionFailed", "Error: deleteRoute method in RouteService returned false!");
                return "failure";
            }
        }

        LOGGER.error("Error: no such action!");
        ui.addAttribute("actionFailed", "Error no such action!");
        return "failure";
    }

    /**
     * Change route string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/changeroutepage", method = RequestMethod.GET)
    public String changeRoute(Model ui){
        LOGGER.info("Controller: AdminController, metod = changeRoute,  action = \"/changeroutepage\", request = GET");
        Collection<City> cities = cityRepository.getAll();
        ui.addAttribute("citiesList", cities);
        return "/admin/changeroutepage";
    }

    /**
     * Change route post string.
     *
     * @param routeDTO      the route dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/changeroutepage", method = RequestMethod.POST)
    public String changeRoutePost(RouteDTO routeDTO, BindingResult bindingResult,Model ui){
        LOGGER.info("Controller: AdminController, metod = changeRoute,  action = \"/changeroutepage\", request = POST");
        if (routeDTO == null){
            LOGGER.error("Error: route Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: route Data Transfer Object is null!");
            return "failure";
        }
        boolean result = routeService.updateRoute(routeDTO);
        if (result){
            LOGGER.info("Route successfully updated!");
            ui.addAttribute("actionSuccess", "Route successfully updated!");
            return "success";
        }
        else {
            LOGGER.error("Error: updateRoute method in RouteService returned false!");
            ui.addAttribute("actionFailed","Error:  updateRoute method in RouteService returned false!" );
            return "failure";
        }
    }


    /**
     * User change page string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/userchangepage", method = RequestMethod.GET)
    public String userChangePage(Model ui){
        LOGGER.info("Controller: AdminController, metod = userChangePage,  action = \"/userchangepage\", request = GET");
        Collection<City> cities = cityRepository.getAll();
        ui.addAttribute("citiesList", cities);
        Collection<Truck> availableTrucks = truckService.getFreeTrucks();
        ui.addAttribute("updatedUser", null);
        return "/admin/userchangepage";
    }

    /**
     * User change page post string.
     *
     * @param userDTO       the user dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/userchangepage", method = RequestMethod.POST)
    public String userChangePagePost(UserDTO userDTO, BindingResult bindingResult,Model ui){
        LOGGER.info("Controller: AdminController, metod = userChangePagePost,  action = \"/userchangepage\", request = POST");
        if (userDTO == null) {
            LOGGER.error("Error: user Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: user Data Transfer Object is null!");
            return "failure";
        }
        UpdateMessageType result = userService.updateUser(userDTO);
        if (result.equals(UpdateMessageType.USER_EDITED)){
            LOGGER.info("User successfully updated!");
            ui.addAttribute("actionSuccess", "User successfully updated!");
            rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.USER_EDITED, statisticService));
            return "success";
        }
        else {
            LOGGER.error("Error: updateUser method in UserService returned false!");
            ui.addAttribute("actionFailed","Error: updateUser method in UserService returned false!" );
            return "failure";
        }
    }


    /**
     * Order change page string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/orderchangepage", method = RequestMethod.GET)
    public String orderChangePage(Model ui) {
        LOGGER.info("Controller: AdminController, metod = orderChangePage,  action = \"/orderchangepage\", request = GET");
        return "/admin/orderchangepage";
    }

    /**
     * Order change page post string.
     *
     * @param orderDTO      the order dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
//todo: refactor!!
    @RequestMapping(value = "/orderchangepage", method = RequestMethod.POST)
    public String orderChangePagePost(OrderDTO orderDTO, BindingResult bindingResult, Model ui) {
        LOGGER.info("Controller: AdminController, metod = orderChangePage,  action = \"/orderchangepage\", request = POST");
        if (orderDTO == null) {
            LOGGER.error("Error: order Data Access object is empty!");
            ui.addAttribute("actionFailed", "Error: order Data Access object is empty");
            return "failure";
        }
        ui.addAttribute("updatedOrderDTO", orderDTO);
        Order order = null;
        if (orderDTO.getCargosInOrder() == null || orderDTO.getCargosInOrder().length == 0 || orderDTO.getCargosInOrder()[0].equals("No cargos available")) {
            int id = 0;
            try {
                id = Integer.parseInt(orderDTO.getId());
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("Error: order id value is not valid!");
                ui.addAttribute("actionFailed", "Error: order id value is not valid!");
                return "failure";
            }
            OrderDTO newOrderDTO = null;
            if (id != 0) {
                order = orderRepository.getById(id);
                if (order != null) {
                    List<Cargo> currentCargosInOrder = new ArrayList<Cargo>(order.getCargosInOrder());
                    String[] newCargosInOrder = new String[currentCargosInOrder.size()];
                    for (int i = 0; i < newCargosInOrder.length; i++) {
                        newCargosInOrder[i] = Integer.toString(currentCargosInOrder.get(i).getId());
                    }
                    newOrderDTO = new OrderDTO(
                            orderDTO.getId(),
                            orderDTO.getPersonalNumber(),
                            orderDTO.getDescription(),
                            orderDTO.getStatus(),
                            orderDTO.getAssignedTruckId(),
                            newCargosInOrder);
                    Collection<Truck> availableTrucks = null;
                    try {
                        availableTrucks = orderService.getAvailableTrucks(newOrderDTO);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if (availableTrucks == null) availableTrucks = new ArrayList<Truck>();
                    availableTrucks.add(order.getAssignedTruck());
                    Collection<Cargo> chosenCargos = orderService.getChosenCargos(newOrderDTO);
                    ui.addAttribute("availableTrucks", availableTrucks);
                    ui.addAttribute("chosenCargos", chosenCargos);
                } else {
                    LOGGER.error("Error: no such order!");
                    ui.addAttribute("actionFailed", "Error: no such order!");
                    return "failure";
                }
            } else {
                LOGGER.error("Error: order id value is not valid!");
                ui.addAttribute("actionFailed", "Error: order id value is not valid!");
                return "failure";
            }

        } else {
            Collection<Truck> availableTrucks = null;
            try {
                availableTrucks = orderService.getAvailableTrucks(orderDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Collection<Cargo> chosenCargos = orderService.getChosenCargos(orderDTO);
            ui.addAttribute("availableTrucks", availableTrucks);
            ui.addAttribute("chosenCargos", chosenCargos);
        }
        return "/admin/reassigntrucktoorderpage";
    }


    /**
     * Reassign truck to order page string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/reassigntrucktoorderpage", method = RequestMethod.GET)
    public String reassignTruckToOrderPage(Model ui) {
        LOGGER.info("Controller: AdminController, metod = reassignTruckToOrderPage,  action = \"/reassigntrucktoorderpage\", request = GET");

        return "/admin/reassigntrucktoorderpage";
    }

    /**
     * Reassign truck to order page post string.
     *
     * @param orderDTO      the order dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/reassigntrucktoorderpage", method = RequestMethod.POST)
    public String reassignTruckToOrderPagePost(OrderDTO orderDTO, BindingResult bindingResult, Model ui) {
        LOGGER.info("Controller: AdminController, metod = reassignTruckToOrderPagePost,  action = \"/reassigntrucktoorderpage\", request = POST");
        if (orderDTO == null) {
            LOGGER.error("Error: order Data Access object is empty!");
            ui.addAttribute("actionFailed", "Error: order Data Access object is empty");
            return "failure";
        }
        ReturnValuesContainer<Order> result = null;
        try {
            result = orderService.updateOrder(orderDTO,0);
        }
        catch (Exception e){
            LOGGER.error("Error: " + e.getMessage());
            ui.addAttribute("actionFailed", "Error: " + e.getMessage());
            return "failure";
        }
        if (result.getUpdateMessageType().equals(UpdateMessageType.ORDER_EDITED)) {
            LOGGER.info("Order successfully edited!");
            ui.addAttribute("actionSuccess", "Order successfully edited!");
            rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.ORDER_EDITED,result.getReturnedValue()));
            return "success";
        } else {
            LOGGER.error("Error: updateOrder method in OrderService returned false.");
            ui.addAttribute("actionFailed", "Error while trying to update order.");
            return "failure";
        }
    }


    /**
     * Add new user page string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/addnewuserpage", method = RequestMethod.GET)
    String addNewUserPage(Model ui) {
        LOGGER.info("Controller: AdminController, metod = addNewUserPage,  action = \"/addnewuserpage\", request = GET");
        Collection<UserRole> userRoles = userService.getRoles();
        ui.addAttribute("userRoles", userRoles);
        Collection<City> cities = cityRepository.getAll();
        ui.addAttribute("citiesList", cities);
        Collection<Truck> availableTrucks = truckService.getFreeTrucks();
        ui.addAttribute("trucksList", availableTrucks);
        return "/admin/addnewuserpage";
    }

    /**
     * Add new user page post string.
     *
     * @param userDTO       the user dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/addnewuserpage", method = RequestMethod.POST)
    String addNewUserPagePost(UserDTO userDTO, BindingResult bindingResult, Model ui) {
        LOGGER.info("Controller: AdminController, metod = addNewUserPage,  action = \"/addnewuserpage\", request = POST");
        if (userDTO == null) {
            LOGGER.error("Error: user Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: user Data Transfer Object is null!");
            return "failure";
        }
        UpdateMessageType result = userService.createUser(userDTO);
        if (result.equals(UpdateMessageType.USER_CREATED)) {
            LOGGER.info("New " + userDTO.getRole().toLowerCase() + " created successfully");
            ui.addAttribute("actionSuccess", "New " + userDTO.getRole().toLowerCase() + " created successfully");
            rabbitMQSender.sendMessage(messageConstructor.createMessage(UpdateMessageType.USER_CREATED, statisticService));
            return "success";
        } else {
            LOGGER.error("Error: createUser method in UserService returned false!");
            ui.addAttribute("actionFailed", "Error: createUser method in UserService returned false!");
            return "failure";
        }
    }

    /**
     * Add new city string.
     *
     * @return the string
     */
    @RequestMapping(value = "/addnewcitypage", method = RequestMethod.GET)
    public String addNewCity(){
        LOGGER.info("Controller: AdminController, metod = addNewCityPage,  action = \"/addnewcitypage\", request = GET");
        return "/admin/addnewcitypage";
    }

    /**
     * Add new city post string.
     *
     * @param cityDTO       the city dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/addnewcitypage", method = RequestMethod.POST)
    public String addNewCityPost(CityDTO cityDTO, BindingResult bindingResult, Model ui){
        LOGGER.info("Controller: AdminController, metod = addNewCityPage,  action = \"/addnewcitypage\", request = POST");
        if (cityDTO == null){
            LOGGER.error("Error: city Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: city Data Transfer Object is null!");
            return "failure";
        }
        boolean result = cityService.createCity(cityDTO);
        if (result){
            LOGGER.info("City created successfully!");
            ui.addAttribute("actionSuccess", "City created successfully!");
            return "success";
        }
        else {
            LOGGER.error("Error: createCity method in CityService returned false!");
            ui.addAttribute("actionFailed", "Error: createCity method in CityService returned false!");
            return "failure";
        }
    }

    /**
     * Change city string.
     *
     * @return the string
     */
    @RequestMapping(value = "/changecitypage", method = RequestMethod.GET)
    public String changeCity(){
        LOGGER.info("Controller: AdminController, metod = changeCityPage,  action = \"/addnewcitypage\", request = GET");
        return "/admin/changecitypage";
    }

    /**
     * Change city post string.
     *
     * @param cityDTO       the city dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/changecitypage", method = RequestMethod.POST)
    public String changeCityPost(CityDTO cityDTO, BindingResult bindingResult, Model ui){
        LOGGER.info("Controller: AdminController, metod = changeCityPage,  action = \"/changecitypage\", request = POST");
        if (cityDTO == null){
            LOGGER.error("Error: city Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: city Data Transfer Object is null!");
            return "failure";
        }
        boolean result = cityService.updateCity(cityDTO);
        if (result){
            LOGGER.info("City updated successfully!");
            ui.addAttribute("actionSuccess", "City updated successfully!");
            return "success";
        }
        else {
            LOGGER.error("Error: updateCity method in CityService returned false!");
            ui.addAttribute("actionFailed", "Error: updateCity method in CityService returned false!");
            return "failure";
        }
    }

    /**
     * Add new route string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = "/addnewroutepage", method = RequestMethod.GET)
    public String addNewRoute(Model ui){
        LOGGER.info("Controller: AdminController, metod = addNewRoute,  action = \"/addnewroutepage\", request = GET");
        Collection<City> citiesList = cityRepository.getAll();
        ui.addAttribute("citiesList", citiesList);
        return "/admin/addnewroutepage";
    }

    /**
     * Add new route post string.
     *
     * @param routeDTO      the route dto
     * @param bindingResult the binding result
     * @param ui            the ui
     * @return the string
     */
    @RequestMapping(value = "/addnewroutepage", method = RequestMethod.POST)
    public String addNewRoutePost(RouteDTO routeDTO, BindingResult bindingResult, Model ui){
        LOGGER.info("Controller: AdminController, metod = addNewRoute,  action = \"/addnewroutepage\", request = POST");
        if (routeDTO == null){
            LOGGER.error("Error: route Data Transfer Object is null!");
            ui.addAttribute("actionFailed", "Error: route Data Transfer Object is null!");
            return "failure";
        }
        boolean result = routeService.createRoute(routeDTO);
        if (result){
            LOGGER.info("Route created successfully!");
            ui.addAttribute("actionSuccess", "Route created successfully!");
            return "success";
        }
        else {
            LOGGER.error("Error: createRoute method in RouteService returned false!");
            ui.addAttribute("actionFailed", "Error: createRoute method in RouteService returned false!");
            return "failure";
        }
    }

}