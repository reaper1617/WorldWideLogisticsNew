package com.gerasimchuk.services.impls;

import com.gerasimchuk.repositories.DriverRepository;
import com.gerasimchuk.repositories.TruckRepository;
import com.gerasimchuk.services.interfaces.StatisticService;
import com.gerasimchuk.utils.JSONconvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Statistic service.
 */
@Service
public class StatisticServiceImpl implements StatisticService, JSONconvertable {

    private DriverRepository driverRepository;
    private TruckRepository truckRepository;

    /**
     * Instantiates a new Statistic service.
     *
     * @param driverRepository the driver repository
     * @param truckRepository  the truck repository
     */
    @Autowired
    public StatisticServiceImpl(DriverRepository driverRepository, TruckRepository truckRepository) {
        this.driverRepository = driverRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    public int getNumOfTrucksTotal() {
        return truckRepository.getNumberOfTrucksTotal();
    }

    @Override
    public int getNumOfTrucksFree() {
        return truckRepository.getNumberOfTrucksFree();
    }

    @Override
    public int getNumOfTrucksNotReady() {
        return truckRepository.getNumberOfTrucksNotReady();
    }

    @Override
    public int getNumOfTrucksExecutingOrders() {
        return truckRepository.getNumberOfTrucksExecutingOrder();
    }

    @Override
    public int getNumOfDriversTotal() {
        return driverRepository.getNumOfDriversTotal();
    }

    @Override
    public int getNumOfDriversFree() {
        return driverRepository.getNumOfDriversFree();
    }

    @Override
    public int getNumOfDriversExecutingOrders() {
        return driverRepository.getNumOfDriversExecutingOrders();
    }

    @Override
    public String convertToJSONString() {
        StringBuilder result = new StringBuilder("{");
        result.append("\"numOfTrucksTotal\":").append("\"").append(getNumOfTrucksTotal()).append("\"").append(",");
        result.append("\"numOfTrucksFree\":").append("\"").append(getNumOfTrucksFree()).append("\"").append(",");
        result.append("\"numOfTrucksNotReady\":").append("\"").append(getNumOfTrucksNotReady()).append("\"").append(",");
        result.append("\"numOfTrucksExecutingOrders\":").append("\"").append(getNumOfTrucksNotReady()).append("\"").append(",");
        result.append("\"numOfDriversTotal\":").append("\"").append(getNumOfDriversTotal()).append("\"").append(",");
        result.append("\"numOfDriversFree\":").append("\"").append(getNumOfDriversFree()).append("\"").append(",");
        result.append("\"numOfDriversExecutingOrders\":").append("\"").append(getNumOfDriversExecutingOrders()).append("\"").append("}");
        return result.toString();
    }
}
