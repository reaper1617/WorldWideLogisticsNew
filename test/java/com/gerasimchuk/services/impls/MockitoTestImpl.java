package com.gerasimchuk.services.impls;


import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.repositories.CargoRepository;
import com.gerasimchuk.repositories.CityRepository;
import com.gerasimchuk.repositories.RouteRepository;
import com.gerasimchuk.services.interfaces.CargoService;
import com.gerasimchuk.services.interfaces.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class MockitoTestImpl {


    @Test
    public void CargoServiceImplCreateCargoTest(){
        CityRepository cityRepository = mock(CityRepository.class);
        City cityFrom = new City("TestCityFrom", true);
        when(cityRepository.create("TestCityFrom", true)).thenReturn(cityFrom);
        City cityTo = new City("TestCityTo", true);
        when(cityRepository.create("TestCityTo", true)).thenReturn(cityTo);
        Route r = new Route(cityFrom,cityTo,500);
        RouteRepository routeRepository = mock(RouteRepository.class);
        when(routeRepository.create(cityFrom,cityTo,500)).thenReturn(r);
        CargoDTO cargoDTO = new CargoDTO("",null,"unitTestCargo","10",cityFrom.getName(), cityTo.getName(), null);
        CargoService cargoService = mock(CargoService.class);
        when(cargoService.createCargo(cargoDTO)).thenReturn(true);
        boolean res = cargoService.createCargo(cargoDTO);
        Assert.assertTrue(res);
    }

    @Test
    public void CityServiceCreateCityTest(){
        CityDTO cityDTO = new CityDTO("", "TestCity", Boolean.toString(true));
        CityService cityService = mock(CityService.class);
        when(cityService.createCity(cityDTO)).thenReturn(true);
        boolean res = cityService.createCity(cityDTO);
        assertTrue(res);
    }
}
