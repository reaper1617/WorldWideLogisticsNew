package com.gerasimchuk.services.impls;

import com.gerasimchuk.dto.AdminDTO;
import com.gerasimchuk.dto.DriverDTO;
import com.gerasimchuk.dto.ManagerDTO;
import com.gerasimchuk.dto.UserDTO;
import com.gerasimchuk.entities.*;
import com.gerasimchuk.enums.DriverStatus;
import com.gerasimchuk.enums.UpdateMessageType;
import com.gerasimchuk.enums.UserRole;
import com.gerasimchuk.repositories.*;
import com.gerasimchuk.services.interfaces.UserService;
import com.gerasimchuk.utils.PersonalNumberGenerator;
import com.gerasimchuk.utils.ReturnValuesContainer;
import com.gerasimchuk.validators.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The type User service.
 */


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private DriverRepository driverRepository;
    private ManagerRepository managerRepository;
    private AdminRepository adminRepository;
    private CityRepository cityRepository;
    private TruckRepository truckRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DTOValidator dtoValidator;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UserServiceImpl.class);

    /**
     * Instantiates a new User service.
     *
     * @param userRepository        the user repository
     * @param driverRepository      the driver repository
     * @param managerRepository     the manager repository
     * @param adminRepository       the admin repository
     * @param cityRepository        the city repository
     * @param truckRepository       the truck repository
     * @param bCryptPasswordEncoder the b crypt password encoder
     * @param dtoValidator          the dto validator
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, DriverRepository driverRepository, ManagerRepository managerRepository, AdminRepository adminRepository, CityRepository cityRepository, TruckRepository truckRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DTOValidator dtoValidator) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
        this.cityRepository = cityRepository;
        this.truckRepository = truckRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dtoValidator = dtoValidator;
    }

    public UpdateMessageType createDriver(DriverDTO driverDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createDriver");
        if (!dtoValidator.validate(driverDTO)){
            LOGGER.error("Error: driverDTO is not valid.");
            return UpdateMessageType.ERROR_DRIVER_DTO_IS_NOT_VALID;
        }
        double hoursWorkedVal = getHoursWorkedValFromDriverDTO(driverDTO);
        City city = getCurrentCityFromDriverDTO(driverDTO);
        Truck truck = getCurrentTruckFromDriverDTO(driverDTO);
        Driver driver = driverRepository.create(hoursWorkedVal,DriverStatus.FREE,city,truck);
        String personalNumber = PersonalNumberGenerator.generate(10);
        String password = bCryptPasswordEncoder.encode(driverDTO.getPassword());
        User user = userRepository
                .create(driverDTO.getFirstName(),
                driverDTO.getMiddleName(),
                driverDTO.getLastName(),
                personalNumber,
                password,
                driver,
                null,
                null);
        LOGGER.info("Driver " + user.getPersonalNumber() + " created successfully.");
        return  UpdateMessageType.DRIVER_CREATED;
    }


    @Override
    @Transactional
    public ReturnValuesContainer<User> createDriver(DriverDTO driverDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createDriver");
        if (!dtoValidator.validate(driverDTO)){
            LOGGER.error("Error: driverDTO is not valid.");
            return new ReturnValuesContainer<User>(UpdateMessageType.ERROR_DRIVER_DTO_IS_NOT_VALID,null);
        }
        double hoursWorkedVal = getHoursWorkedValFromDriverDTO(driverDTO);
        City city = getCurrentCityFromDriverDTO(driverDTO);
        Truck truck = getCurrentTruckFromDriverDTO(driverDTO);
        Driver driver = driverRepository.create(hoursWorkedVal,DriverStatus.FREE,city,truck);
        String personalNumber = PersonalNumberGenerator.generate(10);
        String password = bCryptPasswordEncoder.encode(driverDTO.getPassword());
        User user = userRepository
                .create(driverDTO.getFirstName(),
                        driverDTO.getMiddleName(),
                        driverDTO.getLastName(),
                        personalNumber,
                        password,
                        driver,
                        null,
                        null);
        LOGGER.info("Driver " + user.getPersonalNumber() + " created successfully.");
        return  new ReturnValuesContainer<User>(UpdateMessageType.DRIVER_CREATED,user);
    }

    @Transactional
    public UpdateMessageType createManager(ManagerDTO managerDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createManager");
        if (!dtoValidator.validate(managerDTO)) {
            LOGGER.error("Error: managerDTO is not valid.");
            return UpdateMessageType.ERROR_MANAGER_DTO_IS_NOT_VALID;
        }
        Manager manager = managerRepository.create();
        User user = userRepository
                .create(managerDTO.getFirstName(),
                        managerDTO.getMiddleName(),
                        managerDTO.getLastName(),
                        PersonalNumberGenerator.generate(10),
                        bCryptPasswordEncoder.encode(managerDTO.getPassword()),
                        null,
                        manager,
                        null);
        LOGGER.info("Manager " + user.getPersonalNumber() + " created successfully.");
        return UpdateMessageType.MANAGER_CREATED;
    }

    @Override
    public ReturnValuesContainer<User> createManager(ManagerDTO managerDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createManager");
        if (!dtoValidator.validate(managerDTO)) {
            LOGGER.error("Error: managerDTO is not valid.");
            return new ReturnValuesContainer<User>(UpdateMessageType.ERROR_MANAGER_DTO_IS_NOT_VALID, null);
        }
        Manager manager = managerRepository.create();
        User user = userRepository
                .create(managerDTO.getFirstName(),
                        managerDTO.getMiddleName(),
                        managerDTO.getLastName(),
                        PersonalNumberGenerator.generate(10),
                        bCryptPasswordEncoder.encode(managerDTO.getPassword()),
                        null,
                        manager,
                        null);
        LOGGER.info("Manager " + user.getPersonalNumber() + " created successfully.");
        return new ReturnValuesContainer<User>(UpdateMessageType.MANAGER_CREATED,user);
    }

    @Transactional
    public UpdateMessageType createAdmin(AdminDTO adminDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createAdmin");
        if (!dtoValidator.validate(adminDTO)){
            LOGGER.error("Error: adminDTO is not valid.");
            return UpdateMessageType.ERROR_ADMIN_DTO_IS_NOT_VALID;
        }
        Admin admin = adminRepository.create();
        User user = userRepository
                .create(adminDTO.getFirstName(),
                        adminDTO.getMiddleName(),
                        adminDTO.getLastName(),
                        PersonalNumberGenerator.generate(10),
                        bCryptPasswordEncoder.encode(adminDTO.getPassword()),
                        null,
                        null,
                        admin);
        LOGGER.info("Admin " + user.getPersonalNumber() + " created successfully.");
        return UpdateMessageType.ADMIN_CREATED;
    }

    @Override
    @Transactional
    public ReturnValuesContainer<User> createAdmin(AdminDTO adminDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createAdmin");
        if (!dtoValidator.validate(adminDTO)){
            LOGGER.error("Error: adminDTO is not valid.");
            return new ReturnValuesContainer<User>(UpdateMessageType.ERROR_ADMIN_DTO_IS_NOT_VALID, null);
        }
        Admin admin = adminRepository.create();
        User user = userRepository
                .create(adminDTO.getFirstName(),
                        adminDTO.getMiddleName(),
                        adminDTO.getLastName(),
                        PersonalNumberGenerator.generate(10),
                        bCryptPasswordEncoder.encode(adminDTO.getPassword()),
                        null,
                        null,
                        admin);
        LOGGER.info("Admin " + user.getPersonalNumber() + " created successfully.");
        return new ReturnValuesContainer<User>(UpdateMessageType.ADMIN_CREATED,user);
    }

    @Transactional
    public UpdateMessageType updateDriver(DriverDTO driverDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateDriver");
        if (!dtoValidator.validate(driverDTO)) {
            LOGGER.error("Error: driverDTO is not valid.");
            return UpdateMessageType.ERROR_DRIVER_DTO_IS_NOT_VALID;
        }
        User updated = null;
        if (driverDTO.getId()!=null) {
            if (driverDTO.getId().length() != 0) {
                if (getIdValFromDTO(driverDTO) != 0) {
                    updated = userRepository.getById(getIdValFromDTO(driverDTO));
                    LOGGER.info("Updated driver: " + updated.getPersonalNumber());
                }
            }
        }
        if (updated!=null){
            updateDriverWithFieldsFromDTO(updated, driverDTO);
            LOGGER.info("Driver " + updated.getPersonalNumber() + " updated successfully.");
            return UpdateMessageType.DRIVER_EDITED;
        }
        LOGGER.error("Error: driver update  failed.");
        return UpdateMessageType.ERROR_CAN_NOT_UPDATE_DRIVER;
    }

    @Transactional
    public UpdateMessageType updateManager(ManagerDTO managerDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateManager");
        if (!dtoValidator.validate(managerDTO)) {
            LOGGER.error("Error: managerDTO is not valid.");
            return UpdateMessageType.ERROR_MANAGER_DTO_IS_NOT_VALID;
        }
        User updated = null;
        if (managerDTO.getId()!=null){
            if (managerDTO.getId().length()!=0){
                if (Integer.parseInt(managerDTO.getId())!=0){
                    updated = userRepository.getById(Integer.parseInt(managerDTO.getId()));
                    LOGGER.info("Updated manager: " + updated.getPersonalNumber());
                }
            }
        }
        if (updated!=null){
            updateManagerWithFieldsFromDTO(updated, managerDTO);
            LOGGER.info("Manager " + updated.getPersonalNumber() + " updated successfully.");
            return UpdateMessageType.MANAGER_EDITED;
        }
        LOGGER.error("Error: manager update  failed.");
        return UpdateMessageType.ERROR_CAN_NOT_UPDATE_MANAGER;
    }

    @Transactional
    public UpdateMessageType updateAdmin(AdminDTO adminDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateAdmin");
        if (!dtoValidator.validate(adminDTO)) {
            LOGGER.error("Error: adminDTO is not valid.");
            return UpdateMessageType.ERROR_ADMIN_DTO_IS_NOT_VALID;
        }
        User updated = null;
        if (adminDTO.getId()!=null){
            if (adminDTO.getId().length()!=0){
                if (Integer.parseInt(adminDTO.getId())!=0){
                    updated = userRepository.getById(Integer.parseInt(adminDTO.getId()));
                    LOGGER.info("Updated admin: " + updated.getPersonalNumber());
                }
            }
        }
        if (updated!=null){
            updateAdminWithFieldsFromDTO(updated, adminDTO);
            LOGGER.info("Admin " + updated.getPersonalNumber() + " updated successfully.");
            return UpdateMessageType.ADMIN_EDITED;
        }
        LOGGER.error("Error: admin update failed.");
        return UpdateMessageType.ERROR_CAN_NOT_UPDATE_ADMIN;
    }

    @Transactional
    public UpdateMessageType deleteDriver(int userId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteDriver");
        if (userId <= 0) {
            LOGGER.error("Error: user id value is not valid (id = " + userId + ").");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        User deleted = userRepository.getById(userId);
        if (deleted == null) {
            LOGGER.error("Error: there is no user with id = " + userId + " in database.");
            return UpdateMessageType.ERROR_NO_USER_WITH_THIS_ID;
        }
        if (deleted.getDriver() == null) {
            LOGGER.error("Error: user " + deleted.getPersonalNumber() + " is not a driver.");
            return UpdateMessageType.ERROR_USER_IS_NOT_A_DRIVER;
        }
        Driver driver = deleted.getDriver();
        userRepository.remove(userId);
        driverRepository.remove(driver.getId());
        LOGGER.info("Driver " + deleted.getPersonalNumber() + " deleted successfully");
        return UpdateMessageType.DRIVER_DELETED;
    }

    @Transactional
    public UpdateMessageType deleteManager(int userId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteManager");
        if (userId <= 0){
            LOGGER.error("Error: user id value is not valid (id = " + userId + ").");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        User deleted = userRepository.getById(userId);
        if (deleted == null) {
            LOGGER.error("Error: there is no user with id = " + userId + " in database.");
            return UpdateMessageType.ERROR_NO_USER_WITH_THIS_ID;
        }
        if (deleted.getManager() == null){
            LOGGER.error("Error: user " + deleted.getPersonalNumber() + " is not a manager.");
            return UpdateMessageType.ERROR_USER_IS_NOT_A_MANAGER;
        }
        Manager manager = deleted.getManager();
        userRepository.remove(userId);
        managerRepository.remove(manager.getId());
        LOGGER.info("Manager " + deleted.getPersonalNumber() + " deleted successfully.");
        return UpdateMessageType.MANAGER_DELETED;
    }

    @Transactional
    public UpdateMessageType deleteAdmin(int userId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteAdmin");
        if (userId <= 0) {
            LOGGER.error("Error: user id value is not valid (id = " + userId + ").");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        User deleted = userRepository.getById(userId);
        if (deleted == null) {
            LOGGER.error("Error: there is no user with id = " + userId + " in database.");
            return UpdateMessageType.ERROR_NO_USER_WITH_THIS_ID;
        }
        if (deleted.getAdmin() == null) {
            LOGGER.error("Error: user " + deleted.getPersonalNumber() + " is not an admin.");
            return UpdateMessageType.ERROR_USER_IS_NOT_AN_ADMIN;
        }
        Admin admin = deleted.getAdmin();
        userRepository.remove(userId);
        adminRepository.remove(admin.getId());
        LOGGER.info("Admin " + deleted.getPersonalNumber() + " deleted successfully.");
        return UpdateMessageType.ADMIN_DELETED;
    }

    public Collection<User> getAllDrivers() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAllDrivers");
        Collection<User> users = userRepository.getAll();
        Collection<User> drivers = new ArrayList<User>();
        for(User u: users){
            if (u.getDriver()!=null) drivers.add(u);
        }
        LOGGER.info("AllDriversCollection: " + drivers + ", size = " + drivers.size());
        return drivers;
    }

    public Collection<User> getFreeDrivers() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getFreeDrivers");
        Collection<User> users = userRepository.getAll();
        Collection<User> freeDrivers = new ArrayList<User>();
        for(User u: users){
            if (u.getDriver()!=null){
                Driver d = u.getDriver();
                LOGGER.info("Driver: " + u.getPersonalNumber());
                if (u.getDriver().getCurrentTruck() != null){
                    LOGGER.warn("Driver " + u.getPersonalNumber() + " is assigned to truck " + u.getDriver().getCurrentTruck());
                    if (d.getCurrentTruck().getAssignedOrder() != null){
                        LOGGER.warn("Driver " + u.getPersonalNumber() + " is executing order " + d.getCurrentTruck().getAssignedOrder().getDescription());
                    }
                }
                if (u.getDriver().getCurrentTruck() == null || u.getDriver().getCurrentTruck().getAssignedOrder()==null){
                    freeDrivers.add(u);
                    LOGGER.info("Driver " + u.getPersonalNumber() + " added to FreeDriversCollection.");
                }
            }
        }
        LOGGER.info("FreeDriversCollection: " + freeDrivers + ", size = " + freeDrivers.size());
        return freeDrivers;
    }

    public Collection<UserRole> getRoles() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getRoles");
        Collection<UserRole> userRoles = new ArrayList<UserRole>();
        userRoles.add(UserRole.ADMIN);
        userRoles.add(UserRole.DRIVER);
        userRoles.add(UserRole.MANAGER);
        LOGGER.info("RolesCollection: " + userRoles + ", size = " + userRoles.size());
        return userRoles;
    }

    @Transactional
    public UpdateMessageType createUser(UserDTO userDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createUser");
        if (!dtoValidator.validate(userDTO)) {
            LOGGER.error("Error: userDTO is not valid.");
            return UpdateMessageType.ERROR_USER_DTO_IS_NOT_VALID;
        }
        UpdateMessageType result = null;
        String role = userDTO.getRole();
        if (role.equals(UserRole.DRIVER.toString())){
            LOGGER.info("Creating driver...");
            DriverDTO driverDTO =
                    new DriverDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    PersonalNumberGenerator.generate(10),
                    userDTO.getPassword(),
                    userDTO.getHoursWorked(),
                    "Free",
                    userDTO.getCurrentCityName(),
                    userDTO.getCurrentTruckRegistrationNumber(),
                    userDTO.getOrderId());
            result = createDriver(driverDTO);
        }
        if (role.equals(UserRole.MANAGER.toString())){
            LOGGER.info("Creating manager...");
            ManagerDTO managerDTO = new ManagerDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPersonalNumber(),
                    userDTO.getPassword());
            result = createManager(managerDTO);
        }
        if (role.equals(UserRole.ADMIN.toString())){
            LOGGER.info("Creating admin...");
            AdminDTO adminDTO = new AdminDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPassword(),
                    userDTO.getPersonalNumber());
            result = createAdmin(adminDTO);
        }
        if (result == null) return UpdateMessageType.ERROR_CAN_NOT_CREATE_USER;
        if (result.equals(UpdateMessageType.DRIVER_CREATED)
                || result.equals(UpdateMessageType.MANAGER_CREATED)
                || result.equals(UpdateMessageType.ADMIN_CREATED)){
            LOGGER.info("User created successfully.");
            return UpdateMessageType.USER_CREATED;
        }
        else{
            LOGGER.error("Error: failed to create user.");
            return UpdateMessageType.ERROR_CAN_NOT_CREATE_USER;
        }
    }

    @Override
    @Transactional
    public ReturnValuesContainer<User> createUser(UserDTO userDTO, int val) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: createUser");
        if (!dtoValidator.validate(userDTO)) {
            LOGGER.error("Error: userDTO is not valid.");
            return new ReturnValuesContainer<User>(UpdateMessageType.ERROR_USER_DTO_IS_NOT_VALID, null);
        }
        ReturnValuesContainer<User> result = null;
        String role = userDTO.getRole();
        if (role.equals(UserRole.DRIVER.toString())){
            LOGGER.info("Creating driver...");
            DriverDTO driverDTO =
                    new DriverDTO(userDTO.getId(),
                            userDTO.getFirstName(),
                            userDTO.getMiddleName(),
                            userDTO.getLastName(),
                            PersonalNumberGenerator.generate(10),
                            userDTO.getPassword(),
                            userDTO.getHoursWorked(),
                            "Free",
                            userDTO.getCurrentCityName(),
                            userDTO.getCurrentTruckRegistrationNumber(),
                            userDTO.getOrderId());
            result = createDriver(driverDTO,0);
        }
        if (role.equals(UserRole.MANAGER.toString())){
            LOGGER.info("Creating manager...");
            ManagerDTO managerDTO = new ManagerDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPersonalNumber(),
                    userDTO.getPassword());
            result = createManager(managerDTO,0);
        }
        if (role.equals(UserRole.ADMIN.toString())){
            LOGGER.info("Creating admin...");
            AdminDTO adminDTO = new AdminDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPassword(),
                    userDTO.getPersonalNumber());
            result = createAdmin(adminDTO,0);
        }
        if (result == null) return new ReturnValuesContainer<User>(UpdateMessageType.ERROR_CAN_NOT_CREATE_USER,null);
        UpdateMessageType resultMessage = result.getUpdateMessageType();
        if (resultMessage.equals(UpdateMessageType.DRIVER_CREATED)
                || resultMessage.equals(UpdateMessageType.MANAGER_CREATED)
                || resultMessage.equals(UpdateMessageType.ADMIN_CREATED)){
            LOGGER.info("User created successfully.");
        }
        else{
            LOGGER.error("Error: failed to create user.");

        }
        return result;
    }

    @Transactional
    public UpdateMessageType updateUser(UserDTO userDTO) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateUser");
        if (!dtoValidator.validate(userDTO)) {
            LOGGER.error("Error: userDTO is not valid.");
            return UpdateMessageType.ERROR_USER_DTO_IS_NOT_VALID;
        }
        UpdateMessageType result = null;
        int id = 0;
        if (userDTO.getId() != null){
            try{
                id = Integer.parseInt(userDTO.getId());
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: can not parse user id.");
                return UpdateMessageType.ERROR_CAN_NOT_PARSE_USER_ID;
            }
        }
        if (id == 0) {
            LOGGER.error("Error: user id value is not valid (id = 0).");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        User updated = userRepository.getById(id);
        if (updated.getDriver() != null){
            LOGGER.info("Updating driver...");
            DriverDTO driverDTO = new DriverDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPersonalNumber(),
                    userDTO.getPassword(),
                    userDTO.getHoursWorked(),
                    userDTO.getDriverStatus(),
                    userDTO.getCurrentCityName(),
                    userDTO.getCurrentTruckRegistrationNumber(),
                    userDTO.getOrderId());
            result = updateDriver(driverDTO);

        }
        if (updated.getAdmin()!= null){
            LOGGER.info("Updating admin...");
            AdminDTO adminDTO = new AdminDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPassword(),
                    userDTO.getPersonalNumber());
            result = updateAdmin(adminDTO);
        }
        if (updated.getManager() != null){
            LOGGER.info("Updating manager...");
            ManagerDTO managerDTO = new ManagerDTO(userDTO.getId(),
                    userDTO.getFirstName(),
                    userDTO.getMiddleName(),
                    userDTO.getLastName(),
                    userDTO.getPersonalNumber(),
                    userDTO.getPassword());
            result = updateManager(managerDTO);
        }
        if (result == null) return UpdateMessageType.ERROR_CAN_NOT_EDIT_USER;
        if (result.equals(UpdateMessageType.DRIVER_EDITED)
            || result.equals(UpdateMessageType.MANAGER_EDITED)
            || result.equals(UpdateMessageType.ADMIN_EDITED)){
            LOGGER.info("User updated successfully.");
            return UpdateMessageType.USER_EDITED;
        }
        else {
            LOGGER.error("Error: failed to update user.");
            return UpdateMessageType.ERROR_CAN_NOT_EDIT_USER;
        }
    }

    @Transactional
    public UpdateMessageType deleteUser(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: deleteUser");
        if (id <= 0) {
            LOGGER.error("Error: user id value is not valid.");
            return UpdateMessageType.ERROR_ID_IS_NOT_VALID;
        }
        UpdateMessageType result = null;
        User deleted = userRepository.getById(id);
        if (deleted == null) {
            LOGGER.error("Error: there is no user with id = " + id + " in database.");
            return UpdateMessageType.ERROR_NO_USER_WITH_THIS_ID;
        }
        if (deleted.getManager() != null){
            LOGGER.info("Deleting manager...");
            result = deleteManager(id);
        }
        if (deleted.getAdmin() != null){
            LOGGER.info("Deleting admin...");
            result = deleteAdmin(id);
        }
        if (deleted.getDriver() != null){
            LOGGER.info("Deleting driver...");
            result = deleteDriver(id);
        }
        if (result.equals(UpdateMessageType.DRIVER_DELETED)
                || result.equals(UpdateMessageType.MANAGER_DELETED)
                || result.equals(UpdateMessageType.ADMIN_DELETED)){
            LOGGER.info("User deleted successfully.");
            return UpdateMessageType.USER_DELETED;
        }
        else {
            LOGGER.error("Error: failed to delete user.");
            return UpdateMessageType.ERROR_CAN_NOT_DELETE_USER;
        }
    }

    private DriverStatus getDriverStatusValFromDriverDTO(DriverDTO driverDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getDriverStatusValFromDriverDTO");
        if (driverDTO.getDriverStatus() == null){
            LOGGER.error("Error: driverStatus field in driverDTO is null.");
            return null;
        }
        String driverStatus = driverDTO.getDriverStatus();
        DriverStatus result = null;
            if (driverStatus.equals("Free")) result = DriverStatus.FREE;
            if (driverStatus.equals("Resting")) result = DriverStatus.RESTING;
            if (driverStatus.equals("Driving")) result = DriverStatus.DRIVING;
            if (driverStatus.equals("Second driver")) result = DriverStatus.SECOND_DRIVER;
            if (driverStatus.equals("Load/unload works")) result = DriverStatus.LOAD_UNLOAD_WORKS;
            if (result != null){
                LOGGER.info("Driver status is " + result);
            }
            else {
                LOGGER.error("Error: driver status is null.");
            }
            return result;
    }

    private double getHoursWorkedValFromDriverDTO(DriverDTO driverDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getDriverStatusValFromDriverDTO");
        if (driverDTO == null){
            LOGGER.error("Error: driverDTO is null.");
            return 0;
        }
        if (driverDTO.getHoursWorked()!=null) {
            double hoursWorked = 0;
            try{
                hoursWorked = Double.parseDouble(driverDTO.getHoursWorked());
            }
            catch (Exception e){
                e.printStackTrace();
                LOGGER.error("Error: can not parse hours worked.");
                return 0;
            }
            LOGGER.info("Hours worked: " + hoursWorked);
            return hoursWorked;
        }
        LOGGER.error("Error: hoursWorked field in driverDTO is null.");
        return 0;
    }

    private City getCurrentCityFromDriverDTO(DriverDTO driverDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCurrentCityFromDriverDTO");
        if (driverDTO==null) {
            LOGGER.error("Error: driverDTO is null.");
            return null;
        }
        if (driverDTO.getCurrentCityName()!=null) {
            City c = cityRepository.getByName(driverDTO.getCurrentCityName());
            LOGGER.info("City: " + c.getName());
            return c;
        }
        LOGGER.error("Error: currentCityName field in driverDTO is null.");
        return null;
    }

    private Truck getCurrentTruckFromDriverDTO(DriverDTO driverDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCurrentTruckFromDriverDTO");
        if (driverDTO == null){
            LOGGER.error("Error: driverDTO is null.");
            return null;
        }
        if (driverDTO.getCurrentTruckRegistrationNumber()!=null) {
            Truck t = truckRepository.getByRegistrationNumber(driverDTO.getCurrentTruckRegistrationNumber());
            LOGGER.info("Truck: " + t.getRegistrationNumber());
            return t;
        }
        LOGGER.error("Error: currentTruckRegistrationNumber field in driverDTO is null.");
        return null;
    }

    private int getIdValFromDTO(Object dto){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getIdValFromDTO");
        if (dto == null) {
            LOGGER.error("Error: dto is null.");
            return 0;
        }
        if (dto instanceof AdminDTO) {
            LOGGER.info("Dto is instance of AdminDTO. Id = " + Integer.parseInt(((AdminDTO) dto).getId()));
            return Integer.parseInt(((AdminDTO) dto).getId());
        }
        if (dto instanceof DriverDTO) {
            LOGGER.info("Dto is instance of DriverDTO. Id = " + Integer.parseInt(((DriverDTO) dto).getId()));
            return Integer.parseInt(((DriverDTO) dto).getId());
        }
        if (dto instanceof ManagerDTO) {
            LOGGER.info("Dto is instance of ManagerDTO. Id = " + Integer.parseInt(((ManagerDTO) dto).getId()));
            return Integer.parseInt(((ManagerDTO) dto).getId());
        }
        LOGGER.error("Error: dto is not instance of known DTO.");
        return 0;
    }

    private void updateDriverWithFieldsFromDTO(User updated, DriverDTO driverDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateDriverWithFieldsFromDTO");
        String newFirstName = null;
        String newMiddleName = null;
        String newLastName = null;
        String newPersonalNumber = null;
        String newPassword = null;
        Double newHoursWorked;
        DriverStatus newDriverStatus;
        City newCity = null;
        Truck newTruck = null;
        if (driverDTO.getFirstName()!=null && driverDTO.getFirstName().length()!=0) newFirstName = driverDTO.getFirstName();
        else newFirstName = updated.getName();
        LOGGER.info("New first name: " + newFirstName);
        if (driverDTO.getMiddleName()!=null && driverDTO.getMiddleName().length()!=0) newMiddleName = driverDTO.getMiddleName();
        else newMiddleName = updated.getMiddleName();
        LOGGER.info("New middle name: " + newMiddleName);
        if (driverDTO.getLastName()!=null && driverDTO.getLastName().length()!=0) newLastName = driverDTO.getLastName();
        else newLastName = updated.getLastName();
        LOGGER.info("New last name: " + newLastName);
        if (driverDTO.getPersonalNumber()!=null && driverDTO.getPersonalNumber().length()!=0) newPersonalNumber = driverDTO.getPersonalNumber();
        else newPersonalNumber = updated.getPersonalNumber();
        LOGGER.info("New personal number: " + newPersonalNumber);
        if (driverDTO.getPassword()!=null && driverDTO.getPassword().length()!=0) newPassword = bCryptPasswordEncoder.encode(driverDTO.getPassword());
        else newPassword = updated.getPassword();
        LOGGER.info("New password: " + newPassword);
        if (driverDTO.getHoursWorked()!=null && driverDTO.getHoursWorked().length()!=0) newHoursWorked = Double.parseDouble(driverDTO.getHoursWorked());
        else newHoursWorked = updated.getDriver().getHoursWorked();
        LOGGER.info("New hours worked: " + newHoursWorked);
        if (driverDTO.getDriverStatus()!=null && driverDTO.getDriverStatus().length()!=0 && !driverDTO.getDriverStatus().equals("Not selected")) newDriverStatus = getDriverStatusValFromDriverDTO(driverDTO);
        else newDriverStatus = updated.getDriver().getStatus();
        LOGGER.info("New status: " + newDriverStatus);
        if (driverDTO.getCurrentCityName()!=null && driverDTO.getCurrentCityName().length()!=0 && !driverDTO.getCurrentCityName().equals("Not selected") && !driverDTO.getCurrentCityName().equals("No cities available")) newCity = cityRepository.getByName(driverDTO.getCurrentCityName());
        else newCity = updated.getDriver().getCurrentCity();
        LOGGER.info("New current city: " + newCity);
        if (driverDTO.getCurrentTruckRegistrationNumber()!=null && driverDTO.getCurrentTruckRegistrationNumber().length()!=0 && !driverDTO.getCurrentTruckRegistrationNumber().equals("Not selected") && !driverDTO.getCurrentTruckRegistrationNumber().equals("No trucks available")) newTruck = truckRepository.getByRegistrationNumber(driverDTO.getCurrentTruckRegistrationNumber());
        else newTruck = updated.getDriver().getCurrentTruck();
        LOGGER.info("New current truck: " + newTruck);
        Driver driver = driverRepository.update(updated.getDriver().getId(),newHoursWorked,newDriverStatus,newCity,newTruck);
        userRepository.update(updated.getId(),newFirstName,newMiddleName,newLastName,newPersonalNumber,newPassword,driver,null,null);
        LOGGER.info("Driver fields updated successfully.");
    }

    private void updateManagerWithFieldsFromDTO(User updated, ManagerDTO managerDTO){
        LOGGER.info("Class: " + this.getClass().getName() + " method: updateManagerWithFieldsFromDTO");
        String newFirstName = null;
        String newMiddleName = null;
        String newLastName = null;
        String newPersonalNumber = null;
        String newPassword = null;
        if (managerDTO.getFirstName()!=null && managerDTO.getFirstName().length()!=0) newFirstName = managerDTO.getFirstName();
        else newFirstName = updated.getName();
        LOGGER.info("New first name: " + newFirstName);
        if (managerDTO.getMiddleName()!=null && managerDTO.getMiddleName().length()!=0) newMiddleName = managerDTO.getMiddleName();
        else newMiddleName = updated.getMiddleName();
        LOGGER.info("New middle name: " + newMiddleName);
        if (managerDTO.getLastName()!=null && managerDTO.getLastName().length()!=0) newLastName = managerDTO.getLastName();
        else newLastName = updated.getLastName();
        LOGGER.info("New last name: " + newLastName);
        if (managerDTO.getPersonalNumber()!=null && managerDTO.getPersonalNumber().length()!=0) newPersonalNumber = managerDTO.getPersonalNumber();
        else newPersonalNumber = updated.getPersonalNumber();
        LOGGER.info("New personal number: " + newPersonalNumber);
        if (managerDTO.getPassword()!=null && managerDTO.getPassword().length()!=0) newPassword = bCryptPasswordEncoder.encode(managerDTO.getPassword());
        else newPassword = updated.getPassword();
        LOGGER.info("New password: " + newPassword);
        Manager manager = managerRepository.update(updated.getManager().getId());
        userRepository.update(updated.getId(),newFirstName, newMiddleName, newLastName, newPersonalNumber, newPassword, null,manager, null);
        LOGGER.info("Manager fields updated successfully.");
    }

    private void updateAdminWithFieldsFromDTO(User updated, AdminDTO adminDTO){
        String newFirstName = null;
        String newMiddleName = null;
        String newLastName = null;
        String newPersonalNumber = null;
        String newPassword = null;
        if (adminDTO.getFirstName()!=null && adminDTO.getFirstName().length()!=0) newFirstName = adminDTO.getFirstName();
        else newFirstName = updated.getName();
        LOGGER.info("New first name: " + newFirstName);
        if (adminDTO.getMiddleName()!=null && adminDTO.getMiddleName().length()!=0) newMiddleName = adminDTO.getMiddleName();
        else newMiddleName = updated.getMiddleName();
        LOGGER.info("New middle name: " + newMiddleName);
        if (adminDTO.getLastName()!=null && adminDTO.getLastName().length()!=0) newLastName = adminDTO.getLastName();
        else newLastName = updated.getLastName();
        LOGGER.info("New last name: " + newLastName);
        if (adminDTO.getPersonalNumber()!=null && adminDTO.getPersonalNumber().length()!=0) newPersonalNumber = adminDTO.getPersonalNumber();
        else newPersonalNumber = updated.getPersonalNumber();
        LOGGER.info("New personal number: " + newPersonalNumber);
        if (adminDTO.getPassword()!=null && adminDTO.getPassword().length()!=0) newPassword = bCryptPasswordEncoder.encode(adminDTO.getPassword());
        else newPassword = updated.getPassword();
        LOGGER.info("New password: " + newPassword);
        Admin admin = adminRepository.update(updated.getAdmin().getId());
        userRepository.update(updated.getId(),newFirstName, newMiddleName, newLastName, newPersonalNumber, newPassword, null,null, admin);
        LOGGER.info("Admin fields updated successfully.");
    }

}
