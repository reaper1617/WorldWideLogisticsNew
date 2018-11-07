package com.gerasimchuk.controllers;


import com.gerasimchuk.converters.OrderToDTOConverter;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.OrderWithRouteDTO;
import com.gerasimchuk.dto.StatsDTO;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.repositories.OrderRepository;
import com.gerasimchuk.services.interfaces.StatisticService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * The type Rest controller.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private OrderRepository orderRepository;
    private OrderToDTOConverter orderToDTOConverter;
    private StatisticService statisticService;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RestController.class);

    /**
     * Instantiates a new Rest controller.
     *
     * @param orderRepository     the order repository
     * @param orderToDTOConverter the order to dto converter
     * @param statisticService    the statistic service
     */
    @Autowired
    public RestController(OrderRepository orderRepository, OrderToDTOConverter orderToDTOConverter, StatisticService statisticService) {
        this.orderRepository = orderRepository;
        this.orderToDTOConverter = orderToDTOConverter;
        this.statisticService = statisticService;
    }

    /**
     * Get last orders string.
     *
     * @return the string
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    public String getLastOrders(){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getLastOrders");
        Collection<Order> orders = orderRepository.getLastOrders(10);
        Collection<OrderWithRouteDTO> orderWithRouteDTOS = null;
        try {
            orderWithRouteDTOS = orderToDTOConverter.convertToDTOWithRouteCollection(orders);
        } catch (RouteException e) {
            e.printStackTrace();
            LOGGER.error("Class: " + this.getClass().getName() + " out from getLastOrders method: catched exception " + e.getMessage());
            return null;
        }
        Gson gson = new Gson();
        String res = gson.toJson(orderWithRouteDTOS);
        return res;
    }

    /**
     * Get stats string.
     *
     * @return the string
     */
    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    @ResponseBody
    public String getStats(){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getStats");

        StatsDTO statsDTO = new StatsDTO(statisticService.getNumOfTrucksTotal(),
                statisticService.getNumOfTrucksFree(),
                statisticService.getNumOfTrucksNotReady(),
                statisticService.getNumOfTrucksExecutingOrders(),
                statisticService.getNumOfDriversTotal(),
                statisticService.getNumOfDriversFree(),
                statisticService.getNumOfDriversExecutingOrders());
        Gson gson = new Gson();
        String res = gson.toJson(statsDTO,StatsDTO.class);
        return res;
    }

}
