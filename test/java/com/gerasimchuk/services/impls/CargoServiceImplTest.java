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
import com.gerasimchuk.utils.ReturnValuesContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class CargoServiceImplTest {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private RouteRepository routeRepository;


    @Test
    public void createCargo() {
        City cityFrom = cityRepository.create("unitTestCityFrom",true);
        City cityTo = cityRepository.create("unitTestCityTo",true);
        Route r = routeRepository.create(cityFrom,cityTo,500);
        CargoDTO cargoDTO = new CargoDTO("",null,"unitTestCargo","10",cityFrom.getName(), cityTo.getName(), null);
        boolean res = cargoService.createCargo(cargoDTO);
        Cargo created = cargoRepository.getByName("unitTestCargo");
        //remove
        cargoRepository.remove(created.getId());
        routeRepository.remove(r.getId());
        cityRepository.remove(cityFrom.getId());
        cityRepository.remove(cityTo.getId());
        assertTrue(res);
    }

    @Test
    public void updateCargo() {
        City cityFrom = cityRepository.create("unitTestCityFrom",true);
        City cityTo = cityRepository.create("unitTestCityTo",true);
        Route r = routeRepository.create(cityFrom,cityTo,500);
        CargoDTO cargoDTO = new CargoDTO("",null,"unitTestCargo","10",cityFrom.getName(), cityTo.getName(), null);
        boolean res = cargoService.createCargo(cargoDTO);
        Cargo created = cargoRepository.getByName("unitTestCargo");
        CargoDTO updatedCargoDTO = new CargoDTO(Integer.toString(created.getId()),null,"unitUpdTestCargo","12",null, null, null);
        boolean upd = cargoService.updateCargo(updatedCargoDTO);
        //remove
        cargoRepository.remove(created.getId());
        routeRepository.remove(r.getId());
        cityRepository.remove(cityFrom.getId());
        cityRepository.remove(cityTo.getId());
        assertTrue(upd);
    }

    @Test
    public void deleteCargo() {
        City cityFrom = cityRepository.create("unitTestCityFrom",true);
        City cityTo = cityRepository.create("unitTestCityTo",true);
        Route r = routeRepository.create(cityFrom,cityTo,500);
        CargoDTO cargoDTO = new CargoDTO("",null,"unitTestCargo","10",cityFrom.getName(), cityTo.getName(), null);
        boolean res = cargoService.createCargo(cargoDTO);
        Cargo created = cargoRepository.getByName("unitTestCargo");
        boolean del = cargoService.deleteCargo(created.getId());
        //remove
        routeRepository.remove(r.getId());
        cityRepository.remove(cityFrom.getId());
        cityRepository.remove(cityTo.getId());
        assertTrue(del);
    }

    @Test
    public void deleteCargo1() {
        City cityFrom = cityRepository.create("unitTestCityFrom",true);
        City cityTo = cityRepository.create("unitTestCityTo",true);
        Route r = routeRepository.create(cityFrom,cityTo,500);
        CargoDTO cargoDTO = new CargoDTO("",null,"unitTestCargo","10",cityFrom.getName(), cityTo.getName(), null);
        boolean res = cargoService.createCargo(cargoDTO);
        Cargo created = cargoRepository.getByName("unitTestCargo");
        UpdateMessageType del = cargoService.deleteCargo(created.getId(), 0);
        //remove
        routeRepository.remove(r.getId());
        cityRepository.remove(cityFrom.getId());
        cityRepository.remove(cityTo.getId());
        assertEquals(UpdateMessageType.CARGO_DELETED,del);
    }

    @Test
    public void getAvailableCargos() {
        Collection<Cargo> allCargos = cargoRepository.getAll();
        Collection<Cargo> availableByRepo = new ArrayList<Cargo>();
        for(Cargo c: allCargos){
            if (c.getOrder() == null && c.getStatus().equals(CargoStatus.PREPARED)) availableByRepo.add(c);
        }
        Collection<Cargo> availableByService = cargoService.getAvailableCargos();
        boolean availableEquals = true;
        for(Cargo cargoByRepo: availableByRepo){
            boolean equalFound = false;
            for(Cargo cargoByService: availableByService){
                if (cargoByRepo.getName().equals(cargoByService.getName())) equalFound = true;
            }
            if (!equalFound) {
                availableEquals = false;
                break;
            }
        }
        assertTrue(availableEquals);
    }

    @Test
    public void getCargoStatusFromString() {
        String status = "PREPARED";
        CargoStatus res = cargoService.getCargoStatusFromString(status);
        assertEquals(CargoStatus.PREPARED, res);
    }

    @Test
    public void getCargoStatusFromNullString() {
        String status = null;
        CargoStatus res = cargoService.getCargoStatusFromString(status);
        assertNull(res);
    }
}