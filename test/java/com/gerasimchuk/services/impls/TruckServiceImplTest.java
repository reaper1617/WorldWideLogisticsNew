package com.gerasimchuk.services.impls;

import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.TruckRepository;
import com.gerasimchuk.services.interfaces.TruckService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class TruckServiceImplTest {

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckRepository truckRepository;

//    private static List<Truck> createdTrucks = new ArrayList<Truck>();
    public TruckServiceImplTest() {
    }

//    @Before
//    public void setUp() throws Exception {
//        for(Truck t: createdTrucks){
//            truckRepository.remove(t.getId());
//        }
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        for(Truck t: createdTrucks){
//            truckRepository.remove(t.getId());
//        }
//    }

    @Test
    public void createTruck() {
        TruckDTO truckDTO = new TruckDTO("","la48376","3","3","Ready","Moscow",null,null);
        UpdateMessageType result = truckService.createTruck(truckDTO);
        Truck created = truckRepository.getByRegistrationNumber("la48376");
        //
        truckRepository.remove(created.getId());
        assertEquals(UpdateMessageType.TRUCK_CREATED, result);
    }

    @Test
    public void updateTruck() {
        TruckDTO truckDTO = new TruckDTO("","lp48576","3","3","Ready","Moscow",null,null);
        UpdateMessageType result = truckService.createTruck(truckDTO);
        Truck created = truckRepository.getByRegistrationNumber("lp48576");
        TruckDTO updateTruckDTO = new TruckDTO(Integer.toString(created.getId()),"lp43336","4","4",null,"Moscow",null,null);
        UpdateMessageType res = truckService.updateTruck(updateTruckDTO);
        //
        truckRepository.remove(created.getId());
        assertEquals(UpdateMessageType.TRUCK_EDITED, res);
    }

    @Test
    public void deleteTruck() {
        TruckDTO truckDTO = new TruckDTO("","mo43396","3","3","Ready","Moscow",null,null);
        UpdateMessageType result = truckService.createTruck(truckDTO);
        Truck created = truckRepository.getByRegistrationNumber("mo43396");
        UpdateMessageType res = truckService.deleteTruck(created.getId());
        assertEquals(UpdateMessageType.TRUCK_DELETED, res);
    }

    @Test
    public void getFreeTrucks() {
        int numOfFreeTrucks = truckRepository.getNumberOfTrucksFree();
        Collection<Truck> trucksByService = truckService.getFreeTrucks();
        boolean allTrucksFree = true;
        for(Truck t: trucksByService){
            if (t.getAssignedOrder() != null || !t.getState().equals(TruckState.READY)){
                allTrucksFree = false;
                break;
            }
        }
        assertTrue( (numOfFreeTrucks == trucksByService.size())&&allTrucksFree);
    }
}