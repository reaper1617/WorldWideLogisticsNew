package com.gerasimchuk.converters;

import com.gerasimchuk.dto.RouteDTO;
import com.gerasimchuk.entities.Route;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Route to dto converter.
 */
@Component
public class RouteToDTOConverterImpl implements RouteToDTOConverter {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RouteToDTOConverterImpl.class);
    @Override
    public RouteDTO convert(Route route) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (route == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: route input value is null");
            return null;
        }
        String id = Integer.toString(route.getId());
        String cityFrom = route.getCityFrom().getName();
        String cityTo = route.getCityTo().getName();
        String distance = Double.toString(route.getDistance());
        RouteDTO routeDTO = new RouteDTO(id,cityFrom,cityTo,distance);
        LOGGER.info("Class: UserToDTOConverterImpl, method: convert, result dto:" + routeDTO);
        return routeDTO;
    }

    @Override
    public List<RouteDTO> convert(List<Route> routes) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (routes == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: route list input is null");
            return null;
        }
        List<RouteDTO> routeDTOS = new ArrayList<RouteDTO>();
        for(Route r: routes){
            routeDTOS.add(convert(r));
        }
        LOGGER.info("Class: UserToDTOConverterImpl, method: convert, result list:" + routeDTOS);
        return routeDTOS;
    }
}
