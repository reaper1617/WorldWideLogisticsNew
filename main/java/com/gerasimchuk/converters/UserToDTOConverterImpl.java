package com.gerasimchuk.converters;

import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User to dto converter.
 */
@Component
public class UserToDTOConverterImpl implements UserToDTOConverter {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UserToDTOConverterImpl.class);

    @Override
    public UserDTO convert(User u) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (u == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: user input value is null");
            return null;
        }

        String id = Integer.toString(u.getId());
        String firstName = u.getName();
        String middleName = u.getMiddleName();
        String lastName = u.getLastName();
        String pNum= u.getPersonalNumber();
        String password=u.getPassword();

        String role = getUserRole(u);
        if (role == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: user input value is null");
            return null;
        }
        String hoursWorked="-";
        String driverStatus="-";
        String currentCity = "-";
        String truckRegNum="-";
        String orderId="-";
        String orderDescription = "-";
        if (role.equals(UserRole.DRIVER.toString())){
            Driver d = u.getDriver();
            hoursWorked = Double.toString(d.getHoursWorked());
            driverStatus = d.getStatus().toString();
            currentCity = d.getCurrentCity().getName();
            if(d.getCurrentTruck() != null){
                truckRegNum = d.getCurrentTruck().getRegistrationNumber();
                Order order = d.getCurrentTruck().getAssignedOrder();
                if (order != null){
                    orderId = Integer.toString(order.getId());
                    orderDescription = order.getDescription();
                }
            }
        }
        UserDTO userDTO = new UserDTO(id,firstName,middleName,lastName,pNum,password,role,hoursWorked,driverStatus,currentCity,truckRegNum,orderId, orderDescription);
        LOGGER.info("Class: UserToDTOConverterImpl, method: convert, result dto:" + userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> convert(List<User> users) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: convert");
        if (users == null || users.isEmpty()){
            LOGGER.error("Class: " + this.getClass().getName() + " out from convert method: user list input value is null");
            return null;
        }
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for(User u: users){
            userDTOS.add(convert(u));
        }
        LOGGER.info("Class: " + this.getClass().getName() + " out from convert method: result:" + userDTOS);
        return userDTOS;
    }


    private String getUserRole(User u){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getUserRole");
        if (u == null){
            LOGGER.error("Class: " + this.getClass().getName() + " out from getUserRole method: user input value is null.");
            return null;
        }
        if (u.getDriver() != null){
            LOGGER.info("Class: " + this.getClass().getName() + " out from getUserRole method: result = " + UserRole.DRIVER.toString());
            return UserRole.DRIVER.toString();
        }
        if (u.getManager()!=null){
            LOGGER.info("Class: " + this.getClass().getName() + " out from getUserRole method: result = " + UserRole.MANAGER.toString());
            return UserRole.MANAGER.toString();
        }
        if (u.getAdmin() != null){
            LOGGER.info("Class: " + this.getClass().getName() + " out from getUserRole method: result = " + UserRole.ADMIN.toString());
            return UserRole.ADMIN.toString();
        }
        LOGGER.error("Class: " + this.getClass().getName() + " out from getUserRole method: user role undefined, returned null");
        return null;
    }
}
