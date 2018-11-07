package com.gerasimchuk.converters;

import com.gerasimchuk.dto.TruckDTO;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Truck to dto converter.
 */
@Component
public class TruckToDTOConverterImpl implements TruckToDTOConverter {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TruckToDTOConverterImpl.class);
    @Override

    public TruckDTO convert(Truck truck) {
        LOGGER.info("Class: TruckToDTOConverterImpl, method: convert");
        if (truck == null) {
            LOGGER.error("Class: TruckToDTOConverterImpl, out from convert method: truck parameter is null");
            return null;
        }
        String id = Integer.toString(truck.getId());
        String regNum = truck.getRegistrationNumber();
        String numOfDrivers = Integer.toString(truck.getNumOfDrivers());
        String capacity = Double.toString(truck.getCapacity());
        String state = truck.getState().toString();
        String currentCity = truck.getCurrentCity().getName();
        String[] assignedDrivers = null;
        String[] assignedDriversNames = null;
        if (!truck.getDriversInTruck().isEmpty()) {
            int size = truck.getDriversInTruck().size();
            assignedDrivers = new String[size];
            assignedDriversNames = new String[size];
            int counter = 0;
            for(Driver d: truck.getDriversInTruck()){
                User u = d.getUser();
                assignedDrivers[counter] = Integer.toString(u.getId());
                assignedDriversNames[counter]=u.getName() + " " + u.getMiddleName() + " " + u.getLastName();
            }
        }
        String assignedOrder = null;
        String assignedOrderDescription = null;
        if (truck.getAssignedOrder() != null){
            assignedOrder = Integer.toString(truck.getAssignedOrder().getId());
            assignedOrderDescription = truck.getAssignedOrder().getDescription();
        }
        TruckDTO res = new TruckDTO(id,regNum,numOfDrivers,capacity,state,currentCity,assignedDrivers,assignedDriversNames,assignedOrder,assignedOrderDescription);
        LOGGER.info("Class: TruckToDTOConverterImpl, method: convert, result dto:" + res);
        return res;
    }

    @Override
    public List<TruckDTO> convert(List<Truck> trucks) {
        LOGGER.info("Class: TruckToDTOConverterImpl, method: convert");
        if (trucks == null){
            LOGGER.info("Class: TruckToDTOConverterImpl, out from convert method: trucks list is null");
            return null;
        }
        List<TruckDTO> trucksList = new ArrayList<TruckDTO>();
        for(Truck t: trucks){
            trucksList.add(convert(t));
        }
        LOGGER.info("Class: TruckToDTOConverterImpl, method: convert, result list:" + trucksList);
        return trucksList;
    }
}
