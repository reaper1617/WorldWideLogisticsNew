package com.gerasimchuk.services.impls;

import com.gerasimchuk.dto.CityDTO;
import com.gerasimchuk.entities.City;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.repositories.CityRepository;
import com.gerasimchuk.services.interfaces.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class CityServiceImplTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;



    @Test
    public void createCity() {
        CityDTO cityDTO = new CityDTO("","UnitTestCity", Boolean.toString(false));
        boolean res = cityService.createCity(cityDTO);
        City created = cityRepository.getByName("UnitTestCity");
        cityRepository.remove(created.getId());
        assertTrue(res);
    }

    @Test
    public void updateCity() {
        CityDTO cityDTO = new CityDTO("","UnitTestCity", Boolean.toString(false));
        boolean res = cityService.createCity(cityDTO);
        City created = cityRepository.getByName("UnitTestCity");
        CityDTO updateCityDTO = new CityDTO(Integer.toString(created.getId()),"NewUnitTestCity", Boolean.toString(true));
        boolean upd = cityService.updateCity(updateCityDTO);
        // remove
        cityRepository.remove(created.getId());
        assertTrue(upd);
    }

    @Test
    public void deleteCity() {
        CityDTO cityDTO = new CityDTO("","UnitTestCity", Boolean.toString(false));
        cityService.createCity(cityDTO);
        City created = cityRepository.getByName("UnitTestCity");
        boolean del = false;
        try {
            del = cityService.deleteCity(created.getId());
        } catch (Exception e) {
            cityRepository.remove(created.getId());
            e.printStackTrace();
            fail();
        }
        assertTrue(del);
    }

    @Test
    public void deleteCity1() {
        CityDTO cityDTO = new CityDTO("","UnitTestCity", Boolean.toString(false));
        cityService.createCity(cityDTO);
        City created = cityRepository.getByName("UnitTestCity");
        UpdateMessageType del = null;
        try {
            del = cityService.deleteCity(created.getId(),0);
        } catch (Exception e) {
            cityRepository.remove(created.getId());
            e.printStackTrace();
            fail();
        }
        assertEquals(UpdateMessageType.CITY_DELETED,del);
    }
}