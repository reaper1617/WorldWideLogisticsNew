package com.gerasimchuk.converters;

import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.entities.Cargo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Cargo to dto converter.
 */
@Component
public class CargoToDTOConverterImpl implements CargoToDTOConverter {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CargoToDTOConverterImpl.class);

    @Override
    public CargoDTO convert(Cargo cargo) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (cargo == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: user input value is null");
            return null;
        }

        String id = Integer.toString(cargo.getId());
        String pNum = cargo.getPersonalNumber();
        String name = cargo.getName();
        String weight = Double.toString(cargo.getWeight());
        String cityFrom = cargo.getRoute().getCityFrom().getName();
        String cityTo = cargo.getRoute().getCityTo().getName();
        String status = cargo.getStatus().toString();
        CargoDTO cargoDTO = new CargoDTO(id,pNum,name,weight,cityFrom,cityTo,status);
        LOGGER.info("Class: CargoToDTOConverterImpl, method: convert, result dto:" + cargoDTO);
        return cargoDTO;
    }

    @Override
    public List<CargoDTO> convert(List<Cargo> cargoList) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (cargoList == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: user list input parameter is null");
            return null;
        }
        List<CargoDTO> cargoDTOS = new ArrayList<CargoDTO>();
        for(Cargo c: cargoList){
            cargoDTOS.add(convert(c));
        }
        LOGGER.info("Class: CargoToDTOConverterImpl, method: convert, result list:" + cargoDTOS);
        return cargoDTOS;
    }
}
