package com.gerasimchuk.services.impls;

import com.gerasimchuk.dto.RouteDTO;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.RouteRepository;
import com.gerasimchuk.services.interfaces.RouteService;
import com.gerasimchuk.utils.ReturnValuesContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class RouteServiceImplTest {


    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteRepository routeRepository;



    @Test
    @Transactional
    public void createRoute() {
        RouteDTO routeDTO = new RouteDTO("","Moscow","Petrozavodsk", "600");
        ReturnValuesContainer<Route> res = routeService.createRoute(routeDTO, 0);
        assertEquals(UpdateMessageType.ROUTE_CREATED, res.getUpdateMessageType());
    }

    @Test
    @Transactional
    public void updateRoute() {
        RouteDTO routeDTO = new RouteDTO("","Moscow","Petrozavodsk", "400");
        ReturnValuesContainer<Route> res = routeService.createRoute(routeDTO, 0);
        RouteDTO updateRouteDTO = new RouteDTO(Integer.toString(res.getReturnedValue().getId()),"Moscow","Petrozavodsk", "500");
        ReturnValuesContainer<Route> result = routeService.updateRoute(updateRouteDTO, 0);
        assertEquals(UpdateMessageType.ROUTE_EDITED, result.getUpdateMessageType());
    }

    @Test
    @Transactional
    public void deleteRoute() {
        RouteDTO routeDTO = new RouteDTO("","Moscow","Petrozavodsk", "400");
        ReturnValuesContainer<Route> res = routeService.createRoute(routeDTO, 0);
        boolean result = false;
        try {
            result = routeService.deleteRoute(res.getReturnedValue().getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(result);
    }
}