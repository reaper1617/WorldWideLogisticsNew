package com.gerasimchuk.services.impls;

import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverStatus;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.repositories.DriverRepository;
import com.gerasimchuk.repositories.TruckRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class StatisticServiceImplTest {

    @Autowired
    private StatisticServiceImpl statisticService;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private DriverRepository driverRepository;

    public StatisticServiceImplTest() {
    }

    @Test
    public void getNumOfTrucksTotal() {
        int numOfTrucksTotal = statisticService.getNumOfTrucksTotal();
        int numOfTrucksByRepo = truckRepository.getAll().size();
        assertEquals(numOfTrucksByRepo, numOfTrucksTotal);
    }

    @Test
    public void getNumOfTrucksFree() {
        int numOfTrucksFree = statisticService.getNumOfTrucksFree();
        int numOfTrucksByRepo =0;
        Collection<Truck> allTrucks =  truckRepository.getAll();
        for(Truck t: allTrucks){
            if (t.getAssignedOrder() == null && t.getState().equals(TruckState.READY)) numOfTrucksByRepo++;
        }
        assertEquals(numOfTrucksByRepo,numOfTrucksFree);
    }

    @Test
    public void getNumOfTrucksNotReady() {
        int numOfTrucksNotReady = statisticService.getNumOfTrucksNotReady();
        int numOfTrucksByRepo =0;
        Collection<Truck> allTrucks =  truckRepository.getAll();
        for(Truck t: allTrucks){
            if (t.getState().equals(TruckState.NOT_READY)) numOfTrucksByRepo++;
        }
        assertEquals(numOfTrucksByRepo,numOfTrucksNotReady);
    }

    @Test
    public void getNumOfTrucksExecutingOrders() {
        int numByStats = statisticService.getNumOfTrucksExecutingOrders();
        int numByRepo = 0;
        Collection<Truck> trucks = truckRepository.getAll();
        for(Truck t: trucks){
            if (t.getAssignedOrder()!=null) numByRepo++;
        }
        assertEquals(numByRepo,numByStats);
    }

    @Test
    public void getNumOfDriversTotal() {
        int numByStats = statisticService.getNumOfDriversTotal();
        int numByRepo = driverRepository.getAll().size();
        assertEquals(numByRepo,numByStats);
    }

    @Test
    public void getNumOfDriversFree() {
        int numByStats = statisticService.getNumOfDriversFree();
        int numByRepo = 0;
        Collection<Driver> drivers = driverRepository.getAll();
        for(Driver d: drivers){
            if (d.getStatus().equals(DriverStatus.FREE)) numByRepo++;
        }
        assertEquals(numByRepo,numByStats);
    }

    @Test
    public void getNumOfDriversExecutingOrders() {
        int numByStats = statisticService.getNumOfDriversExecutingOrders();
        int numByRepo = 0;
        Collection<Driver> drivers = driverRepository.getAll();
        for(Driver d: drivers){
            if (!d.getStatus().equals(DriverStatus.FREE)) numByRepo++;
        }
        assertEquals(numByRepo,numByStats);
    }

    @Test
    public void convertToJSONString() {
        String s = statisticService.convertToJSONString();
        Gson gson = new Gson();
        try{
            gson.fromJson(s,StatisticServiceImpl.class);
            assertTrue(true);
        }
        catch (Exception e){
            fail();
        }
    }
}