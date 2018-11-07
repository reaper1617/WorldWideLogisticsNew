package com.gerasimchuk.services.impls;

import com.gerasimchuk.constants.WWLConstants;
import com.gerasimchuk.dto.CargoDTO;
import com.gerasimchuk.dto.OrderDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.*;
import com.gerasimchuk.exceptions.driverexceptions.TooManyHoursWorkedForOrderException;
import com.gerasimchuk.exceptions.routeexceptions.RouteException;
import com.gerasimchuk.repositories.*;
import com.gerasimchuk.services.interfaces.CargoService;
import com.gerasimchuk.services.interfaces.OrderService;
import com.gerasimchuk.utils.ReturnValuesContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class OrderServiceImplTest {


    @Autowired
    private OrderService orderService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CargoService cargoService;

    @Test
    public void getChosenCargos() {

        CargoDTO cargoDTO = new CargoDTO("","","TestCargo","10","Moscow","Petrozavodsk","");
        cargoService.createCargo(cargoDTO);
        Cargo persistedCargo = cargoRepository.getByName("TestCargo");
//        Collection<Cargo> cargos = cargoRepository.getAll();
        String[] cargosInOrder = new String[1];
        cargosInOrder[0] = Integer.toString(persistedCargo.getId());
//        for(Cargo c: cargos){
//            if (c.getStatus().equals(CargoStatus.PREPARED)) {
//                cargosInOrder[0] = Integer.toString(c.getId());
//                break;
//            }
//        }
        OrderDTO orderDTO = new OrderDTO(null,null,null,null,null,cargosInOrder);
        Collection<Cargo> cargos1 = orderService.getChosenCargos(orderDTO);

        cargoService.deleteCargo(persistedCargo.getId());
        Cargo c = cargos1.iterator().next();
        if (c == null) fail();
        boolean res = c.getName().equals("TestCargo") && cargos1.size()==1;
//        assertEquals(1, cargos1.size());
        assertTrue(res);
    }

    @Test
    public void getAvailableTrucks() {
        OrderDTO orderDTO = null;
        Collection<Truck>  res = null;
        try {
            res = orderService.getAvailableTrucks(orderDTO);
        } catch (RouteException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(res);
    }

    @Test
    public void getOrderRoute() {
        OrderDTO orderDTO = null;
        Truck truck = null;
        List<City> route = null;
        try {
            route = orderService.getOrderRoute(orderDTO,truck);
        } catch (RouteException e) {
            e.printStackTrace();
            fail();
        }
        assertNull(route);
    }

    @Test
    public void getOrderRouteReal() {
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ln37465",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Driver driver = new Driver(1,DriverStatus.FREE,currentCity,createdTruck);
        Driver persistedDriver = driverRepository.create(driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),driver.getCurrentTruck());
        User user = new User("UnitTestUser","UnitTestUser","UnitTestUser","ncjdkf857","password",driver,null,null);
        User persistedUser = userRepository.create(user.getName(),user.getMiddleName(),user.getLastName(),user.getPersonalNumber(),user.getPassword(),persistedDriver,null,null);
        Route createdRoute = routeRepository.create(currentCity, cityTo,500);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,createdRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        ReturnValuesContainer<Order> res = null;
        List<City> gottenOrderRoute = null;
        try {
            res = orderService.createOrder(orderDTO,0);
            gottenOrderRoute = orderService.getOrderRoute(orderDTO,createdTruck);
        } catch (RouteException e) {
            if (res != null)
                if (res.getUpdateMessageType().equals(UpdateMessageType.ORDER_CREATED)) orderService.deleteOrder(res.getReturnedValue().getId());
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }
        boolean routeCorrect = gottenOrderRoute.contains(currentCity)&&gottenOrderRoute.contains(cityTo)&&gottenOrderRoute.size()==2;

        // delete part:
        orderService.deleteOrder(res.getReturnedValue().getId());
        cargoRepository.remove(c1.getId());
        routeRepository.remove(createdRoute.getId());
        userRepository.remove(persistedUser.getId());
        driverRepository.remove(persistedDriver.getId());
        truckRepository.remove(createdTruck.getId());
        assertTrue(routeCorrect);
    }



    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        UpdateMessageType res = null;
        try {
            res = orderService.createOrder(orderDTO);
        } catch (RouteException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID, res);
    }

    @Test
    public void createRealOrder() {
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ln37465",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Driver driver = new Driver(1,DriverStatus.FREE,currentCity,createdTruck);
        Driver persistedDriver = driverRepository.create(driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),createdTruck);
        User user = new User("UnitTestUser","UnitTestUser","UnitTestUser","ncjdkf857","password",driver,null,null);
        User persistedUser = userRepository.create(user.getName(),user.getMiddleName(),user.getLastName(),user.getPersonalNumber(),user.getPassword(),persistedDriver,null,null);
        Route createdRoute = routeRepository.create(currentCity, cityTo,500);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,createdRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        ReturnValuesContainer<Order> res = null;
        try {
            res = orderService.createOrder(orderDTO,0);
        } catch (RouteException e) {
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }
        // delete part:
        orderService.deleteOrder(res.getReturnedValue().getId());
        cargoRepository.remove(c1.getId());
        routeRepository.remove(createdRoute.getId());
        userRepository.remove(persistedUser.getId());
        driverRepository.remove(persistedDriver.getId());
        truckRepository.remove(createdTruck.getId());
        assertEquals(UpdateMessageType.ORDER_CREATED, res.getUpdateMessageType());
    }

    @Test
    public void createOrder1() {
        OrderDTO orderDTO = new OrderDTO();
        ReturnValuesContainer<Order> res = null;
        try {
            res = orderService.createOrder(orderDTO,0);
        } catch (RouteException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(res.getUpdateMessageType(),UpdateMessageType.ERROR_CAN_NOT_PARSE_ORDER_ID);
    }

    @Test
    public void updateOrder() {
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ln37465",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Driver driver = new Driver(1,DriverStatus.FREE,currentCity,createdTruck);
        Driver persistedDriver = driverRepository.create(driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),driver.getCurrentTruck());
        User user = new User("UnitTestUser","UnitTestUser","UnitTestUser","ncjdkf857","password",driver,null,null);
        User persistedUser = userRepository.create(user.getName(),user.getMiddleName(),user.getLastName(),user.getPersonalNumber(),user.getPassword(),persistedDriver,null,null);
        Route createdRoute = routeRepository.create(currentCity, cityTo,500);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,createdRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        ReturnValuesContainer<Order> res = null;
        try {
            res = orderService.createOrder(orderDTO,0);
        } catch (RouteException e) {
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }
        Order createdOrder = res.getReturnedValue();
        OrderDTO updOrderDTO = new OrderDTO(Integer.toString(createdOrder.getId()),null,"unitTestOrderUpd",null,null,null);
        UpdateMessageType result = null;
        try {
            result = orderService.updateOrder(updOrderDTO);
        } catch (Exception e) {
            orderService.deleteOrder(res.getReturnedValue().getId());
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }

        // delete part:
        orderService.deleteOrder(res.getReturnedValue().getId());
        cargoRepository.remove(c1.getId());
        routeRepository.remove(createdRoute.getId());
        userRepository.remove(persistedUser.getId());
        driverRepository.remove(persistedDriver.getId());
        truckRepository.remove(createdTruck.getId());
        assertEquals(UpdateMessageType.ORDER_EDITED, result);
    }

    @Test
    public void getOrderStatusFromString() {
        String status = OrderStatus.NOT_PREPARED.toString();
        OrderStatus res = orderService.getOrderStatusFromString(status);
        assertEquals(res,OrderStatus.NOT_PREPARED);
    }

    @Test
    public void areAllCargosDelivered() {
        City city = new City("TestCity",true);
        City cityTo = new City("TestCityTo",true);
        Truck truck = new Truck("dd20394",2,2,TruckState.READY,city);
        Order order = new Order("7736452837","TestOrderDescr","15 OCT 2018 15:08:22",OrderStatus.NOT_PREPARED,truck);
        Set<Cargo> cargosInOrder = new HashSet<Cargo>();
        Route r = new Route(city,cityTo,500);
        cargosInOrder.add(new Cargo("7364987629","TestCargo",10,CargoStatus.DELIVERED,r));
        order.setCargosInOrder(cargosInOrder);
        boolean res = orderService.areAllCargosDelivered(order);
        assertTrue(res);
    }

    @Test
    public void deleteOrder() {
        // create new
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ms33665",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Driver driver = new Driver(1,DriverStatus.FREE,currentCity,createdTruck);
        Driver persistedDriver = driverRepository.create(driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),driver.getCurrentTruck());
        User user = new User("UnitTestUser","UnitTestUser","UnitTestUser","ncjdkf857","password",driver,null,null);
        User persistedUser = userRepository.create(user.getName(),user.getMiddleName(),user.getLastName(),user.getPersonalNumber(),user.getPassword(),persistedDriver,null,null);
        //        driverRepository.update(driver.getId(),driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),createdTruck);
        Route createdRoute = routeRepository.create(currentCity, cityTo,500);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,createdRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        ReturnValuesContainer<Order> res = null;
        try {
            res = orderService.createOrder(orderDTO,0);
        } catch (RouteException e) {
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }
        Order createdOrder = res.getReturnedValue();
        UpdateMessageType result = orderService.deleteOrder(createdOrder.getId());

        // delete part:
        cargoRepository.remove(c1.getId());
        routeRepository.remove(createdRoute.getId());
        userRepository.remove(persistedUser.getId());
        driverRepository.remove(persistedDriver.getId());
        truckRepository.remove(createdTruck.getId());
        assertEquals(UpdateMessageType.ORDER_DELETED, result);
    }

    @Test
    public void deleteOrder1() {
        // create new
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ms33665",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Driver driver = new Driver(1,DriverStatus.FREE,currentCity,createdTruck);
        Driver persistedDriver = driverRepository.create(driver.getHoursWorked(),driver.getStatus(),driver.getCurrentCity(),driver.getCurrentTruck());
        User user = new User("UnitTestUser","UnitTestUser","UnitTestUser","ncjdkf857","password",driver,null,null);
        User persistedUser = userRepository.create(user.getName(),user.getMiddleName(),user.getLastName(),user.getPersonalNumber(),user.getPassword(),persistedDriver,null,null);
        Route createdRoute = routeRepository.create(currentCity, cityTo,500);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,createdRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        ReturnValuesContainer<Order> res = null;
        try {
            res = orderService.createOrder(orderDTO,0);
        } catch (RouteException e) {
            cargoRepository.remove(c1.getId());
            routeRepository.remove(createdRoute.getId());
            userRepository.remove(persistedUser.getId());
            driverRepository.remove(persistedDriver.getId());
            truckRepository.remove(createdTruck.getId());
            e.printStackTrace();
            fail();
        }
        Order createdOrder = res.getReturnedValue();
        OrderDTO deleteOrderDTO = new OrderDTO(Integer.toString(createdOrder.getId()),null,null,null,null,null);
        UpdateMessageType result = orderService.deleteOrder(deleteOrderDTO);
        // delete part:
        cargoRepository.remove(c1.getId());
        routeRepository.remove(createdRoute.getId());
        userRepository.remove(persistedUser.getId());
        driverRepository.remove(persistedDriver.getId());
        truckRepository.remove(createdTruck.getId());
        assertEquals(UpdateMessageType.ORDER_DELETED, result);
    }

    @Test
    public void getExecutingTime() {
        City currentCity = cityRepository.getByName("Moscow");
        City cityTo = cityRepository.getByName("Petrozavodsk");
        Truck newTruck = new Truck("ms33665",3,3,TruckState.READY,currentCity);
        Truck createdTruck = truckRepository.create(newTruck.getRegistrationNumber(),newTruck.getNumOfDrivers(),newTruck.getCapacity(),newTruck.getState(),newTruck.getCurrentCity());
        Route gottenRoute = routeRepository.getByCities(currentCity, cityTo);
//        assertNotNull(gottenRoute);
        Cargo c1 = cargoRepository.create("7738526452","unitTestCargo",5,CargoStatus.PREPARED,gottenRoute);
        String[] cargosInOrder = {Integer.toString(c1.getId())};
        OrderDTO orderDTO = new OrderDTO("",null,"unitTestOrder",null,Integer.toString(createdTruck.getId()),cargosInOrder);
        double resByMethod = 0;
        try {
            resByMethod = orderService.getExecutingTime(orderDTO);
        } catch (RouteException e) {
            e.printStackTrace();
            cargoRepository.remove(c1.getId());
            routeRepository.remove(gottenRoute.getId());
            truckRepository.remove(createdTruck.getId());
            fail();
        }

        double distance = c1.getRoute().getDistance();
        double resByCount = distance/WWLConstants.AVERAGE_TRUCK_SPEED;
        boolean result = (resByCount==resByMethod);
        // delete part:
        cargoRepository.remove(c1.getId());
        //routeRepository.remove(gottenRoute.getId());
        truckRepository.remove(createdTruck.getId());
        assertTrue(result);
    }

}