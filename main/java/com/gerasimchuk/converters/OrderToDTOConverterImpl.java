package com.gerasimchuk.converters;

import com.gerasimchuk.dto.GoogleMarkerDTO;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.dto.OrderWithRouteDTO;
import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.enums.OrderStatus;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.services.interfaces.OrderService;
import com.gerasimchuk.utils.OrderWithRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Order to Data Transfer Object converter
 *
 * @author Reaper
 * @version 1.0
 */
@Component
public class OrderToDTOConverterImpl implements OrderToDTOConverter{

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(OrderToDTOConverterImpl.class);


    private OrderService orderService;

    /**
     * Instantiates a new Order to dto converter.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderToDTOConverterImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Convert order dto.
     *
     * @param order the order
     * @return the order dto
     */
    public static OrderDTO convert(Order order){
        LOGGER.info("Class: OrderToDTOConvert, method: convert");
        LOGGER.info("Trying to convert order " + order.getDescription() + " to OrderDTO");
        String id = Integer.toString(order.getId());
        LOGGER.info("Id field is: " + id);
        String personalNumber = order.getPersonalNumber();
        LOGGER.info("personalNumber  field is: " + personalNumber);
        String description = order.getDescription();
        LOGGER.info("description field is: " + description);
        String date = order.getDate();
        LOGGER.info("date field is: " + date);
        String status = getOrderStatus(order);
        LOGGER.info("status  field is: " + status);
        String assignedTruckId = null;
        String assignedTruckRegNum = null;
        if (order.getAssignedTruck() != null) {
            assignedTruckId = Integer.toString(order.getAssignedTruck().getId());
            assignedTruckRegNum = order.getAssignedTruck().getRegistrationNumber();
        }
        LOGGER.info("assignedTruckId field is: " + assignedTruckId);
        String[] cargosInOrder = null;
        if (order.getCargosInOrder() != null && !order.getCargosInOrder().isEmpty()) {
            cargosInOrder = new String[order.getCargosInOrder().size()];
            Object[] cargosInOrderArray = order.getCargosInOrder().toArray();
            if (cargosInOrderArray == null || cargosInOrderArray.length == 0) return null;
            for (int i = 0; i < cargosInOrder.length; i++) {
                cargosInOrder[i] = Integer.toString(((Cargo) cargosInOrderArray[i]).getId());
            }
        }
        LOGGER.info("cargosInOrder field is: " + cargosInOrder);

        String[] assignedDrivers = null;
        if (order.getAssignedTruck()!=null){
            Set<Driver> driversInTruck = order.getAssignedTruck().getDriversInTruck();

            if (driversInTruck!=null && !driversInTruck.isEmpty()){
                int size = driversInTruck.size();
                assignedDrivers = new String[size];
                int i = 0;
                for(Driver d: driversInTruck){
                    assignedDrivers[i] = d.getUser().getName() + " " + d.getUser().getMiddleName() + " " + d.getUser().getLastName();
                    i++;
                }
            }
        }
        LOGGER.info("assignedDrivers field is: " + assignedDrivers);
        OrderDTO orderDTO = new OrderDTO(id,personalNumber,description,date,status,assignedTruckId,assignedTruckRegNum,cargosInOrder,assignedDrivers);
        LOGGER.info("Created orderDTO: " + orderDTO);
        return orderDTO;
    }

    public OrderWithRouteDTO convertToDTOWithRoute(Order order) throws RouteException {
        OrderDTO dto = convert(order);
        String[] route = null;
        List<City> cities = orderService.getOrderRoute(OrderToDTOConverterImpl.convert(order), order.getAssignedTruck());
        int size = cities.size();
        route = new String[size];
        for(int i = 0; i < size; i++){
            route[i] = cities.get(i).getName();
        }
        assert dto != null;
        return new OrderWithRouteDTO(
                dto.getId(),
                dto.getPersonalNumber(),
                dto.getDescription(),
                dto.getDate(),
                dto.getStatus(),
                dto.getAssignedTruckId(),
                dto.getAssignedTruckRegistrationNumber(),
                dto.getCargosInOrder(),
                dto.getAssignedDrivers(), route);
    }

    @Override
    public GoogleMarkerDTO convertToGoogleMarkerDto(Order order) throws RouteException {
        OrderWithRouteDTO orderWithRouteDTO = convertToDTOWithRoute(order);
        String currentCity = null;
        if (order.getAssignedTruck() != null) {
            currentCity = order.getAssignedTruck().getCurrentCity().getName();
        }
        return new GoogleMarkerDTO(orderWithRouteDTO.getId(),
                orderWithRouteDTO.getPersonalNumber(),
                orderWithRouteDTO.getDescription(),
                orderWithRouteDTO.getDate(),
                orderWithRouteDTO.getStatus(),
                orderWithRouteDTO.getAssignedTruckId(),
                orderWithRouteDTO.getAssignedTruckRegistrationNumber(),
                orderWithRouteDTO.getCargosInOrder(),
                orderWithRouteDTO.getAssignedDrivers(),
                orderWithRouteDTO.getRoute(), currentCity);
    }

    @Override
    public Collection<OrderWithRouteDTO> convertToDTOWithRouteCollection(Collection<Order> orders) throws RouteException {
        LOGGER.info("Class: " + this.getClass() + ", method: convertToDTOWithRouteCollection");
        if (orders == null || orders.isEmpty()){
            LOGGER.info("Class: " + this.getClass() + ", out from convertToDTOWithRouteCollection: input orders list is null or empty.");
            return null;
        }
        Collection<OrderWithRouteDTO> orderWithRouteDTOS = new ArrayList<OrderWithRouteDTO>();
        for(Order o: orders){
            orderWithRouteDTOS.add(convertToDTOWithRoute(o));
        }
        LOGGER.info("Class: " + this.getClass() + ", out from convertToDTOWithRouteCollection: result collection: " + orderWithRouteDTOS);
        return orderWithRouteDTOS;
    }


    private static String getOrderStatus(Order order){
        LOGGER.info("Class: OrderToDTOConvert, method: getOrderStatus");
        OrderStatus status = order.getStatus();
        if (status.equals(OrderStatus.NOT_PREPARED)){
            LOGGER.info("Defined status: Not prepared");
            return "Not prepared";}
        if (status.equals(OrderStatus.PREPARED)){
            LOGGER.info("Defined status: Prepared");
            return "Prepared";
        }
        if (status.equals(OrderStatus.EXECUTING)){
            LOGGER.info("Defined status: Executing");
            return "Executing";
        }
        if (status.equals(OrderStatus.EXECUTED)) {
            LOGGER.info("Defined status: Executed");
            return "Executed";
        }
        LOGGER.info("Status undefined: returned \"Not prepared\" value");
        return "Not prepared";
    }
}
